package com.cheng.manage.entity.account.role;

/**
 * @Author: cheng fei
 * @Date: 2019/4/27 14:32
 * @Description:
 */
public class RoleDTO extends RoleBO{

    public RoleDTO() {

    }

    public RoleDTO(RoleBO roleBO) {
        setId(roleBO.getId());
        setName(roleBO.getName());
        setRoleCode(roleBO.getRoleCode());
        setDisplayOrder(roleBO.getDisplayOrder());
        setStatus(roleBO.getStatus());
        setRemark(roleBO.getRemark());
        setCreateTime(roleBO.getCreateTime());
        setCreateId(roleBO.getCreateId());
        setUpdateTime(roleBO.getUpdateTime());
        setUpdateId(roleBO.getUpdateId());
        setReservedOne(roleBO.getReservedOne());
        setReservedTwo(roleBO.getReservedTwo());
        setReservedThree(roleBO.getReservedThree());
    }

    @Override
    public String toString() {
        return "RoleDTO{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", status=" + getStatus() +
                ", remark='" + getRemark() + '\'' +
                ", createTime=" + getCreateTime() +
                ", createId=" + getCreateId() +
                ", createName='" + getCreateName() + '\'' +
                ", updateTime=" + getUpdateTime() +
                ", updateId=" + getUpdateId() +
                ", updateName='" + getUpdateId() + '\'' +
                ", reservedOne='" + getReservedOne() + '\'' +
                ", reservedTwo='" + getReservedTwo() + '\'' +
                ", reservedThree='" + getReservedThree() + '\'' +
                '}';
    }
}
