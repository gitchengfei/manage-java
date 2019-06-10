package com.cheng.manage.entity.system.dictionary;

/**
 * @Description : 数据字典下拉列表接收实体类
 * @Author : cheng fei
 * @Date : 2019/4/17 00:00
 */
public class Select {

    /**
     * key
     */
    private String key;
    /**
     * value
     */
    private String value;
    /**
     * 名称
     */
    private String name;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Select{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
