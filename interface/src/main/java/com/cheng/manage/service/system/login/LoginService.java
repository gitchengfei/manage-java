package com.cheng.manage.service.system.login;

import com.cheng.manage.common.exception.MyException;
import com.cheng.manage.common.result.Result;
import com.cheng.manage.entity.account.account.AccountBO;

/**
 * @Description :
 * @Author : cheng fei
 * @Date : 2019/3/22 00:23
 */
public interface LoginService {

    /**
     * 作者: cheng fei
     * 创建日期: 2019/3/23 0:38
     * 描述 : 获取登陆人员账号
     * @param data 登陆加密后的数据
     * @param key 验证码key
     * @param code 验证码
     * @return
     */
    Result getLoginAccount(String data, String key, String code);
}
