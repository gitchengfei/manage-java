package com.cheng.manage.dao.account.role;

import com.cheng.manage.common.mapper.BaseMapper;
import com.cheng.manage.entity.account.role.RoleMenuBO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: cheng fei
 * @Date: 2019/4/30 1:36
 * @Description:  角色菜单mapper
 */
@Repository
public interface RoleMenuMapper extends BaseMapper<RoleMenuBO> {

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/30 1:38
     * 描述 : 通过角色Id删除角色菜单表
     * @param roleId 角色id
     * @return
     */
    int deleteRoleMenuByRoleId(@Param("roleId") Integer roleId);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/30 2:05
     * 描述 : 通过角色ID查询角色拥有的菜单ID列表
     * @param roleId
     * @return
     */
    List<Integer> getRoleMneuByRoleId(@Param("roleId") Integer roleId);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/5/4 16:31
     * 描述 : 通过角色Id查询菜单id列表
     * @param roleId 角色id
     * @return
     */
    List<Integer> getRoleIdListByRoleId(@Param("roleId") Integer roleId);
}