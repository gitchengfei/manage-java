package com.cheng.manage.dao.file;

import com.cheng.manage.common.mapper.BaseMapper;
import com.cheng.manage.entity.file.UnusedFileBO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: cheng fei
 * @Date: 2019/6/1 20:05
 * @Description: 未使用文件Mapper
 */
@Repository
public interface UnusedFileMapper extends BaseMapper<UnusedFileBO> {

    /**
     * 作者: cheng fei
     * 创建日期: 2019/6/2 0:20
     * 描述 : 根据文件ID删除未使用记录
     * @param fileId 文件Id
     * @return
     */
    Integer deleteByFileId(@Param("fileId") Integer fileId);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/6/4 18:19
     * 描述 : 根据文id查询未使用文件
     * @param fileId 文件Id
     * @return
     */
    int countByFileId(@Param("fileId") Integer fileId);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/6/8 10:35
     * 描述 : 获取需要删除的未使用文件列表
     * @param deleteDay 删除日期
     * @return
     */
    List<UnusedFileBO> getDeleteList(String deleteDay);
}