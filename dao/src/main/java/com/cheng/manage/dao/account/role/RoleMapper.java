package com.cheng.manage.dao.account.role;

import com.cheng.manage.common.mapper.BaseMapper;
import com.cheng.manage.entity.account.role.RoleBO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @Author: cheng fei
 * @Date: 2019/4/23 22:10
 * @Description: 角色Mapper
 */
@Repository
public interface RoleMapper extends BaseMapper<RoleBO> {

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/23 22:23
     * 描述 : 通过姓名的查询角色总数
     * @param name
     * @param excludeId
     * @return
     */
    int countByName(@Param("name") String name, @Param("excludeId") Integer excludeId);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/23 23:46
     * 描述 : 查询角色列表
     * @param name 名称(模糊查询)
     * @return
     */
    List<RoleBO> getRoleList(@Param("name") String name);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/27 14:57
     * 描述 : 根据ID查询修改时间
     * @param id id
     * @return
     */
    Date getUpdateTimeById(@Param("id") Integer id);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/28 15:56
     * 描述 : 根据角色id查询角色名称
     * @param id id
     * @return
     */
    String getRoleNameById(@Param("id") Integer id);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/5/2 18:52
     * 描述 : 获取超级管理员角色列表
     * @return
     */
    List<RoleBO> getAllRole();

    /**
     * 作者: cheng fei
     * 创建日期: 2019/5/16 16:38
     * 描述 :获取超级管理员角色id列表
     * @return
     */
    List<Integer> getAllRoleId();
}