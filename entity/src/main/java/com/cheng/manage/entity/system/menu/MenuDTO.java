package com.cheng.manage.entity.system.menu;

import java.util.List;

/**
 * @Description :
 * @Author : cheng fei
 * @Date : 2019/4/2 21:56
 */
public class MenuDTO extends MenuBO{

    private List<MenuDTO> children;

    private MenuDTO(){

    }

    public MenuDTO(MenuBO menu) {
        setId(menu.getId());
        setName(menu.getName());
        setUrl(menu.getUrl());
        setParentId(menu.getParentId());
        setDisplayOrder(menu.getDisplayOrder());
        setStatus(menu.getStatus());
        setHasChildren(menu.getHasChildren());
        setHasPermission(menu.getHasPermission());
        setCreateTime(menu.getCreateTime());
        setCreateId(menu.getCreateId());
        setUpdateTime(menu.getUpdateTime());
        setUpdateId(menu.getUpdateId());
        setReservedOne(menu.getReservedOne());
        setReservedTwo(menu.getReservedTwo());
        setReservedThree(menu.getReservedThree());
    }

    public List<MenuDTO> getChildren() {
        return children;
    }

    public void setChildren(List<MenuDTO> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "MenuDTO{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", url='" + getUrl() + '\'' +
                ", parentId=" + getParentId() +
                ", displayOrder=" + getDisplayOrder() +
                ", children=" + children +
                ", status=" + getStatus() +
                ", hasChildren=" + getHasChildren() +
                ", hasPermission=" + getHasPermission() +
                ", createTime=" + getCreateTime() +
                ", createId=" + getCreateId() +
                ", createName='" + getCreateName() + '\'' +
                ", updateTime=" + getUpdateTime() +
                ", updateId=" + getUpdateId() +
                ", updateName='" + getUpdateName() + '\'' +
                ", reservedOne='" + getReservedOne() + '\'' +
                ", reservedTwo='" + getReservedTwo() + '\'' +
                ", reservedThree='" + getReservedThree() + '\'' +
                '}';
    }
}
