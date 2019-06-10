package com.cheng.manage.entity.account.account;

import javax.persistence.*;
/**
 * @Author: cheng fei
 * @Date: 2019/5/2 18:42
 * @Description: 数据库账号角色表对应实体类
 */
@Table(name = "account_role")
public class AccountRoleBO {
    /**
     * 用户角色表主键
     */
    @Id
    private Integer id;

    /**
     * 用户ID
     */
    @Column(name = "account_id")
    private Integer accountId;

    /**
     * 角色id
     */
    @Column(name = "role_id")
    private Integer roleId;

    public AccountRoleBO() {
    }

    public AccountRoleBO(Integer accountId, Integer roleId) {
        this.accountId = accountId;
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "AccountRoleBO{" +
                "id=" + id +
                ", accountId=" + accountId +
                ", roleId=" + roleId +
                '}';
    }

    /**
     * 获取用户角色表主键
     *
     * @return id - 用户角色表主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置用户角色表主键
     *
     * @param id 用户角色表主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取用户ID
     *
     * @return account_id - 用户ID
     */
    public Integer getAccountId() {
        return accountId;
    }

    /**
     * 设置用户ID
     *
     * @param accountId 用户ID
     */
    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    /**
     * 获取角色id
     *
     * @return role_id - 角色id
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     * 设置角色id
     *
     * @param roleId 角色id
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}