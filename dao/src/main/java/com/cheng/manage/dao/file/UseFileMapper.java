package com.cheng.manage.dao.file;

import com.cheng.manage.common.mapper.BaseMapper;
import com.cheng.manage.entity.file.UseFileBO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: cheng fei
 * @Date: 2019/6/2 0:17
 * @Description:  已使用文件mapper
 */

@Repository
public interface UseFileMapper extends BaseMapper<UseFileBO> {

    /**
     * 作者: cheng fei
     * 创建日期: 2019/6/2 0:25
     * 描述 : 删除文件引用
     * @param fileId 文件id
     * @param tableName 引用表名
     * @param columnId 引用记录Id
     * @return
     */
    int deleteByFileIdAndTableNameAndColumnId(@Param("fileId") Integer fileId, @Param("tableName") String tableName, @Param("columnId") Integer columnId);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/6/4 14:23
     * 描述 : 通过文件ID删除已使用文件数据
     * @param fileId 文件ID
     * @return
     */
    int deleteByFileId(@Param("fileId") int fileId);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/6/5 11:48
     * 描述 : 获取文件使用列表
     * @param fileId 文件Id
     * @return
     */
    List<UseFileBO> getUseFileByFileId(@Param("fileId") Integer fileId);
}