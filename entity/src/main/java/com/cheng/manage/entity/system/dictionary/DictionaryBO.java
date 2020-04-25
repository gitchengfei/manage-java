package com.cheng.manage.entity.system.dictionary;

import com.cheng.manage.entity.base.BaseBO;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @Author: cheng fei
 * @Date: 2019/4/21 23:16
 * @Description: 数据库数据库操作日志表对应实体类
 */
@Table(name = "dictionary")
public class DictionaryBO extends BaseBO {

    /**
     * 名称
     */
    private String name;

    /**
     * key
     */
    @Column(name = "code")
    private String key;

    /**
     * value
     */
    private String value;

    /**
     * 排序码
     */
    @Column(name = "display_order")
    private Integer displayOrder;

    /**
     * 父节点
     */
    @Column(name = "parent_id")
    private Integer parentId;

    /**
     * 状态:0不可用,1可用
     */
    private Boolean status;

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
     * 获取名称
     *
     * @return name - 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置名称
     *
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取key
     *
     * @return key - key
     */
    public String getKey() {
        return key;
    }

    /**
     * 设置key
     *
     * @param key key
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * 获取value
     *
     * @return value - value
     */
    public String getValue() {
        return value;
    }

    /**
     * 设置value
     *
     * @param value value
     */
    public void setValue(String value) {
        this.value = value;
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
     * 获取父节点
     *
     * @return parent_id - 父节点
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * 设置父节点
     *
     * @param parentId 父节点
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取状态:0不可用,1可用
     *
     * @return status - 状态:0不可用,1可用
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     * 设置状态:0不可用,1可用
     *
     * @param status 状态:0不可用,1可用
     */
    public void setStatus(Boolean status) {
        this.status = status;
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
        return "DictionaryBO{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                ", key='" + key + '\'' +
                ", value='" + value + '\'' +
                ", displayOrder=" + displayOrder +
                ", parentId=" + parentId +
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
}
