package com.cheng.manage.entity.system.db.log;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "db_log")
public class DbLogBO {
    /**
     * 数据库操作日志表主键
     */
    @Id
    protected Long id;

    /**
     * 操作人
     */
    @Column(name = "account_id")
    protected Integer accountId;

    /**
     * 操作时间
     */
    @Column(name = "create_time")
    protected Date createTime;

    /**
     * 数据字典中数据库操作日志类型
     */
    protected String type;

    /**
     * 操作内容
     */
    protected String log;

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
     * 获取数据库操作日志表主键
     *
     * @return id - 数据库操作日志表主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置数据库操作日志表主键
     *
     * @param id 数据库操作日志表主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取操作人
     *
     * @return account_id - 操作人
     */
    public Integer getAccountId() {
        return accountId;
    }

    /**
     * 设置操作人
     *
     * @param accountId 操作人
     */
    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    /**
     * 获取操作时间
     *
     * @return create_time - 操作时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置操作时间
     *
     * @param createTime 操作时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取数据字典中数据库操作日志类型
     *
     * @return type - 数据字典中数据库操作日志类型
     */
    public String getType() {
        return type;
    }

    /**
     * 设置数据字典中数据库操作日志类型
     *
     * @param type 数据字典中数据库操作日志类型
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取操作内容
     *
     * @return log - 操作内容
     */
    public String getLog() {
        return log;
    }

    /**
     * 设置操作内容
     *
     * @param log 操作内容
     */
    public void setLog(String log) {
        this.log = log;
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
        return "DbLogBO{" +
                "id=" + id +
                ", accountId=" + accountId +
                ", createTime=" + createTime +
                ", type='" + type + '\'' +
                ", log='" + log + '\'' +
                ", reservedOne='" + reservedOne + '\'' +
                ", reservedTwo='" + reservedTwo + '\'' +
                ", reservedThree='" + reservedThree + '\'' +
                '}';
    }
}