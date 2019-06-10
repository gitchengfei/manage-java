package com.cheng.manage.constant.app.system.login;

/**
 * @Description : 登陆模块常量
 * @Author : cheng fei
 * @Date : 2019/3/23 02:21
 */
public class LoginConstant {


    /**
     * 请求来源 - pc端
     */
    public static final String REQUEST_SOURCE_PC = "pc";

    /**
     * 请求来源 - 移动端
     */
    public static final String REQUEST_SOURCE_MOBILE = "mobile";

    /**
     * 账号存储token的前缀
     */
    public static final String ACCOUNT_TOKEN_PRE = "account_token_pre:";

    /**
     * 账号存储session的前缀
     */
    public static final String ACCOUNT_SESSION_PRE = "account_session_pre:";

    /**
     * 用户登录校验后用户信息存放前缀
     */
    public static  final String LOGIN_ACCOUNT_PRE = "Login_account_pre:";

    /**
     * 用户session失效存入session属性中的key
     */
    public static final String LOGIN_MSG = "login_msg";

    /**
     * 严重session是否有效
     */
    public static final String VALID_SESSION = "valid_session";

    /**
     * 用户信息
     */
    public static final String ACCOUNT_INFO = "account_info";

    /**
     * session key link
     */
    public static final String SESSION_LINK = "_";
}
