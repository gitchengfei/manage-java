package com.cheng.manage.entity.file;

import com.cheng.manage.entity.base.BaseBO;

import java.util.Date;
import javax.persistence.*;

/**
 * @Author: cheng fei
 * @Date: 2019/6/1 19:52
 * @Description:  数据库未使用文件表对应实体类
 */

@Table(name = "unused_file")
public class UnusedFileBO extends BaseBO {

    /**
     * 文件Id
     */
    @Column(name = "file_id")
    private Integer fileId;

    /**
     * 上传时间
     */
    @Column(name = "upload_time")
    private Date uploadTime;

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
     * 获取上传时间
     *
     * @return upload_time - 上传时间
     */
    public Date getUploadTime() {
        return uploadTime;
    }

    /**
     * 设置上传时间
     *
     * @param uploadTime 上传时间
     */
    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    @Override
    public String toString() {
        return "UnusedFileBO{" +
                "id=" + getId() +
                ", fileId=" + fileId +
                ", uploadTime=" + uploadTime +
                '}';
    }
}