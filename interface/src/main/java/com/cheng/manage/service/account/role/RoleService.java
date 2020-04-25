package com.cheng.manage.service.account.role;

import com.cheng.manage.common.result.Result;
import com.cheng.manage.entity.account.role.RoleBO;

import java.util.List;

/**
 * @Description :
 * @Author : cheng fei
 * @Date : 2019/3/23 01:59
 */
public interface RoleService {

    /**
     * 作者: cheng fei
     * 创建日期: 2019/3/23 2:29
     * 描述 : 获取用户角色集合
     * @param username 用户名
     * @return
     */
    List<RoleBO> getRoleListByUsername(String username);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/23 22:08
     * 描述 : 添加/修改角色
     * @param roleBO 角色
     * @param isUpdateStatus 是否是修改状态
     * @return
     */
    Result saveOrUpdateRole(RoleBO roleBO, boolean isUpdateStatus);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/23 23:42
     * 描述 : 获取角色列表
     * @param name 权限名称(模糊查询)
     * @param roleCode 权限名称(模糊查询)
     * @param page 页码
     * @param pageSize 每页加载条数
     * @return
     */
    Result getRoleList(String name, String roleCode, Integer page, Integer pageSize);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/23 23:53
     * 描述 : 检测角色名称是否可用
     * @param name
     * @param excludeId
     * @return
     */
    Result checkName(String name, Integer excludeId);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/28 15:52
     * 描述 : 删除觉得
     * @param id 角色id
     * @return
     */
    Result deleteRole(Integer id);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/30 1:19
     * 描述 : 修改角色权限
     * @param id 角色id
     * @param permissions 权限列表
     * @return
     */
    Result updateRolePermission(Integer id, String permissions);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/30 2:02
     * 描述 : 查询角色权限列表
     * @param id 角色Id
     * @returnd
     */
    Result getRolePermission(Integer id);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/5/2 21:04
     * 描述 : 获取角色多选框数据
     * @return
     */
    Result getRoleCheckbox();

    /**
     * 作者: cheng fei
     * 创建日期: 2020/4/25 21:33
     * 描述 : 检测角色编码是否重复
     * @param code
     * @param excludeId
     * @return
     */
    Result checkRoleCode(String code, Integer excludeId);
}
