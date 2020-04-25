package com.cheng.manage.service.account.role.impl;

import com.cheng.manage.common.exception.MyException;
import com.cheng.manage.common.result.Result;
import com.cheng.manage.common.result.ResultEnum;
import com.cheng.manage.constant.app.account.role.RoleConstant;
import com.cheng.manage.constant.app.system.menu.MenuConstant;
import com.cheng.manage.constant.app.system.permission.PermissionConstant;
import com.cheng.manage.entity.account.role.RoleBO;
import com.cheng.manage.entity.account.role.RoleDTO;
import com.cheng.manage.entity.account.role.RoleMenuBO;
import com.cheng.manage.entity.account.role.RolePermissionBO;
import com.cheng.manage.service.account.base.AccountBaseService;
import com.cheng.manage.service.account.role.RoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description : roleService 接口实现类
 * @Author : cheng fei
 * @Date : 2019/3/23 02:01
 */

@Service
public class RoleServiceImpl extends AccountBaseService implements RoleService {

    /**
     * 数据库日志操作类型
     */
    private static final String  DB_LOG_TYPE = "SYSTEM_DB_LOG_TYPE_ROLE";

    @Override
    public List<RoleBO> getRoleListByUsername(String username) {
        return super.getRoleListByUsername(username);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result saveOrUpdateRole(RoleBO roleBO, boolean isUpdateStatus) {

        Integer loginAccountId = getLoginAccountId();

        logger.debug("添加角色，account=【 {} 】，role= 【 {} 】，isUpdateStatus=【  {}  】");

        RoleBO oldRoleBO = null;
        if (roleBO.getId() != null) {
            oldRoleBO = roleMapper.selectByPrimaryKey(roleBO.getId());
        }

        //检测参数
        checkRole(roleBO, oldRoleBO, isUpdateStatus);

        if (oldRoleBO == null){
            //补全参数
            roleBO.setStatus(true);
            roleBO.setCreateTime(new Date());
            roleBO.setCreateId(loginAccountId);

            int i = roleMapper.insertSelective(roleBO);
            checkDbInsert(i);

            saveDBLog(loginAccountId,getRoleLog(roleBO), DB_LOG_TYPE);
        } else {

            //补全参数
            roleBO.setUpdateId(loginAccountId);
            roleBO.setUpdateTime(new Date());

            int i = roleMapper.updateByPrimaryKeySelective(roleBO);
            checkDbUpdate(i);

            saveDBLog(loginAccountId, getRoleLog(roleBO, oldRoleBO, isUpdateStatus), DB_LOG_TYPE);

            // 删除所有用户的在redis中缓存的角色列表
            jedisUtil.delAll(appCacheDb,RoleConstant.ACCOUNT_ROLES_KEY_PRE + "*");
        }

        return Result.succee();
    }

    @Override
    public Result getRoleList(String name, String roleCode, Integer page, Integer pageSize) {

        logger.debug("查询角色列表，name=【 {} 】，page=【 {} 】，pageSize=【 {} 】", name, page, pageSize);

        //检测参数
        name = checkSearchString(name);
        roleCode = checkSearchString(roleCode);
        //分页
        PageHelper.startPage(page, pageSize);
        List<RoleBO> roleBOS = roleMapper.getRoleList(name, roleCode);
        PageInfo<RoleBO> info = new PageInfo<>(roleBOS);
        roleBOS = info.getList();

        //数据处理
        List<RoleDTO> data = new ArrayList<>();
        for (RoleBO roleBO : roleBOS) {
            RoleDTO roleDTO = new RoleDTO(roleBO);
            roleDTO.setCreateName(accountMapper.getNameById(roleBO.getCreateId()));
            roleDTO.setUpdateName(accountMapper.getNameById(roleBO.getUpdateId()));
            data.add(roleDTO);
        }

        return getResult(data, info.getTotal());
    }

    @Override
    public Result checkName(String name, Integer excludeId) {
        logger.debug("检测角色名称：name=【 {} 】，excludeId=【 {} 】", name, excludeId);

        return Result.succee(checkRoleName(name, excludeId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result deleteRole(Integer id) {

        logger.debug("删除角色,id=【 {} 】", id);

        //查询角色名称
        String roleName = roleMapper.getRoleNameById(id);

        //删除账号角色
        accountRoleMapper.deleteAccountRoleByROleId(id);

        //删除角色权限
        rolePermissionMapper.deleteRolePermissionByRoleId(id);

        //删除角色
        roleMapper.deleteByPrimaryKey(id);

        //数据库操作日志
        String log = "删除了角色【 " + roleName  + " 】";
        saveDBLog(getLoginAccountId(), log, DB_LOG_TYPE);

        // 删除所有用户的在redis中缓存的角色列表
        jedisUtil.delAll(appCacheDb,RoleConstant.ACCOUNT_ROLES_KEY_PRE + "*");

        return Result.succee();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result updateRolePermission(Integer id, String permissions) {

        //检测参数
        if (id == null) {
            return Result.build(ResultEnum.PARAM_ERROR);
        }

        //删除旧权限
        rolePermissionMapper.deleteRolePermissionByRoleId(id);

        //删除旧角色菜单权限
        roleMenuMapper.deleteRoleMenuByRoleId(id);

        if (StringUtils.isNotBlank(permissions)) {
            //获取权限列表
            String[] permissionArrayStr = permissions.split(",");
            //角色拥有的菜单id列表
            List<Integer> menuIds = new ArrayList<>();
            //角色拥有权限id列表
            List<Integer> permissionIds = new ArrayList<>();
            for (String permission : permissionArrayStr) {
                if (permission.contains(MenuConstant.MENU_PERMISSION_TREE_MENU_PRE)){
                    //菜单权限
                    permission = permission.replace(MenuConstant.MENU_PERMISSION_TREE_MENU_PRE, "");
                    if(!checkNumber(permission) ){
                        throw new MyException(ResultEnum.PARAM_ERROR);
                    }
                    menuIds.add(Integer.parseInt(permission));

                } else if (permission.contains(MenuConstant.MENU_PERMISSION_TREE_PERMISSION_PRE)){
                    //权限
                    permission = permission.replace(MenuConstant.MENU_PERMISSION_TREE_PERMISSION_PRE, "");
                    if(!checkNumber(permission) ){
                        throw new MyException(ResultEnum.PARAM_ERROR);
                    }
                    permissionIds.add(Integer.parseInt(permission));
                }
            }

            //添加角色权限
            for (Integer permissionId : permissionIds) {
                RolePermissionBO rolePermissionBO = new RolePermissionBO(id, permissionId);
                int i = rolePermissionMapper.insertSelective(rolePermissionBO);
                checkDbInsert(i);
            }

            //添加角色菜单权限
            for (Integer menuId : menuIds) {
                RoleMenuBO roleMenuBO = new RoleMenuBO(menuId, id);
                int i = roleMenuMapper.insertSelective(roleMenuBO);
                checkDbInsert(i);
            }
        }

        String log = "更新了【 " + roleMapper.getRoleNameById(id) + " 】角色权限！";
        saveDBLog(getLoginAccountId(), log, DB_LOG_TYPE);

        // 删除所有用户缓存的权限列表
        jedisUtil.delAll(appCacheDb, PermissionConstant.ACCOUNT_PERMISSIONS_KEY_PRE + "*");

        return Result.succee();
    }

    @Override
    public Result getRolePermission(Integer id) {

        List<String> permissions = new ArrayList<>();

        List<Integer> menuIds = roleMenuMapper.getRoleMneuByRoleId(id);
        for (Integer menuId : menuIds) {
            permissions.add(MenuConstant.MENU_PERMISSION_TREE_MENU_PRE + menuId);
        }

        List<Integer> permissionIds = rolePermissionMapper.getPermissionIdyRoleId(id);
        for (Integer permissionId : permissionIds) {
            permissions.add(MenuConstant.MENU_PERMISSION_TREE_PERMISSION_PRE + permissionId);
        }

        return Result.succee(permissions);
    }

    @Override
    public Result getRoleCheckbox() {
        return Result.succee( roleMapper.getAllRole());
    }

    @Override
    public Result checkRoleCode(String code, Integer excludeId) {
        Integer count = roleMapper.checkRoleCode(code, excludeId);
        return Result.succee(null == count || count < 1);
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/23 22:52
     * 描述 : 获取角色数据库修改日志
     * @param roleBO 角色
     * @param oldRoleBO 数据库储存角色
     * @param isUpdateStatus 是否是修改角色状态
     * @return
     */
    private String getRoleLog(RoleBO roleBO, RoleBO oldRoleBO, boolean isUpdateStatus) {
        StringBuffer log = new StringBuffer();

        if (!isUpdateStatus) {
            if (!equalsString(roleBO.getName(), oldRoleBO.getName())){
                log.append("角色名称：").append(oldRoleBO.getName()).append("==>").append(roleBO.getName()).append("，");
            }

            if (!equalsString(roleBO.getRoleCode(), oldRoleBO.getRoleCode())){
                log.append("角色编码：").append(oldRoleBO.getRoleCode()).append("==>").append(roleBO.getRoleCode()).append("，");
            }

            if (!equalsInteger(roleBO.getDisplayOrder(), oldRoleBO.getDisplayOrder())){
                log.append("排序码：").append(oldRoleBO.getDisplayOrder()).append("==>").append(roleBO.getDisplayOrder()).append("，");
            }

            if (!equalsString(roleBO.getRemark(), oldRoleBO.getRemark())){
                log.append("备注：").append(oldRoleBO.getRemark()).append("==>").append(roleBO.getRemark()).append("，");
            }


        } else {
            if (!equalsBoolean(roleBO.getStatus(), oldRoleBO.getStatus())){
                log.append("状态：").append(oldRoleBO.getStatus() ? "启用" : "禁用").append("==>").append(roleBO.getStatus() ? "启用" : "禁用").append("，");
            }
        }
        String logStr = log.toString();
        StringBuffer data = new StringBuffer();
        data.append("修改了角色【 ").append(oldRoleBO.getName()).append("】").append("==>").append("【 ").append(logStr.substring(0, logStr.lastIndexOf("，")));
        data.append("】");
        return data.toString();
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/23 22:51
     * 描述 : 获取角色数据库添加日志
     * @param roleBO 角色
     * @return
     */
    private String getRoleLog(RoleBO roleBO) {
        StringBuffer log = new StringBuffer();
        log.append("添加了角色【 名称=")
                .append(roleBO.getName())
                .append("，排序码=")
                .append(roleBO.getDisplayOrder())
                .append("，备注=")
                .append(StringUtils.isBlank(roleBO.getRemark()) ? "\"\"" : roleBO.getRemark())
                .append(" 】");
        return log.toString();
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/23 22:14
     * 描述 : 检测权限参数
     *
     * @param roleBO         修改的角色
     * @param oldRoleBO      数据库储存角色
     * @param isUpdateStatus 是否是修改状态
     */
    private void checkRole(RoleBO roleBO, RoleBO oldRoleBO, boolean isUpdateStatus) {

        if (!isUpdateStatus) {
            //检测角色名称
            if (StringUtils.isBlank(roleBO.getName())) {
                throw new MyException(ResultEnum.ROLE_NAME_IS_NULL);
            } else {
                if (!checkRoleName(roleBO.getName(), oldRoleBO == null ? null : oldRoleBO.getId())) {
                    throw new MyException(ResultEnum.ROLE_NAME_IS_EXIST);
                }
            }

            //检测角色编码
            if (StringUtils.isBlank(roleBO.getRoleCode())) {
                throw new MyException(ResultEnum.ROLE_CODE_IS_NULL);
            } else {
                if (!(Boolean) checkRoleCode(roleBO.getName(), oldRoleBO == null ? null : oldRoleBO.getId()).getData()) {
                    throw new MyException(ResultEnum.ROLE_CODE_IS_EXIST);
                }
            }

            if (roleBO.getDisplayOrder() == null){
                throw new MyException(ResultEnum.ROLE_DISPLAY_ORDER_IS_NULL);
            }
        } else {
            if (roleBO.getStatus() == null){
                throw new MyException(ResultEnum.ROLE_STATUS_IS_NULL);
            }
        }

        //检测修改时间
        if (oldRoleBO != null){
            checkUpdateTime(roleBO.getUpdateTime(), oldRoleBO.getUpdateTime());
        }
    }

    /**
     *
     * @Author: cheng fei
     * @Date: 2019/4/23 22:27
     * @Description: 检测角色名是否可用
     * @param name 权限名称
     * @param excludeId  需要排除的ID
     * @return
     */
    private boolean checkRoleName(String name, Integer excludeId) {
        int count = roleMapper.countByName(name, excludeId);
        return count <= 0;
    }

}
