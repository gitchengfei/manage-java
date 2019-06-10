package com.cheng.manage.service.system.menu.impl;

import com.cheng.manage.common.result.Result;
import com.cheng.manage.entity.account.account.AccountBO;
import com.cheng.manage.entity.system.menu.MenuBO;

/**
 * @Description :
 * @Author : cheng fei
 * @Date : 2019/3/26 00:53
 */
public interface MenuService {

    /**
     * 作者: cheng fei
     * 创建日期: 2019/3/26 1:03
     * 描述 : 添加/修改菜单
     * @param menu 添加的菜单
     * @param isUpdateStatus 是否是修改菜单状态
     * @return
     */
    Result saveORUpdateMenu(MenuBO menu, boolean isUpdateStatus);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/3/26 2:24
     * 描述 : 查询菜单列表

     * @param parentID 父级菜单ID
     * @param status 菜单状态
     * @return
     */
    Result getMenuList(Integer parentID, Boolean status);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/3/28 21:44
     * 描述 : 检测URL
     * @param id 需要排序的id
     * @param url 菜单url
     * @return
     */
    Result checkURL(Integer id, String url);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/3/28 23:40
     * 描述 : 删除菜单
     * @param id 要删除的菜单ID
     * @return
     */
    Result deleteMenu(Integer id);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/3/31 17:38
     * 描述 : 检测菜单名,同级不能重复
     * @param id 需要排除的id
     * @param parentID 父节点ID
     * @param name 菜单名称
     * @return
     */
    Result checkName(Integer id, Integer parentID, String name);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/1 1:53
     * 描述 : 查询菜单树列表
     * @param parentId 父节点ID
     * @param status 菜单状态:true/false(不传或传""查询全部)
     * @param excludeId 需要排除的ID
     * @return
     */
    Result getMenuTreeList(Integer parentId, Boolean status, Integer excludeId);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/5 12:54
     * 描述 : 获取所有菜单树
     * @return
     */
    Result getAccountMenuAuthority();

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/29 22:24
     * 描述 : 加载菜单权限树
     * @return
     */
    Result getMenuPermissionTree();

    /**
     * 作者: cheng fei
     * 创建日期: 2019/5/22 16:22
     * 描述 : 获取账号菜单权限
     * @param url
     * @return
     */
    Result getAccountMenuPermission(String url);
}
