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
        this.id = permission.getId();
        this.menuId = permission.getMenuId();
        this.name = permission.getName();
        this.url = permission.getUrl();
        this.displayOrder = permission.getDisplayOrder();
        this.status = permission.getStatus();
        this.remark = permission.getRemark();
        this.createId = permission.getCreateId();
        this.createTime = permission.getCreateTime();
        this.updateId = permission.getUpdateId();
        this.updateTime = permission.getUpdateTime();
        this.reservedOne = permission.getReservedOne();
        this.reservedTwo = permission.getReservedTwo();
        this.reservedThree = permission.getReservedThree();
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
                ", id=" + id +
                ", menuId=" + menuId +
                ", url='" + url + '\'' +
                ", name='" + name + '\'' +
                ", displayOrder=" + displayOrder +
                ", status=" + status +
                ", remark='" + remark + '\'' +
                ", createId=" + createId +
                ", createTime=" + createTime +
                ", updateId=" + updateId +
                ", updateTime=" + updateTime +
                ", reservedOne='" + reservedOne + '\'' +
                ", reservedTwo='" + reservedTwo + '\'' +
                ", reservedThree='" + reservedThree + '\'' +
                '}';
    }
}
