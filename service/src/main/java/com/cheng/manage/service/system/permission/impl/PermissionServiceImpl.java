package com.cheng.manage.service.system.permission.impl;

import com.cheng.manage.common.exception.MyException;
import com.cheng.manage.common.result.Result;
import com.cheng.manage.common.result.ResultEnum;
import com.cheng.manage.constant.app.system.SystemConstant;
import com.cheng.manage.constant.app.system.permission.PermissionConstant;
import com.cheng.manage.entity.system.menu.MenuBO;
import com.cheng.manage.entity.system.permission.PermissionBO;
import com.cheng.manage.entity.system.permission.PermissionDTO;
import com.cheng.manage.service.system.base.SystemBaseService;
import com.cheng.manage.service.system.permission.PermissionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @Description : permissionService 接口实现类
 * @Author : cheng fei
 * @Date : 2019/3/23 02:09
 */

@Service
public class PermissionServiceImpl extends SystemBaseService implements PermissionService {

    private Logger logger = LoggerFactory.getLogger(PermissionServiceImpl.class);

    /**
     * 数据量操作日志类型
     */
    private static final String DB_LOG_TYPE = "SYSTEM_DB_LOG_TYPE_PERMISSION";

    @Override
    public Set<String> getPermissionUrlsByRoleIds(Set<Integer> roleIds, String username) {
        return super.getPermissionUrlsByRoleIds(roleIds, username);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result saveOrUpdatePermission(PermissionBO permission, boolean isUpdateStatus) {

        logger.debug("新增/修改权限： permission=【 {} 】， isUpdateStatus=【 {} 】", permission, isUpdateStatus);

        Integer loginAccountId = getLoginAccountId();

        PermissionBO oldPermission = null;
        if (permission.getId() != null) {
            oldPermission = permissionMapper.selectByPrimaryKey(permission.getId());
        }

        //检测参数
        checkPermission(permission, oldPermission, isUpdateStatus);

        if (permission.getId() == null) {

            //补全参数
            permission.setStatus(true);
            permission.setCreateTime(new Date());
            permission.setCreateId(loginAccountId);

            int i = permissionMapper.insertSelective(permission);
            checkDbInsert(i);

            //修改菜单是否配置了权限
            updateMenuHasPermission(permission.getMenuId(), true);

            saveDBLog(loginAccountId, getDBLog(permission), DB_LOG_TYPE);

        } else {

            //补全参数
            permission.setUpdateId(loginAccountId);
            permission.setUpdateTime(new Date());

            int i = permissionMapper.updateByPrimaryKeySelective(permission);
            checkDbUpdate(i);

            saveDBLog(loginAccountId, getDBLog(permission, oldPermission, isUpdateStatus), DB_LOG_TYPE);

        }

        //删除所有用户缓存的权限列表
        jedisUtil.delAll(appCacheDb,PermissionConstant.ACCOUNT_PERMISSIONS_KEY_PRE + "*");

        // 新增/修改权限时,删除菜单权限树缓存
        deleteRedis(SystemConstant.MENU_PERMISSION_TREE_REDIS_KEY);

        return Result.succee();
    }

    @Override
    public Result getPermissionList(Integer menuId, Integer page, Integer pageSize) {

        logger.debug("查询权限列表: menuId=【 {} 】", menuId);

        List<Integer> menuIds = getMenuAllChildMenuIds(menuId, true, true);

        String orderBy = "create_time";

        if (menuIds.size() == 1) {
            orderBy = "display_order";
        }

        PageHelper.startPage(page, pageSize);

        List<PermissionBO> permissionList = permissionMapper.getPermissionListByMenuIds(menuIds, orderBy);

        PageInfo<PermissionBO> info = new PageInfo<>(permissionList);

        List<PermissionDTO> permissionDTOS = new ArrayList<>(permissionList.size());

        for (PermissionBO permission : info.getList()) {

            PermissionDTO permissionDTO = new PermissionDTO(permission);
            permissionDTO.setCreateName(accountMapper.getNameById(permission.getCreateId()));
            permissionDTO.setUpdateName(accountMapper.getNameById(permission.getUpdateId()));
            permissionDTOS.add(permissionDTO);
        }

        return getResult(permissionDTOS, info.getTotal());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result deletePermission(Integer id) {

        logger.debug("删除权限, account=【 {} 】, id=【 {} 】", id);

        PermissionBO permissionBO = permissionMapper.selectByPrimaryKey(id);

        int i = permissionMapper.deleteByPrimaryKey(id);
        checkDbDelete(i);

        saveDBLog(getLoginAccountId(), "删除了权限【 " + permissionBO.getName() + " 】", DB_LOG_TYPE);

        //修改菜单是否配置了权限
        updateMenuHasPermission(permissionBO.getMenuId(), false);

        // 删除权限时,删除菜单权限树缓存
        deleteRedis(SystemConstant.MENU_PERMISSION_TREE_REDIS_KEY);

        //删除所有用户缓存的权限列表
        jedisUtil.delAll(appCacheDb,PermissionConstant.ACCOUNT_PERMISSIONS_KEY_PRE + "*");

        return Result.succee();
    }

    @Override
    public Result checkName(Integer menuId, String name, Integer excludeId) {
        return Result.succee(checkPermissionName(name, menuId, excludeId));
    }

    @Override
    public Result checkUrl(String url, Integer excludeId) {
        return Result.succee(checkPermissionUrl(url, excludeId));
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/30 2:53
     * 描述 : 修改菜单是否配置了权限
     *
     * @param menuId
     * @param hasPermission
     */
    private void updateMenuHasPermission(Integer menuId, boolean hasPermission) {
        MenuBO updateMenuBO = new MenuBO();
        updateMenuBO.setId(menuId);
        updateMenuBO.setHasPermission(hasPermission);
        if (hasPermission) {
            MenuBO menuBO = menuMapper.selectByPrimaryKey(menuId);
            if (!menuBO.getHasPermission()) {
                int i = menuMapper.updateByPrimaryKeySelective(updateMenuBO);
                checkDbUpdate(i);
            }
        } else {
            int count = permissionMapper.countByMenuId(menuId);
            if (count == 0) {
                int i = menuMapper.updateByPrimaryKeySelective(updateMenuBO);
                checkDbUpdate(i);
            }
        }
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/17 23:57
     * 描述 : 获取数据库操作日志
     *
     * @param permission     需要修改的权限
     * @param oldPermission  修改前的权限
     * @param isUpdateStatus 是否是修改权限状态
     * @return
     */
    private String getDBLog(PermissionBO permission, PermissionBO oldPermission, boolean isUpdateStatus) {
        StringBuffer log = new StringBuffer();
        if (!isUpdateStatus) {
            //菜单ID
            if (!equalsInteger(permission.getMenuId(), oldPermission.getMenuId())) {
                log.append("所属菜单：").append(menuMapper.getMenuNameByMenuID(oldPermission.getMenuId()))
                        .append("==>").append(menuMapper.getMenuNameByMenuID(permission.getMenuId())).append("，");
            }

            //名称
            if (!equalsString(permission.getName(), oldPermission.getName())) {
                log.append("权限名称：").append(oldPermission.getName()).append("==>").append(permission.getName()).append("，");
            }

            //url
            if (!equalsString(permission.getUrl(), oldPermission.getUrl())) {
                log.append("url：").append(oldPermission.getUrl()).append("==>").append(permission.getUrl()).append("，");
            }

            //排序码
            if (!equalsInteger(permission.getDisplayOrder(), oldPermission.getDisplayOrder())) {
                log.append("排序码：").append(oldPermission.getDisplayOrder()).append("==>").append(permission.getDisplayOrder()).append("，");
            }

            //备注
            if (!equalsString(permission.getRemark(), oldPermission.getRemark())) {
                log.append("备注：").append(oldPermission.getRemark()).append("==>").append(permission.getRemark()).append("，");
            }

        }

        //状态
        if (isUpdateStatus && !equalsBoolean(permission.getStatus(), oldPermission.getStatus())) {
            log.append("状态：").append(oldPermission.getStatus() ? "启用" : "禁用").append("==>").append(permission.getStatus() ? "启用" : "禁用").append("，");
        }

        String string = log.toString();
        if (StringUtils.isBlank(string)) {
            return "";
        } else {
            StringBuffer data = new StringBuffer();
            data.append("修改了权限【 ").append(oldPermission.getName()).append(" 】").append("==>【 ").append(string.substring(0, string.lastIndexOf("，"))).append(" 】");
            return data.toString();
        }
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/17 23:47
     * 描述 : 获取数据量操作日志
     *
     * @param permission 添加的权限
     * @return
     */
    private String getDBLog(PermissionBO permission) {
        StringBuffer log = new StringBuffer();
        log.append("添加了权限【 ")
                .append("所属菜单=").append(menuMapper.getMenuNameByMenuID(permission.getMenuId())).append("，")
                .append("权限名称=").append(permission.getName()).append("，")
                .append("url=").append(permission.getUrl()).append("，")
                .append("排序码=").append(permission.getDisplayOrder()).append("，")
                .append("备注=").append(permission.getRemark()).append(" 】");
        return log.toString();
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/17 23:03
     * 描述 : 检测参数
     *
     * @param permission     权限
     * @param oldPermission  数据库存储权限
     * @param isUpdateStatus 是否是修改状态
     */
    private void checkPermission(PermissionBO permission, PermissionBO oldPermission, boolean isUpdateStatus) {

        if (!isUpdateStatus) {
            //检测菜单id
            if (permission.getMenuId() == null) {
                throw new MyException(ResultEnum.PERMISSION_MENU_ID_IS_NULL);
            } else if (!checkMenuId(permission.getMenuId())) {

            }

            //检测权限名称
            if (StringUtils.isBlank(permission.getName())) {
                throw new MyException(ResultEnum.PERMISSION_NAME_IS_IS_NULL);
            } else if (!checkPermissionName(permission.getName(), permission.getMenuId(), oldPermission == null ? null : permission.getId())) {
                throw new MyException(ResultEnum.PERMISSION_NAME_IS_IS_EXIST);
            }

            //检测权限url
            if (StringUtils.isBlank(permission.getUrl())) {
                throw new MyException(ResultEnum.PERMISSION_URL_IS_NULL);
            } else if (!checkPermissionUrl(permission.getUrl(), oldPermission == null ? null : permission.getId())) {
                throw new MyException(ResultEnum.PERMISSION_URL_IS_EXIST);
            }

            //检测排序码
            if (permission.getDisplayOrder() == null) {
                throw new MyException(ResultEnum.PERMISSION_DISPLAY_ORDER_IS_NULL);
            }
        } else {
            if (permission.getStatus() == null) {
                throw new MyException(ResultEnum.PERMISSION_STATUS_IS_NULL);
            }
        }

        //检测更新时间
        if (oldPermission != null) {
            checkUpdateTime(permission.getUpdateTime(), oldPermission.getUpdateTime());
        }
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/18 0:53
     * 描述 : 检测所属菜单
     *
     * @param menuId 菜单ID
     * @return
     */
    private Boolean checkMenuId(Integer menuId) {
        return menuMapper.getHasChildrenByID(menuId);
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/17 23:34
     * 描述 : 检测权限url
     *
     * @param url       url
     * @param excludeId 需要排除的Id
     * @return
     */
    private boolean checkPermissionUrl(String url, Integer excludeId) {

        int count = permissionMapper.countBUrl(url, excludeId);

        return count == 0;
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/17 23:25
     * 描述 : 检测权限名称
     *
     * @param name      权限名称
     * @param menuId    所属菜单ID
     * @param excludeId 需要排除的Id
     * @return
     */
    private boolean checkPermissionName(String name, Integer menuId, Integer excludeId) {

        int count = permissionMapper.countByNameAndMenuId(name, menuId, excludeId);

        return count == 0;
    }
}
