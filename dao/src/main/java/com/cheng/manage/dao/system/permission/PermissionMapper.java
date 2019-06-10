package com.cheng.manage.dao.system.permission;

import com.cheng.manage.common.mapper.BaseMapper;
import com.cheng.manage.entity.system.permission.PermissionBO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
@Repository
public interface PermissionMapper extends BaseMapper<PermissionBO> {

    /**
     * 作者: cheng fei
     * 创建日期: 2019/3/23 3:09
     * 描述 : 通过ID获取权限URL
     *
     * @param id id
     * @return
     */
    String getUrlByID(@Param("id")Integer id);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/17 23:28
     * 描述 : 通过权限名和菜单ID查询权限总数
     *
     * @param name
     * @param menuId
     * @param excludeId
     * @return
     */
    int countByNameAndMenuId(@Param("name")String name, @Param("menuId")Integer menuId, @Param("excludeId")Integer excludeId);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/17 23:35
     * 描述 : 通过权限url查询权限总数
     *
     * @param url
     * @param excludeId
     * @return
     */
    int countBUrl(@Param("url")String url, @Param("excludeId")Integer excludeId);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/18 0:13
     * 描述 : 通过ID查询修改时间
     *
     * @param id
     * @return
     */
    Date getUpdateTimeByID(@Param("id")Integer id);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/18 0:35
     * 描述 : 通过菜单ID查询权限列表
     *
     * @param menuId 菜单id
     * @param status 状态:全部/启用/禁用 null/true/false
     * @return
     */
    List<PermissionBO> getPermissionListByMenuId(@Param("menuId")Integer menuI, @Param("status") Boolean status);


    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/21 22:31
     * 描述 : 通过菜单id列表获取权限列表
     * @param menuIds
     * @param orderBy
     * @return
     */
    List<PermissionBO> getPermissionListByMenuIds(@Param("menuIds")List<Integer> menuIds, @Param("orderBy") String orderBy);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/18 0:51
     * 描述 : 根据ID查询权限名称
     *
     * @param id
     * @return
     */
    String getNameByID(@Param("id")Integer id);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/30 2:58
     * 描述 : 通过菜单Id查询权限总数
     * @param menuId 菜单Id
     * @return
     */
    int countByMenuId(@Param("menuId") Integer menuId);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/5/16 17:18
     * 描述 : 获取超级管理员所有权限
     * @return
     */
    List<String> getAllPermissionId();
}
