package com.cheng.manage.entity.account.account;

import com.cheng.manage.entity.base.BaseBO;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;
/**
 * @Author: cheng fei
 * @Date: 2019/4/21 23:12
 * @Description: 数据库账号表对应的实体类
 */
@Table(name = "account")
public class AccountBO extends BaseBO {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 姓名
     */
    private String name;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 状态是否可用
     */
    private Boolean status;

    /**
     * 是否删除
     */
    @Column(name = "is_delete")
    private Boolean delete;

    /**
     * 账号头像
     */
    @Column(name = "head_portrait")
    private Integer headPortrait;

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
     * 修改人
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 修改人ID
     */
    @Column(name = "update_id")
    private Integer updateId;

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
     * 获取用户名
     *
     * @return username - 用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置用户名
     *
     * @param username 用户名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取密码
     *
     * @return password - 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取姓名
     *
     * @return name - 姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置姓名
     *
     * @param name 姓名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取手机号
     *
     * @return phone - 手机号
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置手机号
     *
     * @param phone 手机号
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取邮箱
     *
     * @return email - 邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置邮箱
     *
     * @param email 邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取状态是否可用
     *
     * @return status - 状态是否可用
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     * 设置状态是否可用
     *
     * @param status 状态是否可用
     */
    public void setStatus(Boolean status) {
        this.status = status;
    }

    /**
     * 获取是否删除
     * @return delete 是否删除
     */
    public Boolean getDelete() {
        return delete;
    }

    /**
     * 设置是否删除
     * @param delete 是否删除
     */
    public void setDelete(Boolean delete) {
        this.delete = delete;
    }


    /**
     * 获取账号头像
     * @return headPortrait 账号头像
     */
    public Integer getHeadPortrait() {
        return headPortrait;
    }

    /**
     * 设置账号头像
     * @param headPortrait 账号头像
     */
    public void setHeadPortrait(Integer headPortrait) {
        this.headPortrait = headPortrait;
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
     * 获取修改人
     *
     * @return update_time - 修改人
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置修改人
     *
     * @param updateTime 修改人
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
        super.toString();
        return "AccountBO{" +
                "id=" + getId() +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", status=" + status +
                ", delete=" + delete +
                ", headPortrait=" + headPortrait +
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
