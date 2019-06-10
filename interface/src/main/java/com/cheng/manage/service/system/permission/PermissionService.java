package com.cheng.manage.service.system.permission;

import com.cheng.manage.common.result.Result;
import com.cheng.manage.entity.system.permission.PermissionBO;

import java.util.Set;

/**
 * @Description :
 * @Author : cheng fei
 * @Date : 2019/3/23 02:08
 */
public interface PermissionService {

    /**
     * 作者: cheng fei
     * 创建日期: 2019/3/23 2:50
     * 描述 : 通过角色code列表获取权限列表
     * @param roleIds 角色Id列表
     * @param username role code列表
     * @return
     */
    Set<String> getPermissionUrlsByRoleIds(Set<Integer> roleIds, String username);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/17 22:54
     * 描述 : 新增/修改权限
     * @param permission 权限
     * @param isUpdateStatus 是否是修改状态
     * @return
     */
    Result saveOrUpdatePermission(PermissionBO permission, boolean isUpdateStatus);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/18 0:27
     * 描述 : 查询权限列表
     * @param menuId 权限所属菜单
     * @param page 页码
     * @param pageSize 每页条数
     * @return
     */
    Result getPermissionList(Integer menuId, Integer page, Integer pageSize);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/18 0:46
     * 描述 : 删除权限
     * @param id
     * @return
     */
    Result deletePermission(Integer id);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/18 22:12
     * 描述 : 检测权限名称
     * @param menuId 菜单ID
     * @param name 权限名称
     * @param excludeId 需要排除的ID
     * @return
     */
    Result checkName(Integer menuId, String name, Integer excludeId);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/18 22:14
     * 描述 : 检测权限url
     * @param url 权限url
     * @param excludeId 需要排除的ID
     * @return
     */
    Result checkUrl(String url, Integer excludeId);
}
