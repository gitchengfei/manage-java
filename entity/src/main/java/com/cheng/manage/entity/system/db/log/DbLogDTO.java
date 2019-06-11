package com.cheng.manage.entity.system.db.log;

/**
 * @Description : 数据操作日志查询结果集接受类
 * @Author : cheng fei
 * @Date : 2019/4/16 22:56
 */
public class DbLogDTO extends DbLogBO{

    /**
     * 操作人名称
     */
    private String accountName;
    /**
     * 数据库操作日志类型名称
     */
    private String typeName;
    /**
     * 日志
     */
    private String log;

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return "DbLogDTO{" +
                "accountName='" + accountName + '\'' +
                ", typeName='" + typeName + '\'' +
                ", log='" + log + '\'' +
                ", id=" + getId() +
                ", accountId=" + getAccountId() +
                ", createTime=" + getCreateTime() +
                ", type='" + getType() + '\'' +
                ", log='" + log + '\'' +
                ", reservedOne='" + getReservedOne() + '\'' +
                ", reservedTwo='" + getReservedTwo() + '\'' +
                ", reservedThree='" + getReservedThree() + '\'' +
                '}';
    }
}
