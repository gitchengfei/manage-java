package com.cheng.manage.service.quartz.file;

/**
 * @Author: cheng fei
 * @Date: 2019/6/8 10:55
 * @Description: 文件自动删除接口
 */
public interface AutoDeleteFileService {

    /**
     * 作者: cheng fei
     * 创建日期: 2019/6/8 11:02
     * 描述 : 删除未使用文件
     */
    void doDeleteUnUsedFile();
}
