package com.cheng.manage.dao.file;

import com.cheng.manage.common.mapper.BaseMapper;
import com.cheng.manage.entity.file.FileBO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: cheng fei
 * @Date: 2019/6/1 19:44
 * @Description:  文件mapper
 */
@Repository
public interface FileMapper extends BaseMapper<FileBO> {

    /**
     * 作者: cheng fei
     * 创建日期: 2019/6/2 0:29
     * 描述 : 根据文件Id查询文件地址
     * @param fileId 文件Id
     * @return
     */
    String getPathById(@Param("fileId") Integer fileId);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/6/3 10:18
     * 描述 : 查询文件列表
     * @param fileName 文件名称（模糊查询）
     * @param type 类型
     * @param filePurpose 文件用途
     * @param startTime 开始上传时间
     * @param endTime 结束上传时间
     * @return
     */
    List<FileBO> getFileList(@Param("fileName") String fileName, @Param("type") String type, @Param("filePurpose") String filePurpose, @Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/6/8 15:42
     * 描述 : 根据文件Id修改文件为删除状态
     * @param id 文件Id
     * @return
     */
    int updateDeleteById(@Param("id") Integer id);

}