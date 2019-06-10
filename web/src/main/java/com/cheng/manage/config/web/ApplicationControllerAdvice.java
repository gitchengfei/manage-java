package com.cheng.manage.config.web;

import com.cheng.manage.common.exception.MyException;
import com.cheng.manage.common.result.Result;
import com.cheng.manage.common.result.ResultEnum;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.session.UnknownSessionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;



/**
 * @Description : 全局异常处理
 * @Author : cheng fei
 * @Date : 2019/3/24 23:58
 */
@ControllerAdvice
public class ApplicationControllerAdvice {

    Logger logger = LoggerFactory.getLogger(ApplicationControllerAdvice.class);

    /**
     * 全局异常处理
     * @param e
     * @return
     */

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result exception(Exception e){
        logger.error("ApplicationControllerAdvice.exception==>", "全局异常处理了异常:" + e.getMessage(), e);
        e.printStackTrace();
        return Result.build(ResultEnum.UNDEFINED_ERROR);
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    public Result unauthorizedException(Exception e){
        logger.error("ApplicationControllerAdvice.unauthorizedException==>", "无权限异常:" + e.getMessage(), e);
        e.printStackTrace();
        return Result.build(ResultEnum.NOT_PERMISSION);
    }


    @ExceptionHandler(UnknownSessionException.class)
    public Result unknownSessionException(Exception e){
        logger.error("ApplicationControllerAdvice.unknownSessionException==>", "未找到session异常:" + e.getMessage(), e);
        e.printStackTrace();
        return Result.build(ResultEnum.NOT_LOG_IN);
    }

    @ExceptionHandler(MyException.class)
    @ResponseBody
    public Result myException(Exception e){
        MyException myException = (MyException) e;
        e.printStackTrace();
        return Result.build(myException.getResultEnum());
    }


}
