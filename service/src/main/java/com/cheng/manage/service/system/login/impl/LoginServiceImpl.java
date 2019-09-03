package com.cheng.manage.service.system.login.impl;

import com.cheng.manage.common.exception.MyException;
import com.cheng.manage.common.redis.JedisUtil;
import com.cheng.manage.common.result.Result;
import com.cheng.manage.common.result.ResultEnum;
import com.cheng.manage.common.util.AESCBCUtils;
import com.cheng.manage.common.json.JsonUtils;
import com.cheng.manage.constant.app.ApplicationConstant;
import com.cheng.manage.constant.app.system.login.LoginConstant;
import com.cheng.manage.dao.account.account.AccountMapper;
import com.cheng.manage.entity.account.account.AccountBO;
import com.cheng.manage.entity.account.account.AccountInfo;
import com.cheng.manage.service.system.base.SystemBaseService;
import com.cheng.manage.service.system.login.LoginService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;

import javax.sound.midi.Track;
import java.nio.charset.StandardCharsets;

/**
 * @Description : LoginService 接口实现类
 * @Author : cheng fei
 * @Date : 2019/3/22 00:24
 */

@Service
public class LoginServiceImpl extends SystemBaseService implements LoginService {

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private JedisUtil jedisUtil;

    @Value("${app.pc.key}")
    private String appPcKey;

    @Value("${app.mobile.key}")
    private String appMobileKey;

    @Override
    public Result getLoginAccount(String data, String key, String code) throws MyException {

        // 校验验证码
        Object serviceCode = jedisUtil.get(appCacheDb, key);
        if (serviceCode == null || !serviceCode.toString().equalsIgnoreCase(code)) {
            throw new MyException(ResultEnum.LOGIN_VERIFICATION_CODE_ERROR);
        }
        if (StringUtils.isBlank(data)) {
            throw new MyException(ResultEnum.PARAM_ERROR);
        }

        // 数据解包
        String vi = "";
        String username = "";
        int usernameLength = 0;

        try {
            BASE64Decoder dn = new BASE64Decoder();
            data = new String(dn.decodeBuffer(data), StandardCharsets.UTF_8);
            vi = data.substring(0, 16);
            usernameLength = Integer.parseInt(data.substring(16, 19));
            username = data.substring(data.length() - usernameLength);
        } catch (Exception e) {
            throw new MyException(ResultEnum.LOGIN_DATA_UNPACK_ERROR);
        }

        // 验证登录
        AccountBO account = accountMapper.getAccount(username, getSelectType(username));

        if (account == null) {
            throw new MyException(ResultEnum.LOGIN_NAME_OR_PASS_ERROR);
        }
        //检查账号状态是否禁用
        if(!account.getStatus()) {
            throw new MyException(ResultEnum.ACCOUNT_FORBIDDEN);
        }

        String str = data.substring(19, data.length() - usernameLength);
        AESCBCUtils aes = new AESCBCUtils(account.getPassword(), vi);
        String pc = aes.encrypt(appPcKey);
        String mobile = aes.encrypt(appMobileKey);
        String requestSource = "";
        if (str.equals(pc)) {
            requestSource = LoginConstant.REQUEST_SOURCE_PC;
        } else if (str.equals(mobile)) {
            requestSource = LoginConstant.REQUEST_SOURCE_MOBILE;
        } else {
            throw new MyException(ResultEnum.UNAUTHORIZED);
        }

        //账号验证通过,shiro登录
        jedisUtil.set(appCacheDb, LoginConstant.LOGIN_ACCOUNT_PRE + username, JsonUtils.objectToJson(account));
        UsernamePasswordToken token = new UsernamePasswordToken(account.getUsername(), account.getPassword());
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        session.setAttribute(LoginConstant.VALID_SESSION, null);
        session.setAttribute(LoginConstant.LOGIN_MSG, null);
        subject.login(token);
        //检测是否是重复登录,重复登录设置前登录账号被挤下线通知
        setSessionDisabled(requestSource, account.getId(),ResultEnum.ACCOUNT_LOST_CONNECTION);

        //将用户token存入reids,shirosession有效期是毫秒,这里需要秒
        jedisUtil.set(sessionDb, getAccountTokenPre(requestSource, account.getId()), session.getId().toString(), sessionExpire / 1000);
        if(subject.isAuthenticated()){
            logger.debug("[{}]已登录!", account);
            AccountInfo accountInfo = new AccountInfo();
            accountInfo.setToken(session.getId().toString());
            accountInfo.setHeadPortraitUrl(getFilePath(account.getHeadPortrait()));
            account.setPassword(null);
            accountInfo.setAccount(account);
            accountInfo.setRequestSource(requestSource);
            session.setAttribute(LoginConstant.ACCOUNT_INFO, JsonUtils.objectToJson(accountInfo));
            return Result.succee(accountInfo);
        }else{
            return Result.build(ResultEnum.LOGIN_ERROR);
        }
    }
}
