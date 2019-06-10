package com.cheng.manage.constant.app.system.menu;

/**
 * @Description : 菜单常量
 * @Author : cheng fei
 * @Date : 2019/3/28 21:49
 */
public class MenuConstant {

    /**
     * 父级菜单的默认url
     */
    public static final String PARENT_MENU_URL = "#";

    /**
     * 顶级菜单的父节点ID
     */
    public static final int TOP_PARENT_ID = 0;

    /**
     * 菜单权限树菜单id的前缀
     */
    public static final String MENU_PERMISSION_TREE_MENU_PRE = "m";

    /**
     * 菜单权限树权限id的前缀
     */
    public static final String MENU_PERMISSION_TREE_PERMISSION_PRE = "p";

    /**
     * 菜单树在redis的缓存key
     */
    public static final String MENU_TREE_KEY = "menu_tree_key";
}
