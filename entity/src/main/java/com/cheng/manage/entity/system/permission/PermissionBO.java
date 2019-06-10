package com.cheng.manage.entity.system.permission;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
/**
 * @Author: cheng fei
 * @Date: 2019/4/21 19:01
 * @Description:  数据库权限表对应实体类
 */
@Table(name = "permission")
public class PermissionBO {
    /**
     * 自增长主键
     */
    @Id
    protected Integer id;

    /**
     * 权限所属菜单
     */
    @Column(name = "menu_id")
    protected Integer menuId;

    /**
     * 权限名
     */
    protected String name;

    /**
     * 该权限绑定的url
     */
    protected String url;

    /**
     * 排序码
     */
    @Column(name = "display_order")
    protected Integer displayOrder;

    /**
     * 状态:启用/禁用
     */
    protected Boolean status;

    /**
     * 备注
     */
    protected String remark;

    /**
     * 创建人ID
     */
    @Column(name = "create_id")
    protected Integer createId;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    protected Date createTime;

    /**
     * 修改人ID
     */
    @Column(name = "update_id")
    protected Integer updateId;

    /**
     * 修改时间
     */
    @Column(name = "update_time")
    protected Date updateTime;

    /**
     * 预留字段1
     */
    @Column(name = "reserved_one")
    protected String reservedOne;

    /**
     * 预留字段2
     */
    @Column(name = "reserved_two")
    protected String reservedTwo;

    /**
     * 预留字段3
     */
    @Column(name = "reserved_three")
    protected String reservedThree;

    /**
     * 获取自增长主键
     *
     * @return ID - 自增长主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置自增长主键
     *
     * @param id 自增长主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取权限所属菜单
     *
     * @return menu_id - 权限所属菜单
     */
    public Integer getMenuId() {
        return menuId;
    }

    /**
     * 设置权限所属菜单
     *
     * @param menuId 权限所属菜单
     */
    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    /**
     * 获取权限名
     *
     * @return name - 权限名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置权限名
     *
     * @param name 权限名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取该权限绑定的url
     *
     * @return url - 该权限绑定的url
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置该权限绑定的url
     *
     * @param url 该权限绑定的url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 获取排序码
     *
     * @return display_order - 排序码
     */
    public Integer getDisplayOrder() {
        return displayOrder;
    }

    /**
     * 设置排序码
     *
     * @param displayOrder 排序码
     */
    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    /**
     * 获取状态
     *
     * @return status 状态
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     * 设置排序码
     *
     * @param status 状态
     */
    public void setStatus(Boolean status) {
        this.status = status;
    }

    /**
     * 获取备注
     *
     * @return remark - 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取创建人ID
     *
     * @return create_id - 创建人ID
     */
    public Integer getCreateId() {
        return createId;
    }

    /**
     * 设置创建人ID
     *
     * @param createId 创建人ID
     */
    public void setCreateId(Integer createId) {
        this.createId = createId;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取修改人ID
     *
     * @return update_id - 修改人ID
     */
    public Integer getUpdateId() {
        return updateId;
    }

    /**
     * 设置修改人ID
     *
     * @param updateId 修改人ID
     */
    public void setUpdateId(Integer updateId) {
        this.updateId = updateId;
    }

    /**
     * 获取修改时间
     *
     * @return update_time - 修改时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置修改时间
     *
     * @param updateTime 修改时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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
        return "PermissionBO{" +
                "id=" + id +
                ", menuId=" + menuId +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", displayOrder=" + displayOrder +
                ", status=" + status +
                ", remark='" + remark + '\'' +
                ", createId=" + createId +
                ", createTime=" + createTime +
                ", updateId=" + updateId +
                ", updateTime=" + updateTime +
                ", reservedOne='" + reservedOne + '\'' +
                ", reservedTwo='" + reservedTwo + '\'' +
                ", reservedThree='" + reservedThree + '\'' +
                '}';
    }


}
