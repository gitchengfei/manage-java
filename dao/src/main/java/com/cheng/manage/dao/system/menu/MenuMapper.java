package com.cheng.manage.dao.system.menu;

import com.cheng.manage.common.mapper.BaseMapper;
import com.cheng.manage.entity.system.menu.MenuBO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface MenuMapper extends BaseMapper<MenuBO> {

    /**
     * 作者: cheng fei
     * 创建日期: 2019/3/28 23:29
     * 描述 : 查询菜单列表
     *
     * @param parentId 父菜单ID
     * @param status 菜单状态
     * @return
     */
    List<MenuBO> getMenuList(@Param("parentId")int parentId, @Param("status") Boolean status);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/1 1:57
     * 描述 : 查询菜单树列表
     *
     * @param parentId  父节点id
     * @param status  菜单状态
     * @param excludeId 需要排除的ID
     * @return
     */
    List<MenuBO> getMenuTreeList(@Param("parentId")int parentId, @Param("status") Boolean status, @Param("excludeId")int excludeId);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/3/28 23:30
     * 描述 : 查询菜单总数
     *
     * @param excludeId  需要排除的ID
     * @param url url
     * @return
     */
    int getCountByUrl(@Param("excludeId")Integer excludeId, @Param("url") String url);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/3/28 23:30
     * 描述 : 通过菜单id查询菜单名称
     *
     * @param id
     * @return
     */
    String getMenuNameByMenuID(@Param("id")Integer id);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/3/31 17:28
     * 描述 : 通过父节点ID查询菜单总数
     *
     * @param parentId 父节点id
     * @param status 状态: null/true/false 所有/启用/禁用
     * @return
     */
    int getCountByParentId(@Param("parentId")Integer parentId, @Param("status") Boolean status);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/3/31 17:28
     * 描述 : 通过父节点ID和名称查询菜单总数
     *
     * @param parentId 父节点id
     * @param name 菜单名称
     * @param excludeId 需要排除的id
     * @return
     */
    int getCountByParentIdAndName(@Param("parentId") Integer parentId, @Param("name") String name, @Param("excludeId")Integer excludeId);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/18 0:56
     * 描述 : 通过菜单ID查询是否是存在菜单
     * @param id
     * @return
     */
    Boolean getHasChildrenByID(Integer id);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/21 18:36
     * 描述 : 查询菜单所有子菜单
     * @param menuId 菜单ID
     * @param status 状态
     * @return
     */
    List<Integer> getChildMenuIds(@Param("menuId") Integer menuId, @Param("status") Boolean status);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/23 1:20
     * 描述 : 通过菜单ID查询修改时间
     * @param id
     * @return
     */
    Date getUpdateTimeById(@Param("id") Integer id);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/5/4 16:37
     * 描述 : 查询所有菜单Id
     * @return
     */
    List<Integer> getAllMenuId();

    /**
     * 作者: cheng fei
     * 创建日期: 2019/5/16 11:25
     * 描述 : 通过菜单Id查询菜单url
     * @param id id
     * @return
     */
    MenuBO getUrlAndDisplayOrderById(@Param("id") Integer id);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/5/22 16:51
     * 描述 : 通过菜单url当前菜单所有权限
     * @param url 菜单url
     * @return
     */
    List<String> getMenuPermissionByMenuUrl(@Param("url") String url);
}
