package com.cheng.manage.quartz.file;

import com.cheng.manage.common.date.DateUtils;
import com.cheng.manage.dao.file.FileMapper;
import com.cheng.manage.dao.file.UnusedFileMapper;
import com.cheng.manage.entity.file.UnusedFileBO;
import com.cheng.manage.service.quartz.file.AutoDeleteFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import java.util.Date;
import java.util.List;


/**
 * @Author: cheng fei
 * @Date: 2019/6/8 10:17
 * @Description: 自动清理文件定时任务
 */
@Component
public class AutoDeleteFileQuartz {

    @Autowired
    private AutoDeleteFileService autoDeleteFileService;

    /**
     * 作者: cheng fei
     * 创建日期: 2019/6/8 10:32
     * 描述 : 删除未使用文件
     */
    public void doDeleteFile(){
        autoDeleteFileService.doDeleteUnUsedFile();
    }
}
