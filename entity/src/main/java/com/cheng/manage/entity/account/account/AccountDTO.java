package com.cheng.manage.entity.account.account;

import com.cheng.manage.entity.account.role.RoleBO;

import java.util.List;

/**
 * @Author: cheng fei
 * @Date: 2019/4/30 23:36
 * @Description:
 */
public class AccountDTO extends AccountBO {

    /**
     * 创建人
     */
    private String createName;

    /**
     * 修改人
     */
    private String updateName;

    /**
     * 角色列表
     */
    private List<RoleBO> roles;

    public AccountDTO() {
    }

    public AccountDTO(AccountBO accountBO) {
        this.setId(accountBO.getId());
        this.setUsername(accountBO.getUsername());
        this.setPassword(accountBO.getPassword());
        this.setName(accountBO.getName());
        this.setPhone(accountBO.getPhone());
        this.setEmail( accountBO.getEmail());
        this.setStatus(accountBO.getStatus());
        this.setDelete(accountBO.getDelete());
        this.setHeadPortrait(accountBO.getHeadPortrait());
        this.setCreateTime(accountBO.getCreateTime());
        this.setCreateId(accountBO.getCreateId());
        this.setUpdateTime(accountBO.getUpdateTime());
        this.setUpdateId(accountBO.getUpdateId());
        this.setReservedOne(accountBO.getReservedOne());
        this.setReservedTwo(accountBO.getReservedTwo());
        this.setReservedThree(accountBO.getReservedThree());
    }

    @Override
    public String toString() {
        return "AccountDTO{" +
                "id=" + getId() +
                ", username='" + getUpdateName() + '\'' +
                ", password='" + getPassword() + '\'' +
                ", name='" + getName() + '\'' +
                ", phone='" + getPhone() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", status=" + getStatus() +
                ", delete=" + getDelete() +
                ", headPortrait=" + getHeadPortrait() +
                ", createTime=" + getCreateTime() +
                ", createId=" + getCreateId() +
                ", updateTime=" + getUpdateTime() +
                ", updateId=" + getUpdateId() +
                ", reservedOne='" + getReservedOne() + '\'' +
                ", reservedTwo='" + getReservedTwo() + '\'' +
                ", reservedThree='" + getReservedThree() + '\'' +
                "createName='" + createName + '\'' +
                ", updateName='" + updateName + '\'' +
                ", roles=" + roles +
                '}';
    }

    public List<RoleBO> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleBO> roles) {
        this.roles = roles;
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
