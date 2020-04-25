package com.cheng.manage.common.result;

import java.io.Serializable;

/**
 * @ClassName: ResultEnum
 * @Description:返回状态码枚举
 * @author: cheng fei
 * @date: 2018-05-25 21:48
 */
public enum ResultEnum implements Serializable {

    /**
     * 公共
     */
    SUCCESS(10000, 200, "success！"),
    PARAM_ERROR(10001, 500, "参数异常！"),
    NOT_PERMISSION(10002, 500, "没有操作权限！"),
    UNAUTHORIZED(10003,500, "未授权客服端！"),
    CHECK_UNAUTHORIZED_ERROR(10004,500,"授权检测异常!"),
    HTTP_TIME_OUT_HEADER_ERROR(10005, 500, "http验证请求超时请求头数据错误！"),
    HTTP_TIME_OUT(10006, 500, "http请求超时！"),
    DOWNLOAD_FILE_ERROR(10007, 500, "下载文件失败！"),
    CLOSE_OUTPUT_STREAM_ERROR(10008, 500, "关闭输出流异常！"),
    CLOSE_INPUT_STREAM_ERROR(10009, 500, "关闭输人流异常！"),

    /**
     * shiro相关
     */
    SHIRO_REDIS_SESSION_CREATE_ERROR(10100, 500, "shiro创建session异常！"),
    SHIRO_REDIS_SESSION_DELETE_ERROR(10101, 500, "shiro删除session异常！"),
    SHIRO_REDIS_SESSION_UPDATE_ERROR(10102, 500, "shiro更新session异常！"),
    SHIRO_REDIS_SESSION_SELECT_ERROR(10103, 500, "shiro获取session异常！"),
    SHIRO_REDIS_SESSION_SELECT_ACTIVE_ERROR(10104, 500, "shiro获取存活的session异常！"),
    SHIRO_REALM_AUTHORIZATION_ERROR(10105, 500, "shiro获取角色、权限异常！"),
    SHIRO_REALM_AUTHENTICATION_ERROR(10106, 500, "shiro身份认证异常！"),
    SHIRO_REDIS_CACHE_KEY_IS_NULL(10107, 500, "shiro redis缓存key为空！"),
    SHIRO_REDIS_CACHE_GET_ERROR(10108, 500, "shiro获取缓存异常！"),
    SHIRO_REDIS_CACHE_PUT_ERROR(10109, 500, "shiro添加缓存异常！"),
    SHIRO_REDIS_CACHE_REMOVE_ERROR(10110, 500, "shiro删除缓存异常！"),
    SHIRO_REDIS_CACHE_CLEAR_ERROR(10111, 500, "shiro清空缓存异常！"),
    SHIRO_REDIS_CACHE_SIZE_ERROR(10112, 500, "shiro获取缓存总数异常！"),
    SHIRO_REDIS_CACHE_KEY_SET_ERROR(10113, 500, "shiro获取key set异常！"),
    SHIRO_REDIS_CACHE_VULE_COLLECTION_ERROR(10114, 500, "shiro获取vaule collection异常！"),
    SHIRO_SESSION_MANAGER_GET_SESSION_ID_ERROR(10115, 500, "shiro获取sessionID异常！"),
    SHIRO_SESSION_MANAGER_RETRIEVE_SESSION_ERROR(10116, 500, "shiro获取session异常！"),

    /**
     * 登陆相关
     */
    NOT_LOG_IN(10200, 403, "未登录或登录超时！"),
    ACCOUNT_LOST_CONNECTION(10201, 500, "该账号已在其他地方登陆，请确认是否是本人操作！"),
    ACCOUNT_IS_DISABLED(10202, 500, "该账号已被管理员禁用！"),
    ACCOUNT_UPDATE_PASSWORD(10203, 500, "该账号已被管理员重置了密码请重新登陆！"),
    ACCOUNT_UPDATE_USERNAME(10204, 500, "该账号已被管理员修改了用户名请重新登陆！"),
    ACCOUNT_UPDATE_USERNAME_AND_PASSWORD(10205, 500, "该账号已被管理员修改了用户名和密码请重新登陆！"),
    LOGIN_VERIFICATION_CODE_ERROR(10206, 500, "验证码错误或验证码已过期！"),
    LOGIN_DATA_UNPACK_ERROR(10207, 500, "登录数据解包异常！"),
    LOGIN_NAME_OR_PASS_ERROR(10208, 404, "账户名或者密码错误！"),
    ACCOUNT_FORBIDDEN(10209, 500, "该账号已被禁用！"),
    LOGIN_ERROR(10210, 500, "登录失败!"),

    /**
     * 菜单相关
     */
    MENU_NAME_IS_NULL(10300, 500, "菜单名称不能为空！"),
    MENU_NAME_IS_EXIST(10301, 500, "菜单名称已存在！"),
    MENU_URL_IS_NULL(10302, 500, "菜单url不能为空！"),
    MENU_URL_IS_EXIST(10303, 500, "菜单url已存在！"),
    MENU_PARENT_ID_IS_NULL(10304, 500, "父菜单不能为空！"),
    MENU_DISPLAY_ORDER_IS_NULL(10305, 500, "菜单排序码不能为空！"),
    MENU_STATUS_IS_NULL(10306, 500, "菜单状态不能为空！"),
    MENU_GET_DB_LOG_ERROR(10307, 500, "生成菜单数据库操作日志异常！"),
    MENU_EXIST_CHILDREN_MENU(10308, 500, "该菜单下存在子菜单，请先删除子菜单！"),

    /**
     * 数据字典
     */
    DICTIONARY_ID_IS_NULL(10400, 500, "数据字典ID不能为空！"),
    DICTIONARY_NAME_IS_NULL(10401, 500, "数据字典名称不能为空！"),
    DICTIONARY_KEY_IS_NULL(10402, 500, "数据字典key不能为空！"),
    DICTIONARY_KEY_IS_EXIST(10403, 500, "数据字典key已存在！"),
    DICTIONARY_VALUE_IS_EXIST(10404, 500, "数据字典value已存在！"),
    DICTIONARY_NAME_IS_EXIST(10405, 500, "数据字典名称已存在！"),
    DICTIONARY_DISPLAY_ORDER_IS_NULL(10406, 500, "数据字典排序码不能为空！"),
    DICTIONARY_PARENT_ID_IS_NULL(10407, 500, "数据字典父节点ID不能为空！"),
    DICTIONARY_DELETE_EXIST_CHILDREN(10408, 500, "该数据字典存下在子节点,请先删除子节点！"),

    /**
     * 权限相关
     */
    PERMISSION_MENU_ID_IS_NULL(10500, 500, "权限所属菜单ID不能为空！"),
    PERMISSION_NAME_IS_IS_NULL(10501, 500, "权限名称不能为空！"),
    PERMISSION_NAME_IS_IS_EXIST(10502, 500, "权限名称已存在！"),
    PERMISSION_URL_IS_NULL(10503, 500, "权限url不能为空！"),
    PERMISSION_URL_IS_EXIST(10504, 500, "权限url已存在！"),
    PERMISSION_DISPLAY_ORDER_IS_NULL(10505, 500, "权限排序码不能为空！"),
    PERMISSION_STATUS_IS_NULL(10506, 500, "权限状态不能为空！"),

    /**
     * 角色相关
     */
    ROLE_NAME_IS_NULL(10600, 500, "角色名称不能为空！"),
    ROLE_NAME_IS_EXIST(10601, 500, "角色名称已存在！"),
    ROLE_CODE_IS_NULL(10602, 500, "角色比编码不能为空！"),
    ROLE_CODE_IS_EXIST(10603, 500, "角色编码已存在！"),
    ROLE_DISPLAY_ORDER_IS_NULL(10604, 500, "排序码不能为空！"),
    ROLE_STATUS_IS_NULL(10605, 500, "角色状态不能为空！"),


    /**
     * 账号相关
     */
    ACCOUNT_USERNAME_IS_NULL(10700, 500, "用户名不能为空！"),
    ACCOUNT_USERNAME_REGEX_ERROR(10701, 500, "用户名长度必须在4到16位，并且由字母，数字，下划线，减号组成，首位必须是字母！"),
    ACCOUNT_USERNAME_IS_EXIST(10702, 500, "用户名已存在！"),
    ACCOUNT_PASSWORD_IS_NULL(10703, 500, "密码不能为空！"),
    ACCOUNT_DECODER_PASSWORD_ERROR(10704, 500, "处理密码异常！"),
    ACCOUNT_PASSWORD_REGEX_ERROR(10705, 500, "密码长度必须在6-16位，并且同时包含字母数字特殊字符！"),
    ACCOUNT_NAME_IS_NULL(10706, 500, "姓名不能为空！"),
    ACCOUNT_PHONE_IS_NULL(10707, 500, "手机号不能为空！"),
    ACCOUNT_PHONE_FORMAT_ERROR(10708, 500, "手机号格式不正确！"),
    ACCOUNT_PHONE_IS_EXIST(10709, 500, "手机号已存在！"),
    ACCOUNT_EMAIL_IS_NULL(10710, 500, "邮箱不能为空！"),
    ACCOUNT_EMAIL_FORMAT_ERROR(107011, 500, "邮箱格式不正确！"),
    ACCOUNT_EMAIL_IS_EXIST(10712, 500, "邮箱已存在！"),
    ACCOUNT_STATUS_IS_NULL(10713, 500, "邮箱不能为空！"),
    ACCOUNT_ADMIN_CONT_UPDATE(10714, 500, "超级管理员账号不允许修改/删除！"),
    ACCOUNT_OLD_PASSWORD_IS_NULL(10715, 500,"账号旧密码不能为空！"),
    ACCOUNT_NEW_PASSWORD_IS_NULL(10716, 500,"账号新密码不能为空！"),
    ACCOUNT_OLD_PASSWORD_IS_ERROR(10717, 500,"旧密码错误！"),
    ACCOUNT_OLD_USERNAME_AND_OLD_PASSWORD_EQUAL(10718, 500,"账号用户名与密码与原数据一致,未重置！"),

    /**
     * 文件相关
     */
    FILE_URL_DECODER_ERROR(10800, 500, "url decoder 转码异常！"),
    FILE_IS_NOT_EXIST(10801, 500, "文件不存在！"),

    /**
     * 工具类相关
     */
    DATE_TRANSFORM_ERROR(80701, 500, "时间转换异常！"),

    /**
     * redis相关
     */
    REDIS_DEL_ERROR(80801, 500, "redis del异常！"),
    /**
     * 数据库相关
     */
    DB_SAVE_ERROR(80901, 500, "数据库添加数据异常！"),
    DB_DELETE_ERROR(80902, 500, "数据库删除数据异常！"),
    DB_UPDATE_ERROR(80903, 500, "数据库修改数据异常！"),
    DB_SELECT_ERROR(80904, 500, "数据库查询数据异常！"),
    DB_UPDATE_TIME_ERROR(80905, 500, "该条数据已被修改，请刷新后重新修改！"),
    DB_DATA_ERROR(80905, 500, "数据库数据异常！"),

    /**
     * 未定义错误
     */
    UNDEFINED_ERROR(90000, 500, "未定义的错误！");


    private Integer code;
    private Integer status;
    private String msg;

    private ResultEnum(Integer code, Integer status, String msg) {
        this.code = code;
        this.status = status;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public Integer getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }
}
