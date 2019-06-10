package com.cheng.manage.entity.system.dictionary;

/**
 * @Description :
 * @Author : cheng fei
 * @Date : 2019/4/11 00:07
 */
public class DictionaryDTO extends DictionaryBO{

    private String createName;

    private String updateName;

    public DictionaryDTO(DictionaryBO dictionary) {
        super();
        this.id = dictionary.getId();
        this.name = dictionary.getName();
        this.key = dictionary.getKey();
        this.value = dictionary.getValue();
        this.displayOrder = dictionary.getDisplayOrder();
        this.parentId = dictionary.getParentId();
        this.status = dictionary.getStatus();
        this.createTime = dictionary.getCreateTime();
        this.createId = dictionary.getCreateId();
        this.updateTime = dictionary.getUpdateTime();
        this.updateId = dictionary.getUpdateId();
        this.reservedOne = dictionary.getReservedOne();
        this.reservedTwo = dictionary.getReservedTwo();
        this.reservedThree = dictionary.getReservedThree();
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
        return "DictionaryDTO{" +
                "createName='" + createName + '\'' +
                ", updateName='" + updateName + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", key='" + key + '\'' +
                ", value='" + value + '\'' +
                ", displayOrder=" + displayOrder +
                ", parentId=" + parentId +
                ", status=" + status +
                ", createTime=" + createTime +
                ", createId=" + createId +
                ", updateTime=" + updateTime +
                ", updateId=" + updateId +
                ", reservedOne='" + reservedOne + '\'' +
                ", reservedTwo='" + reservedTwo + '\'' +
                ", reservedThree='" + reservedThree + '\'' +
                '}';
    }
}
