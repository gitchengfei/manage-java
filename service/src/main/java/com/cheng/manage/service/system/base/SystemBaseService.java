package com.cheng.manage.service.system.base;

import com.cheng.manage.dao.system.menu.MenuMapper;
import com.cheng.manage.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description : system 模块BaseService
 * @Author : cheng fei
 * @Date : 2019/3/23 02:12
 */
public class SystemBaseService extends BaseService {

    @Autowired
    protected MenuMapper menuMapper;

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/21 18:32
     * 描述 : 获取菜单所有子菜单ID列表
     *
     * @param menuId      菜单ID
     * @param includeSelf 是否包含自己
     * @return
     */
    protected List<Integer> getMenuAllChildMenuIds(Integer menuId, boolean includeSelf, Boolean status) {

        List<Integer> menuIds = new ArrayList<>();

        if (includeSelf) {
            menuIds.add(menuId);
        }
        List<Integer> childMenuIds = menuMapper.getChildMenuIds(menuId, status);
        menuIds.addAll(childMenuIds);
        getMenuAllChildMenuIds(menuIds, childMenuIds, status);
        return menuIds;
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/21 18:55
     * 描述 : 递归查询子菜单ID
     * @param data
     * @param menuIds
     * @param status
     */
    private void getMenuAllChildMenuIds(List<Integer> data, List<Integer> menuIds, Boolean status) {
        for (Integer menuId : menuIds) {
            List<Integer> childMenuIds = menuMapper.getChildMenuIds(menuId, status);
            data.addAll(childMenuIds);
            getMenuAllChildMenuIds(data, childMenuIds, status);
        }
    }

}
