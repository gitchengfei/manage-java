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
        setId(roleBO.getId());
        setName(roleBO.getName());
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
                "createName='" + createName + '\'' +
                ", updateName='" + updateName + '\'' +
                ", id=" + getId() +
                ", name='" + getName() + '\'' +
                ", status=" + getStatus() +
                ", remark='" + getRemark() + '\'' +
                ", createTime=" + getCreateTime() +
                ", createId=" + getCreateId() +
                ", updateTime=" + getUpdateTime() +
                ", updateId=" + getUpdateId() +
                ", reservedOne='" + getReservedOne() + '\'' +
                ", reservedTwo='" + getReservedTwo() + '\'' +
                ", reservedThree='" + getReservedThree() + '\'' +
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
