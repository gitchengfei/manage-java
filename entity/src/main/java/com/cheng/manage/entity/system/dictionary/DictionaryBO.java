package com.cheng.manage.entity.system.dictionary;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @Author: cheng fei
 * @Date: 2019/4/21 23:16
 * @Description: 数据库数据库操作日志表对应实体类
 */
@Table(name = "dictionary")
public class DictionaryBO {

    /**
     * 数据字典表主键
     */
    @Id
    protected Integer id;

    /**
     * 名称
     */
    protected String name;

    /**
     * key
     */
    @Column(name = "code")
    protected String key;

    /**
     * value
     */
    protected String value;

    /**
     * 排序码
     */
    @Column(name = "display_order")
    protected Integer displayOrder;

    /**
     * 父节点
     */
    @Column(name = "parent_id")
    protected Integer parentId;

    /**
     * 状态:0不可用,1可用
     */
    protected Boolean status;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    protected Date createTime;

    /**
     * 创建人ID
     */
    @Column(name = "create_id")
    protected Integer createId;

    /**
     * 修改时间
     */
    @Column(name = "update_time")
    protected Date updateTime;

    /**
     * 修改人ID
     */
    @Column(name = "update_id")
    protected Integer updateId;

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
     * 获取数据字典表主键
     *
     * @return id - 数据字典表主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置数据字典表主键
     *
     * @param id 数据字典表主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

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
                "id=" + id +
                ", name='" + name + '\'' +
                ", key='" + key + '\'' +
                ", value='" + value + '\'' +
                ", displayOrder=" + displayOrder +
                ", parentId=" + parentId +
                ", status=" + status +
                ", createTime=" + createTime +
                ", createId=" + createId +
                ", updateTime=" + updateTime +
                ", updateId=" + updateId +
                ", reservedOne='" + reservedOne + '\'' +
                ", reservedTwo='" + reservedTwo + '\'' +
                ", reservedThree='" + reservedThree + '\'' +
                '}';
    }
}
