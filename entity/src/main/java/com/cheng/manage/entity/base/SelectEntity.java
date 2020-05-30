package com.cheng.manage.entity.base;

import java.util.List;

/**
 * @Author: cheng fei
 * @Date: 2020/5/20 23:23
 * @Description: 下拉列表查询返回实体
 */
public class SelectEntity {

    /**
     * id
     */
    private Integer id;

    /**
     * name
     */
    private String name;

    /**
     * 子选项
     */
    private List<SelectEntity> children;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SelectEntity> getChildren() {
        return children;
    }

    public void setChildren(List<SelectEntity> children) {
        this.children = children;
    }
}
