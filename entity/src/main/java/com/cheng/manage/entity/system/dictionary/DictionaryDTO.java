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
        setId(dictionary.getId());
        setName(dictionary.getName());
        setKey(dictionary.getKey());
        setValue(dictionary.getValue());
        setDisplayOrder(dictionary.getDisplayOrder());
        setParentId(dictionary.getParentId());
        setStatus(dictionary.getStatus());
        setCreateTime(dictionary.getCreateTime());
        setCreateId(dictionary.getCreateId());
        setUpdateTime(dictionary.getUpdateTime());
        setUpdateId(dictionary.getUpdateId());
        setReservedOne(dictionary.getReservedOne());
        setReservedTwo(dictionary.getReservedTwo());
        setReservedThree(dictionary.getReservedThree());
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
                ", id=" + getId() +
                ", name='" + getName() + '\'' +
                ", key='" + getKey() + '\'' +
                ", value='" + getValue() + '\'' +
                ", displayOrder=" + getDisplayOrder() +
                ", parentId=" + getParentId() +
                ", status=" + getStatus() +
                ", createTime=" + getCreateTime() +
                ", createId=" + getCreateId() +
                ", updateTime=" + getUpdateTime() +
                ", updateId=" + getUpdateId() +
                ", reservedOne='" + getReservedOne() + '\'' +
                ", reservedTwo='" + getReservedTwo() + '\'' +
                ", reservedThree='" + getReservedThree() + '\'' +
                '}';
    }
}
