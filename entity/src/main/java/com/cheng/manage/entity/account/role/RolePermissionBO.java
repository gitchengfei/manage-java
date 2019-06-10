package com.cheng.manage.entity.account.role;

import javax.persistence.*;

@Table(name = "role_permission")
public class RolePermissionBO {
    /**
     * 角色权限表
     */
    @Id
    private Integer id;

    /**
     * 角色id
     */
    @Column(name = "role_id")
    private Integer roleId;

    /**
     * 权限ID
     */
    @Column(name = "permission_id")
    private Integer permissionId;

    public RolePermissionBO() {
    }

    public RolePermissionBO(Integer roleId, Integer permissionId) {
        this.roleId = roleId;
        this.permissionId = permissionId;
    }

    @Override
    public String toString() {
        return "RolePermissionBO{" +
                "id=" + id +
                ", roleId=" + roleId +
                ", permissionId=" + permissionId +
                '}';
    }

    /**
     * 获取角色权限表
     *
     * @return id - 角色权限表
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置角色权限表
     *
     * @param id 角色权限表
     */
    public void setId(Integer id) {
        this.id = id;
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

    /**
     * 获取权限ID
     *
     * @return permission_id - 权限ID
     */
    public Integer getPermissionId() {
        return permissionId;
    }

    /**
     * 设置权限ID
     *
     * @param permissionId 权限ID
     */
    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }
}