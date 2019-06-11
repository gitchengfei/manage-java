package com.cheng.manage.entity.system.permission;

/**
 * @Description :
 * @Author : cheng fei
 * @Date : 2019/4/18 00:39
 */
public class PermissionDTO extends PermissionBO {


    private String createName;

    private String updateName;

    public PermissionDTO() {

    }

    public PermissionDTO(PermissionBO permission) {
        super();
        setId(permission.getId());
        setMenuId(permission.getMenuId());
        setName(permission.getName());
        setUrl(permission.getUrl());
        setDisplayOrder(permission.getDisplayOrder());
        setStatus(permission.getStatus());
        setRemark(permission.getRemark());
        setCreateId(permission.getCreateId());
        setCreateTime(permission.getCreateTime());
        setUpdateId(permission.getUpdateId());
        setUpdateTime(permission.getUpdateTime());
        setReservedOne(permission.getReservedOne());
        setReservedTwo(permission.getReservedTwo());
        setReservedThree(permission.getReservedThree());
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

    @Override
    public String toString() {
        return "PermissionDTO{" +
                "updateName='" + updateName + '\'' +
                ", id=" + getId() +
                ", menuId=" + getMenuId() +
                ", url='" + getUpdateName() + '\'' +
                ", name='" + getName() + '\'' +
                ", displayOrder=" + getDisplayOrder() +
                ", status=" + getStatus() +
                ", remark='" + getRemark() + '\'' +
                ", createId=" + getCreateId() +
                ", createTime=" + getCreateTime() +
                ", updateId=" + getUpdateId() +
                ", updateTime=" + getUpdateTime() +
                ", reservedOne='" + getReservedOne() + '\'' +
                ", reservedTwo='" + getReservedTwo() + '\'' +
                ", reservedThree='" + getReservedThree()+ '\'' +
                '}';
    }
}
