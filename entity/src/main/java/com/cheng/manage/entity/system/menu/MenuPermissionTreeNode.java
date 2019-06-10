package com.cheng.manage.entity.system.menu;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: cheng fei
 * @Date: 2019/4/29 22:31
 * @Description: 菜单权限树节点封装类
 */
public class MenuPermissionTreeNode implements Serializable {

    /**
     * 节点ID
     */
    private String id;
    /**
     * 节点名称
     */
    private String name;

    /**
     * 是否是菜单
     */
    private boolean menu;

    /**
     * 子节点
     */
    private List<MenuPermissionTreeNode> children;

    public MenuPermissionTreeNode() {
    }

    public MenuPermissionTreeNode(String id, String name, boolean menu) {
        this.id = id;
        this.name = name;
        this.menu = menu;
    }

    @Override
    public String toString() {
        return "MenuPermissionTreeNode{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", menu=" + menu +
                ", children=" + children +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isMenu() {
        return menu;
    }

    public void setMenu(boolean menu) {
        this.menu = menu;
    }

    public List<MenuPermissionTreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<MenuPermissionTreeNode> children) {
        this.children = children;
    }
}
