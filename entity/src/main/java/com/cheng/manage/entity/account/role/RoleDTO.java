package com.cheng.manage.entity.account.role;

/**
 * @Author: cheng fei
 * @Date: 2019/4/27 14:32
 * @Description:
 */
public class RoleDTO extends RoleBO{

    /**
     * 创建人名
     */
    private String createName;

    /**
     * 修改人名
     */
    private String updateName;

    public RoleDTO() {

    }

    public RoleDTO(RoleBO roleBO) {
        this.id = roleBO.getId();
        this.name = roleBO.getName();
        this.displayOrder = roleBO.getDisplayOrder();
        this.status = roleBO.getStatus();
        this.remark = roleBO.getRemark();
        this.createTime = roleBO.getCreateTime();
        this.createId = roleBO.getCreateId();
        this.updateTime = roleBO.getUpdateTime();
        this.updateId = roleBO.getUpdateId();
        this.reservedOne = roleBO.getReservedOne();
        this.reservedTwo = roleBO.getReservedTwo();
        this.reservedThree = roleBO.getReservedThree();
    }

    @Override
    public String toString() {
        return "RoleDTO{" +
                "createName='" + createName + '\'' +
                ", updateName='" + updateName + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", remark='" + remark + '\'' +
                ", createTime=" + createTime +
                ", createId=" + createId +
                ", updateTime=" + updateTime +
                ", updateId=" + updateId +
                ", reservedOne='" + reservedOne + '\'' +
                ", reservedTwo='" + reservedTwo + '\'' +
                ", reservedThree='" + reservedThree + '\'' +
                '}';
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
