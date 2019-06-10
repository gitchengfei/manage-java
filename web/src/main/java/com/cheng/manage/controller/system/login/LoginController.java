package com.cheng.manage.controller.system.login;

import com.cheng.manage.common.exception.MyException;
import com.cheng.manage.common.redis.JedisUtil;
import com.cheng.manage.common.result.Result;
import com.cheng.manage.common.json.JsonUtils;
import com.cheng.manage.controller.system.base.SystemBaseController;
import com.cheng.manage.service.system.login.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description : 登陆Controller
 * @Author : cheng fei
 * @Date : 2019/3/22 00:16
 */
@RestController
@RequestMapping("system/login")
@Api(tags = "system.login.SystemBaseController", description = "系统-登陆模块")
public class LoginController extends SystemBaseController {


    @Autowired
    private LoginService loginService;

    @Autowired
    private JedisUtil jedisUtil;

    @Value("${session.expire}")
    private int sessionExpire;

    @PostMapping(value = "")
    @ApiOperation(value = "登陆接口", httpMethod = "POST", produces = "application/json")
    public Result doLogin(
            @ApiParam(value = "账户密码加密后的数据", required = true) String data,
            @ApiParam(value = "验证码", required = true) String code,
            @ApiParam(value = "验证码key", required = true) String key
    ) throws MyException {
        logger.debug("执行登陆,data=[{}], code=[{}], key=[{}]!", data, code, key);
        return loginService.getLoginAccount(data, key, code);

    }

    @PostMapping(value = "/logout")
    @ApiOperation(value = "登出接口", httpMethod = "POST", produces = "application/json")
    public Result doLogout() throws MyException {
        logger.debug("[{}]登出!");
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return Result.succee();
    }

}
