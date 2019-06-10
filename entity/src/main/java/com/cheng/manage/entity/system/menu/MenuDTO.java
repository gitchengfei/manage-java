package com.cheng.manage.entity.system.menu;

import java.util.List;

/**
 * @Description :
 * @Author : cheng fei
 * @Date : 2019/4/2 21:56
 */
public class MenuDTO extends MenuBO{

    /**
     * 创建人
     */
    private String createName;

    /**
     * 修改人
     */
    private String updateName;

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

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    @Override
    public String toString() {
        return "MenuDTO{" +
                "createName='" + createName + '\'' +
                ", updateName='" + updateName + '\'' +
                ", children=" + children +
                ", id=" + getId() +
                ", name='" + getName() + '\'' +
                ", url='" + getUrl() + '\'' +
                ", parentId=" + getParentId() +
                ", displayOrder=" + getDisplayOrder() +
                ", status=" + getStatus() +
                ", hasChildren=" + getHasChildren() +
                ", hasPermission=" + getHasPermission() +
                ", createTime=" + getCreateTime() +
                ", createId=" + getCreateId() +
                ", updateTime=" + getUpdateTime() +
                ", updateId=" + getUpdateId() +
                ", reservedOne='" + getReservedOne() + '\'' +
                ", reservedTwo='" + getReservedTwo() + '\'' +
                ", reservedThree='" + getReservedThree() + '\'' +
                '}';
    }
}
