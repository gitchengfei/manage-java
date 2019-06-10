package com.cheng.manage.entity.account.role;

import javax.persistence.*;

@Table(name = "role_menu")
public class RoleMenuBO {
    /**
     * 主键
     */
    @Id
    private Integer id;

    /**
     * 菜单id
     */
    @Column(name = "menu_id")
    private Integer menuId;

    /**
     * 角色id
     */
    @Column(name = "role_id")
    private Integer roleId;

    public RoleMenuBO() {
    }

    public RoleMenuBO(Integer menuId, Integer roleId) {
        this.menuId = menuId;
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "RoleMenuBO{" +
                "id=" + id +
                ", menuId=" + menuId +
                ", roleId=" + roleId +
                '}';
    }

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取菜单id
     *
     * @return menu_id - 菜单id
     */
    public Integer getMenuId() {
        return menuId;
    }

    /**
     * 设置菜单id
     *
     * @param menuId 菜单id
     */
    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
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