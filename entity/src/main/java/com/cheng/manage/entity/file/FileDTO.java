package com.cheng.manage.entity.file;

import com.cheng.manage.entity.system.dictionary.DictionaryBO;

/**
 * @Author: cheng fei
 * @Date: 2019/6/3 10:40
 * @Description:
 */
public class FileDTO extends FileBO{

    /**
     * 是否引用
     */
    private Boolean used;

    /**
     * 创建人
     */
    private String createName;

    /**
     * 修改人
     */
    private String updateName;

    /**
     * 数据字段类型名称
     */
    private String TypeName;

    public FileDTO() {
    }

    public FileDTO(FileBO fileBO) {
        setId(fileBO.getId());
        setFileName(fileBO.getFileName());
        setPath(fileBO.getPath());
        setFileExtension(fileBO.getFileExtension());
        setFileType(fileBO.getFileType());
        setType(fileBO.getType());
        setSize(fileBO.getSize());
        setTime(fileBO.getTime());
        setDelete(fileBO.getDelete());
        setCreateTime(fileBO.getCreateTime());
        setCreateId(fileBO.getCreateId());
        setUpdateTime(fileBO.getUpdateTime());
        setUpdateId(fileBO.getUpdateId());
    }

    public Boolean getUsed() {
        return used;
    }

    public void setUsed(Boolean used) {
        this.used = used;
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

    public String getTypeName() {
        return TypeName;
    }

    public void setTypeName(String typeName) {
        TypeName = typeName;
    }

    @Override
    public String toString() {
        return "FileDTO{" +
                "id=" + getId() +
                ", fileName='" + getFileName() + '\'' +
                ", path='" + getPath() + '\'' +
                ", fileExtension='" + getFileExtension() + '\'' +
                ", fileType='" + getFileType() + '\'' +
                ", type='" + getType() + '\'' +
                ", filePurpose='" + getFilePurpose()+ '\'' +
                ", size='" + getSize() + '\'' +
                ", time='" + getTime() + '\'' +
                ", delete='" + getDelete() + '\'' +
                ", createTime=" + getCreateTime() +
                ", createId=" + getCreateId() +
                ", updateTime=" + getUpdateName() +
                ", updateId=" + getUpdateId() +
                ", used='" + used + '\'' +
                ", createName='" + createName + '\'' +
                ", updateName='" + updateName + '\'' +
                ", TypeName='" + TypeName + '\'' +
                '}';
    }
}
