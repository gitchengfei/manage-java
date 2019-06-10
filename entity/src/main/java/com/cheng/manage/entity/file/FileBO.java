package com.cheng.manage.entity.file;

import com.cheng.manage.entity.base.BaseBO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import javax.persistence.*;
/**
 * @Author: cheng fei
 * @Date: 2019/6/1 19:40
 * @Description: 数据库文件表对应实体类
 */
@Table(name = "file")
public class FileBO extends BaseBO {

    /**
     * 文件名称
     */
    @Column(name = "file_name")
    private String fileName;

    /**
     * 文件上传地址
     */
    private String path;

    /**
     * 文件扩展名
     */
    @Column(name = "file_extension")
    private String fileExtension;

    /**
     * 文件类型
     */
    @Column(name = "file_type")
    private String fileType;

    /**
     * 类型(引用数据字典中的固定编码)
     */
    private String type;

    /**
     * 文件用途(引用数据字典固定编码)
     */
    @Column(name = "file_purpose")
    private String filePurpose;

    /**
     * 文件大小
     */
    private String size;

    /**
     * 文件时长(视频文件使用)
     */
    private String time;

    /**
     * 是否删除
     */
    @Column(name = "is_delete")
    private Boolean delete;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 创建人
     */
    @Column(name = "create_id")
    private Integer createId;

    /**
     * 修改时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 修改人
     */
    @Column(name = "update_id")
    private Integer updateId;

    /**
     * 获取文件名称
     *
     * @return file_name - 文件名称
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * 设置文件名称
     *
     * @param fileName 文件名称
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * 获取文件上传地址
     *
     * @return path - 文件上传地址
     */
    public String getPath() {
        return path;
    }

    /**
     * 设置文件上传地址
     *
     * @param path 文件上传地址
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * 获取文件扩展名
     *
     * @return file_extension - 文件扩展名
     */
    public String getFileExtension() {
        return fileExtension;
    }

    /**
     * 设置文件扩展名
     *
     * @param fileExtension 文件扩展名
     */
    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    /**
     * 获取文件类型
     *
     * @return file_type - 文件类型
     */
    public String getFileType() {
        return fileType;
    }

    /**
     * 设置文件类型
     *
     * @param fileType 文件类型
     */
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    /**
     * 获取类型(引用数据字典中的固定编码)
     *
     * @return type - 类型(引用数据字典中的固定编码)
     */
    public String getType() {
        return type;
    }

    /**
     * 设置类型(引用数据字典中的固定编码)
     *
     * @param type 类型(引用数据字典中的固定编码)
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取文件用途(引用数据字典固定编码)
     * @return filePurpose 文件用途(引用数据字典固定编码)
     */
    public String getFilePurpose() {
        return filePurpose;
    }

    /**
     * 设置文件用途(引用数据字典固定编码)
     * @param filePurpose 文件用途(引用数据字典固定编码)
     */
    public void setFilePurpose(String filePurpose) {
        this.filePurpose = filePurpose;
    }

    /**
     * 获取文件大小
     *
     * @return size - 文件大小
     */
    public String getSize() {
        return size;
    }

    /**
     * 设置文件大小
     *
     * @param size 文件大小
     */
    public void setSize(String size) {
        this.size = size;
    }

    /**
     * 获取文件时长(视频文件使用)
     *
     * @return time - 文件时长(视频文件使用)
     */
    public String getTime() {
        return time;
    }

    /**
     * 设置文件时长(视频文件使用)
     *
     * @param time 文件时长(视频文件使用)
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * 获取是否删除
     * @return delete 是否删除
     */
    public Boolean getDelete() {
        return delete;
    }

    /**
     * 设置是否删除
     * @param delete 是否删除
     */
    public void setDelete(Boolean delete) {
        this.delete = delete;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取创建人
     *
     * @return create_id - 创建人
     */
    public Integer getCreateId() {
        return createId;
    }

    /**
     * 设置创建人
     *
     * @param createId 创建人
     */
    public void setCreateId(Integer createId) {
        this.createId = createId;
    }

    /**
     * 获取修改时间
     *
     * @return update_time - 修改时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置修改时间
     *
     * @param updateTime 修改时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取修改人
     *
     * @return update_id - 修改人
     */
    public Integer getUpdateId() {
        return updateId;
    }

    /**
     * 设置修改人
     *
     * @param updateId 修改人
     */
    public void setUpdateId(Integer updateId) {
        this.updateId = updateId;
    }

    @Override
    public String toString() {
        return "FileBO{" +
                "fileName='" + fileName + '\'' +
                ", path='" + path + '\'' +
                ", fileExtension='" + fileExtension + '\'' +
                ", fileType='" + fileType + '\'' +
                ", type='" + type + '\'' +
                ", filePurpose='" + filePurpose + '\'' +
                ", size='" + size + '\'' +
                ", time='" + time + '\'' +
                ", delete=" + delete +
                ", createTime=" + createTime +
                ", createId=" + createId +
                ", updateTime=" + updateTime +
                ", updateId=" + updateId +
                '}';
    }
}