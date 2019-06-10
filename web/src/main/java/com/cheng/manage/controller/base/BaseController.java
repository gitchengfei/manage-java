package com.cheng.manage.controller.base;

import com.cheng.manage.common.aliyun.AliyunOSSClientUtil;
import com.cheng.manage.common.exception.MyException;
import com.cheng.manage.common.json.JsonUtils;
import com.cheng.manage.common.result.Result;
import com.cheng.manage.common.result.ResultEnum;
import com.cheng.manage.entity.account.account.AccountBO;
import com.cheng.manage.service.account.account.AccountService;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.PrintWriter;

import static com.sun.deploy.perf.DeployPerfUtil.write;


/**
 * @Description :
 * @Author : cheng fei
 * @Date : 2019/3/26 00:57
 */
public class BaseController {

    /**
     * 日志
     */
    protected Logger logger = LoggerFactory.getLogger(BaseController.class);

    @Value("${spring.redis.app.cache.db}")
    protected int appCacheDb;

    @Value("${spring.redis.session.db}")
    protected int sessionDb;

    @Value("${session.expire}")
    protected int sessionExpire;



    /**
     * 作者: cheng fei
     * 创建日期: 2019/6/3 17:32
     * 描述 : 下载文件
     * @param response
     * @param inputStream
     * @param fileName
     */
    protected void download(HttpServletResponse response, InputStream inputStream, String fileName, String size) {
        PrintWriter writer = null;
        ServletOutputStream outputStream = null;
        try {
            if (inputStream == null) {
                response.setContentType("application/json;charset=UTF-8");
                writer = response.getWriter();
                writer.write(JsonUtils.objectToJson(Result.build(ResultEnum.FILE_IS_NOT_EXIST)));
            }

            response.setContentType("application/x-msdownload");
            response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes("UTF-8"), "ISO8859-1"));
            response.setHeader("Content-Length", size);

            outputStream = response.getOutputStream();

            byte[] data = new byte[1024 * 1024];
            int len = -1;
            while ((len = inputStream.read(data)) > -1) {
                outputStream.write(data, 0, len);
            }
        } catch (Exception e) {
             logger.error("下载文件异常:" + e.getMessage(), e);
             throw new MyException(ResultEnum.DOWNLOAD_FILE_ERROR);
        } finally {
            if (writer != null) {
                writer.flush();
                writer.close();
            }
            if (outputStream != null) {
                try {
                    outputStream.flush();
                    outputStream.close();
                } catch (Exception e) {
                     logger.error("关闭输出流异常:" + e.getMessage(), e);
                     throw new MyException(ResultEnum.CLOSE_OUTPUT_STREAM_ERROR);
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e) {
                    logger.error("关闭输入流异常:" + e.getMessage(), e);
                    throw new MyException(ResultEnum.CLOSE_INPUT_STREAM_ERROR);
                }
            }
        }

    }

}
