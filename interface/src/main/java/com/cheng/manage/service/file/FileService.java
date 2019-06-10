package com.cheng.manage.service.file;

import com.cheng.manage.common.result.Result;
import com.cheng.manage.entity.file.FileBO;

/**
 * @Author: cheng fei
 * @Date: 2019/6/1 18:53
 * @Description: 文件接口
 */
public interface FileService {

    /**
     * 作者: cheng fei
     * 创建日期: 2019/6/1 19:07
     * 描述 : 新增文件
     * @param uploadFileName 文件上传时的名称
     * @return
     */
    Result saveFile(String uploadFileName);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/6/2 16:42
     * 描述 : 查询文件列表
     * @param fileName 文件名称(模糊查询)
     * @param type  类型(数据字典中的类型)
     * @param filePurpose 文件用途(数据字典中的文件用途)
     * @param startTime 上传开始时间
     * @param endTime 上传结束时间
     * @param page 页码
     * @param pageSize 每页加载条数
     * @return
     */
    Result getFileList(String fileName, String type, String filePurpose, String startTime, String endTime, Integer page, Integer pageSize);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/6/3 17:06
     * 描述 : 修改文件名称
     * @param fileBO 修改的文件
     * @return
     */
    Result updateFileName(FileBO fileBO);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/6/3 17:19
     * 描述 : 下载文件
     * @param fileId 文件ID
     * @return
     */
    FileBO downloadFile(Integer fileId);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/6/4 14:12
     * 描述 : 删除文件
     * @param fileIds
     * @return
     */
    Result deleteFile(String fileIds);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/6/5 11:44
     * 描述 : 获取文件使用信息
     * @param fileId
     * @return
     */
    Result getFileUseInfo(Integer fileId);
}
