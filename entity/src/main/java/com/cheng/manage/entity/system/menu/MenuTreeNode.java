package com.cheng.manage.entity.system.menu;

import java.util.List;

/**
 * @Author: cheng fei
 * @Date: 2019/5/5 17:52
 * @Description: 菜单树节点
 */
public class MenuTreeNode {

    /**
     * id
     */
    private  Integer id;

    /**
     * 菜单名称
     */
    private  String name;

    /**
     * 菜单地址
     */
    private  String path;

    /**
     * 子节点
     */
    private List<MenuTreeNode> children;

    public MenuTreeNode() {
    }

    public MenuTreeNode(MenuBO menuBO) {
        this.id = menuBO.getId();
        this.name = menuBO.getName();
        this.path = menuBO.getUrl();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<MenuTreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<MenuTreeNode> children) {
        this.children = children;
    }
}
