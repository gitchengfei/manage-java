package com.cheng.manage.service.quartz.file;

import com.cheng.manage.common.date.DateUtils;
import com.cheng.manage.constant.app.system.db.log.DBLogConstant;
import com.cheng.manage.entity.file.FileBO;
import com.cheng.manage.entity.file.UnusedFileBO;
import com.cheng.manage.service.base.BaseService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author: cheng fei
 * @Date: 2019/6/8 10:54
 * @Description: 文件动删除实现类
 */
@Service
public class AutoDeleteFileServiceImpl extends BaseService implements AutoDeleteFileService{

    private static final String DB_LOG_TYPE = "SYSTEM_DB_LOG_TYPE_FILE";

    @Value("${delete.file.before.day}")
    private int deleteFileBeforeDay;

    @Override
    public void doDeleteUnUsedFile() {
        logger.debug("执行自动清理文件开始...");
        String deleteDay = null;
        try {
            deleteDay  = DateUtils.dateFormat(DateUtils.dateAdd(new Date(), deleteFileBeforeDay, false), DateUtils.DATE_PATTERN);
        } catch (Exception e) {
            logger.error("获取删除时间异常:" + e.getMessage(), e);
            e.printStackTrace();
        }

        List<UnusedFileBO> unusedFiles = unusedFileMapper.getDeleteList(deleteDay);
        for (UnusedFileBO unusedFile : unusedFiles) {

            //获取文件地址
            FileBO fileBO = fileMapper.selectByPrimaryKey(unusedFile.getFileId());

            logger.debug("删除了文件：file=【 {} 】", fileBO);

            //删除未使用表记录
            int i = unusedFileMapper.deleteByPrimaryKey(unusedFile.getId());
            checkDbDelete(i);

            //删除文件表信息
            i = fileMapper.deleteByPrimaryKey(unusedFile.getFileId());
            checkDbDelete(i);

            //删除阿里云上文件
            aliyunOSSClientUtil.deleteFile(fileBO.getPath());

            saveDBLog(DBLogConstant.SYSTEM_ACCOUNT_ID, "删除了文件【 " + fileBO.getFileName() + " 】", "DB_LOG_TYPE");
        }
        logger.debug("执行自动清理文件结束...");
    }
}
