package com.cheng.manage.entity.system.menu;

import com.cheng.manage.entity.base.BaseBO;

import javax.persistence.Column;
import javax.persistence.Table;
/**
 * @Author: cheng fei
 * @Date: 2019/4/21 23:19
 * @Description: 数据库菜单表对应实体类
 */
@Table(name = "menu")
public class MenuBO extends BaseBO {

    /**
     * 菜单名
     */
    private String name;

    /**
     * 菜单转跳URL
     */
    private String url;

    /**
     * 上级菜单ID
     */
    @Column(name = "parent_id")
    private Integer parentId;

    /**
     * 菜单排序码
     */
    @Column(name = "display_order")
    private Integer displayOrder;

    /**
     * 状态:启用/禁用
     */
    private Boolean status;

    /**
     * 是否存在子菜单
     */
    @Column(name = "has_children")
    private Boolean hasChildren;

    /**
     * 是否配置了权限
     */
    @Column(name = "has_permission")
    private Boolean hasPermission;

    /**
     * 预留字段1
     */
    @Column(name = "reserved_one")
    private String reservedOne;

    /**
     * 预留字段2
     */
    @Column(name = "reserved_two")
    private String reservedTwo;

    /**
     * 预留字段3
     */
    @Column(name = "reserved_three")
    private String reservedThree;


    /**
     * 获取菜单名
     *
     * @return name - 菜单名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置菜单名
     *
     * @param name 菜单名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取菜单转跳URL
     *
     * @return url - 菜单转跳URL
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置菜单转跳URL
     *
     * @param url 菜单转跳URL
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 获取上级菜单ID
     *
     * @return parent_id - 上级菜单ID
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * 设置上级菜单ID
     *
     * @param parentId 上级菜单ID
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取菜单排序码
     *
     * @return display_order - 菜单排序码
     */
    public Integer getDisplayOrder() {
        return displayOrder;
    }

    /**
     * 设置菜单排序码
     *
     * @param displayOrder 菜单排序码
     */
    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    /**
     * 获取状态:启用/禁用
     *
     * @return status - 状态:启用/禁用
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     * 设置状态:启用/禁用
     *
     * @param status 状态:启用/禁用
     */
    public void setStatus(Boolean status) {
        this.status = status;
    }

    /**
     * 获取是否存在子菜单
     *
     * @return has_children - 是否存在子菜单
     */
    public Boolean getHasChildren() {
        return hasChildren;
    }

    /**
     * 设置是否存在子菜单
     *
     * @param hasChildren 是否存在子菜单
     */
    public void setHasChildren(Boolean hasChildren) {
        this.hasChildren = hasChildren;
    }

    /**
     * 获取是否配置了权限
     *
     * @return has_children - 是否配置了权限
     */
    public Boolean getHasPermission() {
        return hasPermission;
    }

    /**
     * 设置是否配置了权限
     *
     * @param hasPermission 是否配置了权限
     */
    public void setHasPermission(Boolean hasPermission) {
        this.hasPermission = hasPermission;
    }

    /**
     * 获取预留字段1
     *
     * @return reserved_one - 预留字段1
     */
    public String getReservedOne() {
        return reservedOne;
    }

    /**
     * 设置预留字段1
     *
     * @param reservedOne 预留字段1
     */
    public void setReservedOne(String reservedOne) {
        this.reservedOne = reservedOne;
    }

    /**
     * 获取预留字段2
     *
     * @return reserved_two - 预留字段2
     */
    public String getReservedTwo() {
        return reservedTwo;
    }

    /**
     * 设置预留字段2
     *
     * @param reservedTwo 预留字段2
     */
    public void setReservedTwo(String reservedTwo) {
        this.reservedTwo = reservedTwo;
    }

    /**
     * 获取预留字段3
     *
     * @return reserved_three - 预留字段3
     */
    public String getReservedThree() {
        return reservedThree;
    }

    /**
     * 设置预留字段3
     *
     * @param reservedThree 预留字段3
     */
    public void setReservedThree(String reservedThree) {
        this.reservedThree = reservedThree;
    }

    @Override
    public String toString() {
        return "MenuBO{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", parentId=" + parentId +
                ", displayOrder=" + displayOrder +
                ", status=" + status +
                ", hasChildren=" + hasChildren +
                ", hasPermission=" + hasPermission +
                ", createTime=" + getCreateTime() +
                ", createId=" + getCreateId() +
                ", updateTime=" + getUpdateTime() +
                ", updateId=" + getUpdateId() +
                ", reservedOne='" + reservedOne + '\'' +
                ", reservedTwo='" + reservedTwo + '\'' +
                ", reservedThree='" + reservedThree + '\'' +
                '}';
    }
}
