package com.cheng.manage.service.system.menu.impl;

import com.cheng.manage.common.exception.MyException;
import com.cheng.manage.common.result.Result;
import com.cheng.manage.common.result.ResultEnum;
import com.cheng.manage.constant.app.system.SystemConstant;
import com.cheng.manage.constant.app.system.menu.MenuConstant;
import com.cheng.manage.dao.system.menu.MenuMapper;
import com.cheng.manage.dao.system.permission.PermissionMapper;
import com.cheng.manage.entity.account.account.AccountBO;
import com.cheng.manage.entity.account.role.RoleBO;
import com.cheng.manage.entity.system.menu.MenuBO;
import com.cheng.manage.entity.system.menu.MenuDTO;
import com.cheng.manage.entity.system.menu.MenuPermissionTreeNode;
import com.cheng.manage.entity.system.menu.MenuTreeNode;
import com.cheng.manage.entity.system.permission.PermissionBO;
import com.cheng.manage.service.system.base.SystemBaseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Description :
 * @Author : cheng fei
 * @Date : 2019/3/26 00:52
 */

@Service
public class MenuServiceImpl extends SystemBaseService implements MenuService {


    /**
     * 数据库操作日志类型
     */
    private static final String DB_LOG_TYPE = "SYSTEM_DB_LOG_TYPE_MENU";

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result saveORUpdateMenu(MenuBO menu, boolean isUpdateStatus) {
        logger.debug("新增/修改菜单：menu=【 {} 】", menu);
        Integer loginAccountId = getLoginAccountId();
        MenuBO oldMenu = null;
        if (menu.getId() != null) {
            oldMenu = menuMapper.selectByPrimaryKey(menu.getId());
        }

        //检测参数
        checkParam(menu, oldMenu, isUpdateStatus);

        if (menu.getId() == null) {
            saveMenu(menu, loginAccountId);
            updateParentMenu(loginAccountId, menu.getParentId(), true);
            saveDBLog(loginAccountId, getDbLogByMenu(menu), DB_LOG_TYPE);
        } else {
            updateMenu(menu, oldMenu, loginAccountId);
            saveDBLog(loginAccountId, getDbLogByMenu(menu, oldMenu, isUpdateStatus), DB_LOG_TYPE);
        }

        // 新增/修改菜单时,删除菜单权限树,菜单树缓存
        deleteRedis(SystemConstant.MENU_PERMISSION_TREE_REDIS_KEY);
        deleteRedis(MenuConstant.MENU_TREE_KEY);

        return Result.succee(menu);
    }

    @Override
    public Result getMenuList(Integer parentId, Boolean status) {
        logger.debug("查询菜单列表： parentId【 {} 】，status= 【 {} 】", parentId, status);
        List<MenuBO> menuList = menuMapper.getMenuList(parentId, status);
        List<MenuDTO> data = new ArrayList<>();
        for (MenuBO menu : menuList) {
            MenuDTO menuDTO = new MenuDTO(menu);
            if (menu.getCreateId() != null){
                menuDTO.setCreateName(accountMapper.getNameById(menu.getCreateId()));
            }
            if (menu.getUpdateId() != null){
                menuDTO.setUpdateName(accountMapper.getNameById(menu.getUpdateId()));
            }
            data.add(menuDTO);
        }
        return Result.succee(data);
    }

    @Override
    public Result getMenuTreeList(Integer parentId, Boolean status, Integer excludeId) {
        logger.debug("查询菜单树列表： parentId=【 {} 】，status=【 {} 】，excludeId=【 {} 】", parentId, excludeId);
        List<MenuBO> menuList = menuMapper.getMenuTreeList(parentId, status, excludeId);
        return Result.succee(menuList);
    }

    @Override
    public Result getAccountMenuAuthority() {

        AccountBO loginAccount = getLoginAccount();

        logger.debug("执行查询用户菜单权限");
        Set<Integer> allMenuIds = new HashSet<>();
        if (systemSuperAdministrator.equals(loginAccount.getUsername())) {
            List<Integer> menuIds = menuMapper.getAllMenuId();
            allMenuIds.addAll(menuIds);
        } else {
            //查询当前用户拥有的角色
            List<Integer> roleIds= accountRoleMapper.getRoleIdsByAccountId(loginAccount.getId());

            //查询拥有的菜单Id列表

            for (Integer roleId : roleIds) {
                List<Integer> menuIds = roleMenuMapper.getRoleIdListByRoleId(roleId);
                allMenuIds.addAll(menuIds);
            }

        }

        List<MenuBO> menuAuthority = new ArrayList<>();
        for (Integer menuId : allMenuIds) {
            menuAuthority.add(menuMapper.getUrlAndDisplayOrderById(menuId));
        }

        return Result.succee(menuAuthority);
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/5/6 16:20
     * 描述 : 获取当前用户拥有的菜单树
     * @param allMenuIds
     * @param menuTree
     * @param data
     */
    private void getAccountMenuTree(Set<Integer> allMenuIds, List<MenuTreeNode> menuTree, List<MenuTreeNode> data) {
        for (MenuTreeNode menuTreeNode : menuTree) {
            if (allMenuIds.contains(menuTreeNode.getId())){
                MenuTreeNode accountMenuTreeNode = new MenuTreeNode();
                accountMenuTreeNode.setId(menuTreeNode.getId());
                accountMenuTreeNode.setName(menuTreeNode.getName());
                accountMenuTreeNode.setPath(menuTreeNode.getPath());
                accountMenuTreeNode.setChildren(new ArrayList<MenuTreeNode>());
                data.add(accountMenuTreeNode);
                getAccountMenuTree(allMenuIds, menuTreeNode.getChildren(), accountMenuTreeNode.getChildren());
            }
        }
    }

    @Override
    public Result getMenuPermissionTree() {

        //结果集
        List<MenuPermissionTreeNode> menuPermissionTreeNodes = new ArrayList<>();

        //查询菜单权限树缓
        Object redis = getRedis(SystemConstant.MENU_PERMISSION_TREE_REDIS_KEY);
        if (redis != null){
            return Result.succee(redis);
        }

        //获取菜单权限树
        getMenuPermissionTree(menuPermissionTreeNodes, MenuConstant.TOP_PARENT_ID);

        //缓存菜单权限树
        setRedis(SystemConstant.MENU_PERMISSION_TREE_REDIS_KEY, menuPermissionTreeNodes);

        return Result.succee(menuPermissionTreeNodes);
    }

    @Override
    public Result getAccountMenuPermission(String url) {

        AccountBO loginAccount = getLoginAccount();

        //获取菜单权限
        List<String> menuPermissions = menuMapper.getMenuPermissionByMenuUrl(url);

        //获取用户当前权限
        List<RoleBO> roles = getRoleListByUsername(loginAccount.getUsername());
        Set<Integer> roleIds = new HashSet<>();
        for (RoleBO role : roles) {
            roleIds.add(role.getId());
        }
        Set<String> permissions = getPermissionUrlsByRoleIds(roleIds, loginAccount.getUsername());

        //返回结果集
        Map<String, Boolean> data = new HashMap<>(menuPermissions.size());
        for (String menuPermission : menuPermissions) {
            data.put(menuPermission, permissions.contains(menuPermission));
        }

        return Result.succee(data);
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/29 23:08
     * 描述 : 递归查询权限菜单树
     * @param menuPermissionTreeNodes 权限菜单树节点列表
     * @param parentId 父菜单Id
     */
    private void getMenuPermissionTree(List<MenuPermissionTreeNode> menuPermissionTreeNodes, Integer parentId) {

        List<MenuBO> childrenMenus = menuMapper.getMenuList(parentId, true);
        for (MenuBO childrenMenu: childrenMenus) {
            MenuPermissionTreeNode menuPermissionTreeNode = new MenuPermissionTreeNode();
            menuPermissionTreeNode.setId(MenuConstant.MENU_PERMISSION_TREE_MENU_PRE + childrenMenu.getId());
            menuPermissionTreeNode.setName(childrenMenu.getName());
            menuPermissionTreeNode.setMenu(true);
            menuPermissionTreeNode.setChildren(new ArrayList<MenuPermissionTreeNode>());
            menuPermissionTreeNodes.add(menuPermissionTreeNode);
            if (checkHasChildren(childrenMenu.getId(), true)){
                //存在子菜单
                getMenuPermissionTree(menuPermissionTreeNode.getChildren(), childrenMenu.getId());
            } else {
                //不存在子菜单,查询菜单权限
                getMenuPermissionTreePermissionNode(menuPermissionTreeNode.getChildren(), childrenMenu);
            }

        }

    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/29 23:04
     * 描述 : 查询菜单下权限
     * @param children 菜单权限树子节点列表
     * @param childrenMenu 子菜单
     */
    private void getMenuPermissionTreePermissionNode(List<MenuPermissionTreeNode> children, MenuBO childrenMenu) {

        List<PermissionBO> permissions = permissionMapper.getPermissionListByMenuId(childrenMenu.getId(), true);
        for (PermissionBO permission : permissions) {
            MenuPermissionTreeNode menuPermissionTreeNode = new MenuPermissionTreeNode();
            menuPermissionTreeNode.setId(MenuConstant.MENU_PERMISSION_TREE_PERMISSION_PRE + permission.getId());
            menuPermissionTreeNode.setName(permission.getName());
            menuPermissionTreeNode.setMenu(false);
            children.add(menuPermissionTreeNode);
        }
    }


    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/29 22:47
     * 描述 : 检测菜单是否存在子菜单
     * @param id 菜单Id
     * @param status 菜单状态:全部/启用/禁用 null/true/false
     * @return
     */
    private boolean checkHasChildren(Integer id, Boolean status) {
        int count = menuMapper.getCountByParentId(id, status);
        return count > 0;
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/5 13:10
     * 描述 : 递归查询所有所有菜单
     * @param menus
     */
    private void getMenuTreeAll(List<MenuDTO> menus){

        for (MenuDTO menuDTO : menus) {
            //查询所有子节点
            List<MenuBO> menuList = menuMapper.getMenuList(menuDTO.getId(), true);
            if (menuList != null && menuList.size() > 0){
                ArrayList<MenuDTO> children = new ArrayList<>();
                for (MenuBO menu : menuList) {
                    MenuDTO childrenMenu = new MenuDTO(menu);
                    children.add(childrenMenu);
                }
                menuDTO.setChildren(children);
                getMenuTreeAll(children);
            }
        }
    }

    @Override
    public Result checkURL(Integer id, String url) {
        logger.debug("检测菜单URL, url【 {} 】", url);

        //检测参数
        if (StringUtils.isBlank(url)) {
            return Result.build(ResultEnum.MENU_URL_IS_NULL);
        }

        return Result.succee(checkMenuUrl(id, url));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result deleteMenu(Integer id) {

        logger.debug("删除菜单,菜单ID=【 {} 】", id );

        Integer loginAccountId = getLoginAccountId();

        MenuBO menu = menuMapper.selectByPrimaryKey(id);
        if (menu == null){
            return Result.build(ResultEnum.PARAM_ERROR);
        }
        if (menu.getHasChildren()){
            return Result.build(ResultEnum.MENU_EXIST_CHILDREN_MENU);
        }

        int i = menuMapper.deleteByPrimaryKey(id);
        checkDbDelete(i);

        //修改是否存在子菜单
        updateParentMenu(loginAccountId, menu.getParentId(), false);

        saveDBLog(loginAccountId , "删除了菜单【 " + menu.getName() + " 】", DB_LOG_TYPE);

        // 删除菜单时,删除菜单权限树、菜单树缓存
        deleteRedis(SystemConstant.MENU_PERMISSION_TREE_REDIS_KEY);
        deleteRedis(MenuConstant.MENU_TREE_KEY);

        return Result.succee();
    }

    @Override
    public Result checkName(Integer id, Integer parentId, String name) {

        logger.debug("检测菜单名是否可用:parentId=【 {} 】, name= 【 {} 】", parentId, name);
        if (StringUtils.isBlank(name)){
            return Result.build(ResultEnum.MENU_NAME_IS_NULL);
        }
        return Result.succee(checkMenuName(id, parentId, name));
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/5/5 18:06
     * 描述 : 获取菜单树
     * @return
     */
    private List<MenuTreeNode> getMenuTree(){

        //先查询redis缓存
        final Object redis = getRedis(MenuConstant.MENU_TREE_KEY);
        if (redis != null){
            return (List<MenuTreeNode>) redis;
        }

        List<MenuTreeNode> tree = new ArrayList<>();

        getMenuTree(tree, MenuConstant.TOP_PARENT_ID);

        //添加redis缓存
        setRedis(MenuConstant.MENU_TREE_KEY, tree);

        return tree;

    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/5/5 18:05
     * 描述 : 递归查询所有菜单
     * @param tree
     * @param parentId
     */
    private void getMenuTree(List<MenuTreeNode> tree, Integer parentId){
        List<MenuBO> menuList = menuMapper.getMenuList(parentId, true);
        for (MenuBO menuBO : menuList) {
            MenuTreeNode menuTreeNode = new MenuTreeNode(menuBO);
            menuTreeNode.setChildren(new ArrayList<MenuTreeNode>());
            tree.add(menuTreeNode);
            getMenuTree(menuTreeNode.getChildren(), menuBO.getId());
        }
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/28 16:25
     * 描述 : 检测菜单url
     * @param excludeId
     * @param url
     * @return
     */
    private boolean checkMenuUrl(Integer excludeId, String url){

        if (MenuConstant.PARENT_MENU_URL.equals(url)) {
            return true;
        }

        int count = menuMapper.getCountByUrl(excludeId, url);
        return count <= 0;
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/28 16:22
     * 描述 : 检测菜单名称
     * @param excludeId 需要排除的Id
     * @param parentId 父节点ID
     * @param name 菜单名称
     * @return
     */
    private boolean checkMenuName(Integer excludeId, Integer parentId, String name){
        int count = menuMapper.getCountByParentIdAndName(parentId, name, excludeId);
        return count <= 0;
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/3/26 22:58
     * 描述 : 修改菜单
     *
     * @param menu      菜单
     * @param accountID 当前登陆人员ID
     */
    private void updateMenu(MenuBO menu, MenuBO oldMenu, Integer accountID) {

        //补全参数
        menu.setUpdateId(accountID);
        menu.setUpdateTime(new Date());

        //修改
        int i = menuMapper.updateByPrimaryKeySelective(menu);
        checkDbUpdate(i);
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/3/28 23:03
     * 描述 : 获取菜单操作日志(新增)
     * @param menu 新增的菜单
     * @return
     */
    private String getDbLogByMenu(MenuBO menu){
        StringBuilder log = new StringBuilder();
        log.append("新增菜单【 ")
                .append("菜单名=").append(menu.getName()).append("， ")
                .append("URL=").append(menu.getUrl()).append("， ")
                .append("父菜单=").append(menu.getParentId() == MenuConstant.TOP_PARENT_ID ? "顶级菜单" : menuMapper.getMenuNameByMenuID(menu.getParentId())).append("， ")
                .append("排序码=").append(menu.getDisplayOrder())
                .append(" 】");
        return log.toString();
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/3/28 22:35
     * 描述 : 获取菜单操作日志(修改)
     * @param menu 更新的菜单
     * @param oldMenu 历史菜单
     * @return
     */
    private String getDbLogByMenu(MenuBO menu, MenuBO oldMenu, boolean isUpdateStatus){

        //检测旧菜单
        if (oldMenu == null || menu == null){
            throw new MyException(ResultEnum.MENU_GET_DB_LOG_ERROR);
        }

        StringBuilder log = new StringBuilder();

        if (!isUpdateStatus) {
            if (StringUtils.isNotBlank(menu.getName()) && !equalsString(menu.getName(), oldMenu.getName())) {
                log.append("菜单名：").append(oldMenu.getName()).append("==>").append(menu.getName()).append("，");
            }
            if (StringUtils.isNotBlank(menu.getUrl()) &&  !equalsString(menu.getUrl(), oldMenu.getUrl())) {
                log.append("url：").append(oldMenu.getUrl()).append("==>").append(menu.getUrl()).append("，");
            }
            if (menu.getParentId() != null && !equalsInteger(menu.getParentId(), oldMenu.getParentId())){
                log.append("父菜单：").append(menuMapper.getMenuNameByMenuID(oldMenu.getId())).append("==>")
                        .append(menuMapper.getMenuNameByMenuID(menu.getId())).append("，");
            }
            if (menu.getDisplayOrder() != null && !equalsInteger(menu.getDisplayOrder(), oldMenu.getDisplayOrder())){
                log.append("排序码：").append(oldMenu.getDisplayOrder()).append("==>").append(menu.getDisplayOrder()).append("， ");
            }
            if (menu.getHasChildren() != null && !equalsBoolean(menu.getHasChildren(), oldMenu.getHasChildren())){
                log.append("是否是父级菜单：").append(oldMenu.getHasChildren() ? "是" : "否").append("==>").append(menu.getHasChildren() ? "是" : "否").append("，");
            }
        } else {
            if (!equalsBoolean(menu.getStatus(), oldMenu.getStatus())){
                log.append("状态：").append(oldMenu.getStatus() ? "启用" : "禁用").append("==>").append(menu.getStatus() ? "启用" : "禁用").append("， ");
            }
        }

        String logStr = log.toString();
        if (StringUtils.isNotBlank(logStr)){
            StringBuilder allLog = new StringBuilder();
            allLog.append("修改了菜单【 ").append(oldMenu.getName()).append(" 】").append("==>【 ").append(logStr.substring(0, logStr.lastIndexOf("，"))).append(" 】 ");
            return allLog.toString();
        }else {
            return "";
        }
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/3/26 22:49
     * 描述 : 添加菜单
     *
     * @param menu      菜单
     * @param accountID 当前登陆人员ID
     */
    private void saveMenu(MenuBO menu, int accountID) {

        //补全参数
        menu.setStatus(true);
        menu.setHasChildren(false);
        menu.setHasPermission(false);
        menu.setCreateId(accountID);
        menu.setCreateTime(new Date());

        int i = menuMapper.insertSelective(menu);
        checkDbInsert(i);
    }


    /**
     * 作者: cheng fei
     * 创建日期: 2019/3/28 23:16
     * 描述 : 修改父级菜单是存在子菜单
     * @param accountID 账号ID
     * @param parentId 父菜单ID
     * @param hasChildren  是否存在子菜单
     */
    @Transactional(rollbackFor = Exception.class)
    private void updateParentMenu(Integer accountID, Integer parentId, Boolean hasChildren){
        //检测父菜单
        if (parentId == null){
            throw new MyException(ResultEnum.PARAM_ERROR);
        } else if (parentId == MenuConstant.TOP_PARENT_ID){
            return;
        }

        MenuBO parentMenu = menuMapper.selectByPrimaryKey(parentId);

        if (parentMenu != null){
            if (! (hasChildren && parentMenu.getHasChildren())){
                //要修改的菜单状态和目标状态不一致
                if (!hasChildren){
                    //修改菜单不是父菜单
                    //查询当前菜单是否存在子菜单
                    int count= menuMapper.getCountByParentId(parentMenu.getId(), null);
                    if (count > 0){
                        //存在子菜单
                        return;
                    }
                }
                MenuBO updateMenu = new MenuBO();
                updateMenu.setId(parentMenu.getId());
                updateMenu.setHasChildren(hasChildren);
                int i = menuMapper.updateByPrimaryKeySelective(updateMenu);
                checkDbUpdate(i);

                //数据库操作日志
                saveDBLog(accountID, getDbLogByMenu(updateMenu, parentMenu, false), DB_LOG_TYPE);
            }
        } else {
            throw new MyException(ResultEnum.PARAM_ERROR);
        }
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/3/26 1:05
     * 描述 : 检测参数
     *
     * @param menu    菜单
     * @param oldMenu 修改时查询的旧菜单
     * @return
     */
    private Result checkParam(MenuBO menu, MenuBO oldMenu, boolean isUpdateStatus) {

        if (!isUpdateStatus){

            //检测父菜单
            if (menu.getParentId() == null) {
                throw new MyException(ResultEnum.MENU_PARENT_ID_IS_NULL);
            }

            //检测菜单名称
            if (StringUtils.isBlank(menu.getName())) {
                throw new MyException(ResultEnum.MENU_NAME_IS_NULL);
            } else  if (!checkMenuName(oldMenu == null ? null : oldMenu.getId(), menu.getParentId(), menu.getName())) {
                throw new MyException(ResultEnum.MENU_NAME_IS_EXIST);
            }

            //检测菜单url
            if (StringUtils.isBlank(menu.getUrl())) {
                throw new MyException(ResultEnum.MENU_URL_IS_NULL);
            } else if (!checkMenuUrl(oldMenu == null ? null : oldMenu.getId(), menu.getUrl())) {
                throw new MyException(ResultEnum.MENU_URL_IS_EXIST);
            }

            //菜单排序码
            if (menu.getDisplayOrder() == null) {
                throw new MyException(ResultEnum.MENU_DISPLAY_ORDER_IS_NULL);
            }
        } else {
            if (menu.getStatus() == null) {
                throw new MyException(ResultEnum.MENU_STATUS_IS_NULL);
            }
        }

        //修改时检测更新时间是否一致
        if (menu.getId() != null && oldMenu != null) {
            checkUpdateTime(menu.getUpdateTime(), oldMenu.getUpdateTime());
        }

        return Result.succee();
    }


}
