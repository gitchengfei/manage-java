package com.cheng.manage.service.base;

import com.cheng.manage.common.aliyun.AliyunOSSClientUtil;
import com.cheng.manage.common.exception.MyException;
import com.cheng.manage.common.redis.JedisUtil;
import com.cheng.manage.common.result.Result;
import com.cheng.manage.common.result.ResultEnum;
import com.cheng.manage.common.util.RegexUtils;
import com.cheng.manage.common.json.JsonUtils;
import com.cheng.manage.constant.app.ApplicationConstant;
import com.cheng.manage.constant.app.account.role.RoleConstant;
import com.cheng.manage.constant.app.system.login.LoginConstant;
import com.cheng.manage.constant.app.system.permission.PermissionConstant;
import com.cheng.manage.dao.account.account.AccountMapper;
import com.cheng.manage.dao.account.account.AccountRoleMapper;
import com.cheng.manage.dao.account.role.RoleMapper;
import com.cheng.manage.dao.account.role.RoleMenuMapper;
import com.cheng.manage.dao.account.role.RolePermissionMapper;
import com.cheng.manage.dao.file.FileMapper;
import com.cheng.manage.dao.file.UnusedFileMapper;
import com.cheng.manage.dao.file.UseFileMapper;
import com.cheng.manage.dao.system.db.log.DbLogMapper;
import com.cheng.manage.dao.system.permission.PermissionMapper;
import com.cheng.manage.entity.account.account.AccountBO;
import com.cheng.manage.entity.account.account.AccountInfo;
import com.cheng.manage.entity.account.role.RoleBO;
import com.cheng.manage.entity.file.UnusedFileBO;
import com.cheng.manage.entity.file.UseFileBO;
import com.cheng.manage.entity.system.db.log.DbLogBO;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Mapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.SerializationUtils;

import java.util.*;

/**
 * @Description : 基础Service
 * @Author : cheng fei
 * @Date : 2019/3/23 02:11
 */
public class BaseService {

    protected Logger logger = LoggerFactory.getLogger(BaseService.class);

    @Autowired
    protected DbLogMapper dbLogMapper;

    @Autowired
    protected JedisUtil jedisUtil;

    @Autowired
    protected RolePermissionMapper rolePermissionMapper;

    @Autowired
    protected AccountMapper accountMapper;

    @Autowired
    protected AccountRoleMapper accountRoleMapper;

    @Autowired
    protected RoleMapper roleMapper;

    @Autowired
    protected RoleMenuMapper roleMenuMapper;

    @Autowired
    protected PermissionMapper permissionMapper;

    @Autowired
    protected FileMapper fileMapper;

    @Autowired
    protected AliyunOSSClientUtil aliyunOSSClientUtil;

    @Autowired
    protected UseFileMapper useFileMapper;

    @Autowired
    protected UnusedFileMapper unusedFileMapper;

    @Value("${system.super.administrator}")
    protected String systemSuperAdministrator;

    @Value("${session.expire}")
    protected int sessionExpire;

    @Value("${spring.redis.app.cache.db}")
    protected int appCacheDb;

    @Value("${spring.redis.session.db}")
    protected int sessionDb;

    @Value("${app.cache.expire}")
    protected int appCacheExpire;

    /**
     * 获取当前已登录用户所有信息
     *
     * @return
     */
    protected AccountInfo getLoginAccountInfo() {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        return JsonUtils.jsonToPojo((String) session.getAttribute(LoginConstant.ACCOUNT_INFO), AccountInfo.class);
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/5/28 12:44
     * 描述 : 获取当前登录用户账号信息
     *
     * @return
     */
    protected AccountBO getLoginAccount() {
        return getLoginAccountInfo().getAccount();
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/5/28 12:44
     * 描述 : 获取当前登录用户账号Id
     *
     * @return
     */
    protected Integer getLoginAccountId() {
        return getLoginAccount().getId();
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/6/3 23:51
     * 描述 : 分页返回数据组装
     *
     * @param rows  分页记录
     * @param total 总记录数
     * @return
     */
    protected Result getResult(List rows, Integer total) {
        Map<String, Object> data = new HashMap<>(2);
        data.put(ApplicationConstant.PAGE_ROWS, rows);
        data.put(ApplicationConstant.PAGE_COUNT, total);
        return Result.succee(data);
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/6/3 23:51
     * 描述 : 分页返回数据组装
     *
     * @param rows  分页记录
     * @param total 总记录数
     * @return
     */
    protected Result getResult(List rows, Long total) {
        return getResult(rows, total.intValue());
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/6/3 23:51
     * 描述 : 分页返回数据组装
     *
     * @param info mybatis 分页插件分页查询结果
     * @return
     */
    protected Result getResult(PageInfo info) {
        return getResult(info.getList(), info.getTotal());
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/3/26 1:28
     * 描述 : 插入数据库操作日志
     *
     * @param accountID 账号ID
     * @param log       日志
     */
    protected void saveDBLog(int accountID, String log, String type) {
        if (StringUtils.isBlank(log)) {
            return;
        }
        logger.debug("添加了数据库操作日志,操作人ID[{}]，内容[{}]", accountID, log);
        DbLogBO dbLog = new DbLogBO();
        dbLog.setAccountId(accountID);
        dbLog.setType(type);
        dbLog.setLog(log);
        dbLog.setCreateTime(new Date());
        int i = dbLogMapper.insertSelective(dbLog);
        checkDbInsert(i);
    }

    /**
     * 检测更新时间
     *
     * @param updateTime
     * @param DbUpdateTime
     */
    protected void checkUpdateTime(Date updateTime, Date DbUpdateTime) {
        if (updateTime == null && DbUpdateTime != null) {
            throw new MyException(ResultEnum.DB_UPDATE_TIME_ERROR);
        }
        if (updateTime != null && updateTime.getTime() != DbUpdateTime.getTime()) {
            throw new MyException(ResultEnum.DB_UPDATE_TIME_ERROR);
        }
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/5 13:30
     * 描述 : 添加redis缓存
     *
     * @param key   key
     * @param value value
     */
    protected void setRedis(String key, Object value) {
        logger.debug("添加redis缓存:key=[{}], value=[{}]", key, value);
        try {
            byte[] serialize = SerializationUtils.serialize(value);
            jedisUtil.set(appCacheDb, key.getBytes(), serialize, appCacheExpire);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("添加redis缓存异常:" + e.getMessage(), e);
        }
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/5 13:29
     * 描述 : 获取redis缓存
     *
     * @param key key
     * @return
     */
    protected Object getRedis(String key) {
        logger.debug("获取redis缓存,key=[{}]", key);
        try {
            byte[] bytes = jedisUtil.get(appCacheDb, key.getBytes(), appCacheExpire);
            return SerializationUtils.deserialize(bytes);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取redis缓存异常:" + e.getMessage(), e);
            return null;
        }
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/5 13:34
     * 描述 : 删除reids缓存
     *
     * @param key
     */
    protected void deleteRedis(String key) {
        logger.debug("删除redis缓存, key=[{}]", key);
        try {
            if (jedisUtil.exists(appCacheDb, key.getBytes())) {
                jedisUtil.del(appCacheDb, key.getBytes());
            }
        } catch (Exception e) {
            logger.error("清除redis缓存异常:" + e.getMessage(), e);
            throw new MyException(ResultEnum.REDIS_DEL_ERROR);
        }
    }


    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/11 1:19
     * 描述 : 比较字符串是否相等
     *
     * @param value1
     * @param value2
     * @return
     */
    protected boolean equalsString(String value1, String value2) {
        if (StringUtils.isBlank(value1) && StringUtils.isBlank(value2)) {
            return true;
        } else if (StringUtils.isNotBlank(value1) && StringUtils.isNotBlank(value2)) {
            return value1.equals(value2);
        } else {
            return false;
        }
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/11 1:25
     * 描述 : 比较Integer是否相等
     *
     * @param value1
     * @param value2
     * @return
     */
    protected boolean equalsInteger(Integer value1, Integer value2) {
        if (value1 == null && value2 == null) {
            return true;
        } else if (value1 != null && value2 != null) {
            return value1.equals(value2);
        } else {
            return false;
        }
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/11 1:29
     * 描述 : 比较Boolean是否相等
     *
     * @param value1
     * @param value2
     * @return
     */
    protected boolean equalsBoolean(Boolean value1, Boolean value2) {
        if (value1 == null && value2 == null) {
            return true;
        } else if (value1 != null && value2 != null) {
            return value1.booleanValue() == value2.booleanValue();
        } else {
            return false;
        }
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/16 23:09
     * 描述 : 检测模糊查询字符串
     *
     * @param value
     * @return
     */
    protected String checkSearchString(String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        } else if (ApplicationConstant.UNDEFINED.equals(value)) {
            return null;
        } else {
            return "%" + value + "%";
        }
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/16 23:12
     * 描述 : 检测比较时间字符串
     *
     * @param value
     * @return
     */
    protected String checkCompareDate(String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        } else if (ApplicationConstant.UNDEFINED.equals(value)) {
            return null;
        } else {
            if (RegexUtils.checkDate(value)) {
                return value;
            } else {
                throw new MyException(ResultEnum.PARAM_ERROR);
            }
        }
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/21 23:38
     * 描述 : 检测数据库添加数据
     *
     * @param count 影响数据库行数
     */
    protected void checkDbInsert(int count) {
        if (count < 1) {
            throw new MyException(ResultEnum.DB_SAVE_ERROR);
        }
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/21 23:38
     * 描述 : 检测数据库修改数据
     *
     * @param count 影响数据库行数
     */
    protected void checkDbUpdate(int count) {
        if (count < 1) {
            throw new MyException(ResultEnum.DB_UPDATE_ERROR);
        }
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/21 23:38
     * 描述 : 检测数据库删除数据
     *
     * @param count 影响数据库行数
     */
    protected void checkDbDelete(int count) {
        if (count < 1) {
            throw new MyException(ResultEnum.DB_DELETE_ERROR);
        }
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/3/22 2:28
     * 描述 : 获取账号查询类型
     *
     * @param value 查询值
     * @return
     */
    protected int getSelectType(String value) {
        boolean checkPhone = RegexUtils.checkPhone(value);
        boolean checkEmail = RegexUtils.checkEmail(value);
        if (checkPhone) {
            return AccountMapper.SELECT_ACCOUNT_TYPE_PHONE;
        }
        if (checkEmail) {
            return AccountMapper.SELECT_ACCOUNT_TYPE_EMAIL;

        }
        return AccountMapper.SELECT_ACCOUNT_TYPE_USERNAME;
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/30 1:51
     * 描述 : 检测是否是数字
     *
     * @param value
     * @return
     */
    protected boolean checkNumber(String value) {
        if (StringUtils.isBlank(value)) {
            return false;
        }
        return StringUtils.isNumeric(value);
    }


    /**
     * 作者: cheng fei
     * 创建日期: 2019/6/9 15:10
     * 描述 : 获取账号session token key
     * @param requestSource
     * @param accountId
     * @return
     */
    protected String getAccountTokenPre (String requestSource, Integer accountId) {
        return requestSource + LoginConstant.SESSION_LINK + LoginConstant.ACCOUNT_TOKEN_PRE + accountId;
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/6/9 15:08
     * 描述 :
     * @param requestSource
     * @param accountId
     * @return
     */
    protected String  getAccountSessionKey (String sessionId){
        return LoginConstant.ACCOUNT_SESSION_PRE + sessionId;
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/5/28 12:10
     * 描述 : 设置账号的session失效
     *
     * @param accountId  失效session账号Id
     * @param resultEnum 失效信息
     */
    protected void setSessionDisabled(Integer accountId, ResultEnum resultEnum) {
        setSessionDisabled(LoginConstant.REQUEST_SOURCE_PC, accountId, resultEnum);
        setSessionDisabled(LoginConstant.REQUEST_SOURCE_MOBILE, accountId, resultEnum);
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/5/28 12:10
     * 描述 : 设置账号的session失效
     *
     * @param accountId  失效session账号Id
     * @param resultEnum 失效信息
     */
    protected void setSessionDisabled(String  requestSource, Integer accountId, ResultEnum resultEnum) {
        if (jedisUtil.exists(sessionDb, getAccountTokenPre(requestSource, accountId))) {
            String sessionId = jedisUtil.get(sessionDb, getAccountTokenPre(requestSource, accountId));
            byte[] bytes = jedisUtil.get(sessionDb, (getAccountSessionKey(sessionId)).getBytes());
            Session oldSession = (Session) SerializationUtils.deserialize(bytes);
            oldSession.setAttribute(LoginConstant.LOGIN_MSG, JsonUtils.objectToJson(resultEnum));
            oldSession.setAttribute(LoginConstant.VALID_SESSION, false);
            jedisUtil.set(sessionDb, (getAccountSessionKey(sessionId)).getBytes(), SerializationUtils.serialize(oldSession));
        }
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/5/28 12:12
     * 描述 : 获取用户的角色列表
     *
     * @param username 用户名
     * @return
     */
    protected List<RoleBO> getRoleListByUsername(String username) {

        logger.debug("获取角色id列表, username=【 {} 】", username);
        //reids缓存中获取
        try {
            String json = jedisUtil.get(appCacheDb, RoleConstant.ACCOUNT_ROLES_KEY_PRE + username, sessionExpire);
            if (StringUtils.isNotBlank(json)) {
                return JsonUtils.jsonToList(json, RoleBO.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取redis缓存异常:" + e.getMessage(), e);
        }

        //数据库中查询角色列表
        List<RoleBO> roles = new ArrayList<>();
        if (systemSuperAdministrator.equals(username)) {
            //超级管理员
            //查询所有角色Id
            List<RoleBO> allRoleId = roleMapper.getAllRole();
            roles.addAll(allRoleId);
        } else {
            //查询账号ID
            Integer accountID = accountMapper.getAccountIDByUserName(username);
            if (accountID == null) {
                return roles;
            }
            //查询账号角色关联关系
            roles.addAll(accountRoleMapper.getRoleListByAccountId(accountID));
        }

        //存入redis缓存
        try {
            jedisUtil.set(appCacheDb, RoleConstant.ACCOUNT_ROLES_KEY_PRE + username, JsonUtils.objectToJson(roles), sessionExpire);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("添加redis缓存异常:" + e.getMessage(), e);
        }

        return roles;
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/5/28 12:13
     * 描述 : 获取用户的权限列表
     *
     * @param roleIds  角色id列表
     * @param username 用户名
     * @return
     */
    protected Set<String> getPermissionUrlsByRoleIds(Set<Integer> roleIds, String username) {

        logger.debug("查询角色列表的权限, roleIds=【 {} 】", roleIds);
        Set<String> permissions = new HashSet<>();
        //查询reids缓存
        try {
            String json = jedisUtil.get(appCacheDb, PermissionConstant.ACCOUNT_PERMISSIONS_KEY_PRE + username, sessionExpire);
            if (StringUtils.isNotBlank(json)) {
                permissions.addAll(JsonUtils.jsonToList(json, String.class));
                return permissions;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取redis缓存异常:" + e.getMessage(), e);
        }

        if (systemSuperAdministrator.equals(username)) {
            //超级管理员
            permissions.addAll(permissionMapper.getAllPermissionId());
        } else {
            for (Integer roleId : roleIds) {
                permissions.addAll(rolePermissionMapper.getPermissionUrlByRoleId(roleId));
            }
        }

        //添加redis缓存
        try {
            jedisUtil.set(appCacheDb, PermissionConstant.ACCOUNT_PERMISSIONS_KEY_PRE + username, JsonUtils.objectToJson(permissions), sessionExpire);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取redis缓存异常:" + e.getMessage(), e);
        }
        return permissions;
    }


    /**
     * 作者: cheng fei
     * 创建日期: 2019/6/2 0:23
     * 描述 : 添加文件使用记录
     *
     * @param fileId
     * @param tableName
     * @param columnId
     */
    protected void addUseFile(Integer fileId, String tableName, Integer columnId) {

        //添加引用记录
        UseFileBO useFileBO = new UseFileBO();
        useFileBO.setFileId(fileId);
        useFileBO.setTableName(tableName);
        useFileBO.setColumnId(columnId);

        int i = useFileMapper.insertSelective(useFileBO);
        checkDbInsert(i);

        //删除未使用记录
        unusedFileMapper.deleteByFileId(fileId);
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/6/2 0:23
     * 描述 : 删除文件使用记录
     *
     * @param fileId
     * @param tableName
     * @param columnId
     */
    protected void deleteUseFile(Integer fileId, String tableName, Integer columnId) {

        if (fileId == null) {
            return;
        }

        //删除引用记录
        int i = useFileMapper.deleteByFileIdAndTableNameAndColumnId(fileId, tableName, columnId);
        checkDbDelete(i);

        // 添加至文件未使用记录,等待系统自动删除,避免发生异常,事务回滚,文件已删除
        addUnusedFile(fileId, null);
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/6/4 14:34
     * 描述 : 将文件添加至未使用记录
     *
     * @param fileId 文件Id
     * @param date   文件上传时间/文件未使用时间
     */
    protected void addUnusedFile(Integer fileId, Date date) {
        UnusedFileBO unusedFileBO = new UnusedFileBO();
        unusedFileBO.setFileId(fileId);
        unusedFileBO.setUploadTime(date == null ? new Date() : date);

        int i = unusedFileMapper.insertSelective(unusedFileBO);
        checkDbInsert(i);
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/6/2 0:36
     * 描述 : 添加文件引用,并删除旧文件引用
     *
     * @param newFileId 新文件Id
     * @param OldFileId 旧文件Id
     * @param tableName 表格名称
     * @param columnId  引用记录Id
     */
    protected void addUseFileAndDeleteUseFile(Integer newFileId, Integer OldFileId, String tableName, Integer columnId) {
        addUseFile(newFileId, tableName, columnId);
        deleteUseFile(OldFileId, tableName, columnId);
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/6/4 14:43
     * 描述 : 获取文件地址
     *
     * @param fileId 文件Id
     * @return
     */
    protected String getFilePath(Integer fileId) {
        return getFilePath(fileId, true, false);
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/6/4 14:43
     * 描述 : 获取文件地址
     *
     * @param fileId
     * @param isCloseClient 是否关闭OSS连接
     * @return
     */
    protected String getFilePath(Integer fileId, boolean isCloseClient) {
        return getFilePath(fileId, isCloseClient, false);

    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/6/4 14:43
     * 描述 : 获取文件地址
     *
     * @param isPerpetual url地址是否用久生效
     * @param fileId      文件ID
     * @return
     */
    protected String getFilePath(boolean isPerpetual, Integer fileId) {
        if (fileId == null || fileId == 0) {
            return "";
        }
        String path = fileMapper.getPathById(fileId);
        return getFilePath(isPerpetual, path);

    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/6/4 14:43
     * 描述 : 获取文件地址
     *
     * @param fileId
     * @param isCloseClient 是否关闭OSS连接
     * @param isPerpetual   url地址是否用久生效
     * @return
     */
    protected String getFilePath(Integer fileId, boolean isCloseClient, boolean isPerpetual) {
        if (fileId == null || fileId == 0) {
            return "";
        }
        String path = fileMapper.getPathById(fileId);
        return getFilePath(path, isCloseClient, isPerpetual);

    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/6/4 15:03
     * 描述 : 获取文件地址
     *
     * @param path 上传地址
     * @return
     */
    protected String getFilePath(String path) {
        return getFilePath(path, true, false);

    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/6/4 15:03
     * 描述 : 获取文件地址
     *
     * @param isPerpetual url地址是否用久生效
     * @param path        上传地址
     * @return
     */
    protected String getFilePath(boolean isPerpetual, String path) {
        if (StringUtils.isBlank(path)) {
            return "";
        }
        return aliyunOSSClientUtil.getUrl(isPerpetual, path);
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/6/4 15:03
     * 描述 : 获取文件地址
     *
     * @param path          上传地址
     * @param isCloseClient 是否关闭OSS连接
     * @return
     */
    protected String getFilePath(String path, boolean isCloseClient) {
        return getFilePath(path, isCloseClient, false);

    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/6/4 15:03
     * 描述 : 获取文件地址
     *
     * @param path          上传地址
     * @param isCloseClient 是否关闭OSS连接
     * @param isPerpetual   url地址是否用久生效
     * @return
     */
    protected String getFilePath(String path, boolean isCloseClient, boolean isPerpetual) {
        if (StringUtils.isBlank(path)) {
            return "";
        }
        return aliyunOSSClientUtil.getUrl(path, isCloseClient, isPerpetual);
    }

}
