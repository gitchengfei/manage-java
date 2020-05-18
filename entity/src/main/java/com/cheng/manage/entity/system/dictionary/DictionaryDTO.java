package com.cheng.manage.entity.system.dictionary;

/**
 * @Description :
 * @Author : cheng fei
 * @Date : 2019/4/11 00:07
 */
public class DictionaryDTO extends DictionaryBO{

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

    @Override
    public String toString() {
        return "DictionaryDTO{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", key='" + getKey() + '\'' +
                ", value='" + getValue() + '\'' +
                ", displayOrder=" + getDisplayOrder() +
                ", parentId=" + getParentId() +
                ", status=" + getStatus() +
                ", createTime=" + getCreateTime() +
                ", createId=" + getCreateId() +
                ", createName='" + getCreateName() + '\'' +
                ", updateTime=" + getUpdateTime() +
                ", updateId=" + getUpdateId() +
                ", updateName='" + getUpdateName() + '\'' +
                ", reservedOne='" + getReservedOne() + '\'' +
                ", reservedTwo='" + getReservedTwo() + '\'' +
                ", reservedThree='" + getReservedThree() + '\'' +
                '}';
    }
}
