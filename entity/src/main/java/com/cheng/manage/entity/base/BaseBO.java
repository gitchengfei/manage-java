package com.cheng.manage.entity.base;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: cheng fei
 * @Date: 2019/5/3 01:24
 * @Description: 数据库对应实体类基础
 */
public class BaseBO implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 创建人ID
     */
    @Column(name = "create_id")
    private Integer createId;

    /**
     * 修改人Id
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 修改时间
     */
    @Column(name = "update_id")
    private Integer updateId;

    /**
     * 创建人名称
     */
    private String createName;

    /**
     * 修改人名称
     */
    private String updateName;

    @Override
    public String toString() {
        return "BaseBO{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", createId=" + createId +
                ", updateTime=" + updateTime +
                ", updateId=" + updateId +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateId() {
        return createId;
    }

    public void setCreateId(Integer createId) {
        this.createId = createId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Integer updateId) {
        this.updateId = updateId;
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
}
