package com.cheng.manage.entity.account.role;

import com.cheng.manage.entity.base.BaseBO;

import javax.persistence.Column;
import javax.persistence.Table;
/**
 * @Author: cheng fei
 * @Date: 2019/4/23 22:05
 * @Description:  数据库角色表对应实体类
 */
@Table(name = "role")
public class RoleBO extends BaseBO {

    /**
     * 角色名
     */
    @Column(name = "name")
    private String name;


    @Column(name = "role_code")
    private String roleCode;

    /**
     * 排序码
     */
    @Column(name = "display_order")
    private Integer displayOrder;

    /**
     * 状态
     */
    private Boolean status;

    /**
     * 备注
     */
    private String remark;

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

    @Override
    public String toString() {
        return "RoleBO{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                ", roleCode='" + roleCode + '\'' +
                ", displayOrder='" + displayOrder + '\'' +
                ", status=" + status +
                ", createTime=" + getCreateTime() +
                ", createId=" + getCreateId() +
                ", updateTime=" + getUpdateTime() +
                ", updateId=" + getUpdateId() +
                ", reservedOne='" + reservedOne + '\'' +
                ", reservedTwo='" + reservedTwo + '\'' +
                ", reservedThree='" + reservedThree + '\'' +
                '}';
    }

    /**
     * 获取角色名
     *
     * @return name - 角色名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置角色名
     *
     * @param name 角色名
     */
    public void setName(String name) {
        this.name = name;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    /**
     * 获取排序码
     *
     * @return displayOrder - 排序码
     */
    public Integer getDisplayOrder() {
        return displayOrder;
    }

    /**
     *设置排序码
     *
     * @param displayOrder 排序码
     */
    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    /**
     * 获取状态
     *
     * @return status - 状态
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     * 设置状态
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
     * @param remark - 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
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
}