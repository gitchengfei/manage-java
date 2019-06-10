package com.cheng.manage.entity.file;

import com.cheng.manage.entity.base.BaseBO;

import javax.persistence.*;

/**
 * @Author: cheng fei
 * @Date: 2019/6/2 0:13
 * @Description:  数据库中已使用文件表对用实体类
 */
@Table(name = "use_file")
public class UseFileBO extends BaseBO {

    /**
     * 文件Id
     */
    @Column(name = "file_id")
    private Integer fileId;

    /**
     * 使用表格名称
     */
    @Column(name = "table_name")
    private String tableName;

    /**
     * 引用文件记录Id
     */
    @Column(name = "column_id")
    private Integer columnId;

    /**
     * 获取文件Id
     *
     * @return file_id - 文件Id
     */
    public Integer getFileId() {
        return fileId;
    }

    /**
     * 设置文件Id
     *
     * @param fileId 文件Id
     */
    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    /**
     * 获取使用表格名称
     *
     * @return table_name - 使用表格名称
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * 设置使用表格名称
     *
     * @param tableName 使用表格名称
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     * 获取引用文件记录Id
     *
     * @return column_id - 引用文件记录Id
     */
    public Integer getColumnId() {
        return columnId;
    }

    /**
     * 设置引用文件记录Id
     *
     * @param columnId 引用文件记录Id
     */
    public void setColumnId(Integer columnId) {
        this.columnId = columnId;
    }

    @Override
    public String toString() {
        return "UseFileBO{" +
                "id=" + getId() +
                "fileId=" + fileId +
                ", tableName='" + tableName + '\'' +
                ", columnId=" + columnId +
                '}';
    }
}