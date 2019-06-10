package com.cheng.manage.dao.account.role;

import com.cheng.manage.common.mapper.BaseMapper;
import com.cheng.manage.entity.account.role.RolePermissionBO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: cheng fei
 * @Date: 2019/4/23 23:06
 * @Description:  角色权限Mapper
 */
@Repository
public interface RolePermissionMapper extends BaseMapper<RolePermissionBO> {

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/30 1:23
     * 描述 : 通过角色id删除权限
     * @param roleId 角色id
     * @return
     */
    int deleteRolePermissionByRoleId(@Param("roleId") Integer roleId);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/30 2:08
     * 描述 : 通过角色ID查询角色拥有的权限Url
     * @param roleId 角色ID
     * @return
     */
    List<String> getPermissionUrlByRoleId(@Param("roleId") Integer roleId);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/5/16 17:24
     * 描述 : 根据角色ID查询权限id列表
     * @param roleId 角色Id
     * @return
     */
    List<Integer> getPermissionIdyRoleId(@Param("roleId") Integer roleId);
}