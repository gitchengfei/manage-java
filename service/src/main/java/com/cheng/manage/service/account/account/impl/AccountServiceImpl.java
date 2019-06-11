package com.cheng.manage.service.account.account.impl;

import com.alibaba.druid.sql.visitor.functions.If;
import com.cheng.manage.common.aliyun.AliyunOSSClientUtil;
import com.cheng.manage.common.exception.MyException;
import com.cheng.manage.common.result.Result;
import com.cheng.manage.common.result.ResultEnum;
import com.cheng.manage.common.util.MD5Utils;
import com.cheng.manage.common.util.RegexUtils;
import com.cheng.manage.constant.app.account.role.RoleConstant;
import com.cheng.manage.entity.account.account.AccountBO;
import com.cheng.manage.entity.account.account.AccountDTO;
import com.cheng.manage.entity.account.account.AccountRoleBO;
import com.cheng.manage.service.account.account.AccountService;
import com.cheng.manage.service.account.base.AccountBaseService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description : 账号Service实现类
 * @Author : cheng fei
 * @Date : 2019/3/21 01:03
 */
@Service
public class AccountServiceImpl extends AccountBaseService implements AccountService {

    /**
     * 数据库操作日志类型
     */
    private static final String DB_LOG_TYPE = "SYSTEM_DB_LOG_TYPE_ACCOUNT";

    @Override
    public Result getAccountList(String username, String name, String phone, String email, String roleId, Integer page, Integer pageSize) {

        logger.debug("查询账号列表：username=【 {} 】, name=【 {} 】, phone=【 {} 】, email=【 {} 】, roleIds=【 {} 】, page=【 {} 】, pageSize=【 {} 】", username, name, phone, email, roleId, page, pageSize);

        username = checkSearchString(username);
        name = checkSearchString(name);
        phone = checkSearchString(phone);
        email = checkSearchString(email);
        roleId = StringUtils.isBlank(roleId) ? null : roleId;
        if (roleId != null && !StringUtils.isNumeric(roleId)){
            return Result.build(ResultEnum.PARAM_ERROR);
        }

        PageHelper.startPage(page, pageSize);
        List<AccountBO> accounts;
        if (roleId == null){
            accounts = accountMapper.getAccountList(username, name, phone, email);
        } else {
            accounts = accountMapper.getAccountListByRoleId(username, name, phone, email, Integer.parseInt(roleId), systemSuperAdministrator);
        }
        PageInfo<AccountBO> info = new PageInfo<>(accounts);

        List<AccountDTO> accountDTOS = new ArrayList<>(info.getList().size());

        for (AccountBO accountBO : info.getList()) {
            accountBO.setPassword(null);
            AccountDTO accountDTO = new AccountDTO(accountBO);
            accountDTO.setCreateName(accountMapper.getNameById(accountBO.getCreateId()));
            accountDTO.setUpdateName(accountMapper.getNameById(accountBO.getUpdateId()));
            if (systemSuperAdministrator.equals(accountBO.getUsername())) {
                accountDTO.setRoles(roleMapper.getAllRole());
            } else {
                accountDTO.setRoles(accountRoleMapper.getRoleListByAccountId(accountBO.getId()));
            }
            accountDTOS.add(accountDTO);
        }
        return getResult(accountDTOS, info.getTotal());
    }

    @Override
    public AccountBO getAccountByID(Long accountID) {
        return accountMapper.selectByPrimaryKey(accountID);
    }

    @Override
    public Result saveOrUpdateAccount(AccountBO accountBO, String roleIds, boolean isResetPassword, boolean isUpdateStatus) {

        AccountBO oldAccountBo = null;
        if (accountBO.getId() != null) {
            oldAccountBo = accountMapper.selectByPrimaryKey(accountBO.getId());
        }

        //检测参数
        checkAccountBO(accountBO, oldAccountBo, isResetPassword, isUpdateStatus);

        //重置账号密码时,检测重置内容
        ResultEnum resultEnum = null;
        if (isResetPassword) {
            resultEnum = checkUsernameAndPassword(accountBO, oldAccountBo);
        }

        //获取当前登录账号
        AccountBO loginAccount = getLoginAccount();
        if (accountBO.getId() == null) {

            //补全参数
             accountBO.setCreateId(loginAccount.getId());
             accountBO.setCreateTime(new Date());
             accountBO.setStatus(true);
             accountBO.setDelete(false);

            int i = accountMapper.insertSelective(accountBO);
            checkDbInsert(i);

            //添加账号角色信息
            saveAccountRole(accountBO.getId(), roleIds);

            saveDBLog(loginAccount.getId(), getAccountLog(accountBO, roleIds), DB_LOG_TYPE);
        } else {
            //检测是否修改超级管理员账号
            String username = accountMapper.getUserNameById(accountBO.getId());
            checkUpdateSystemSuperAdministrator(username);

            accountBO.setUpdateId(loginAccount.getId());
            accountBO.setUpdateTime(new Date());

            int i = accountMapper.updateByPrimaryKeySelective(accountBO);
            checkDbUpdate(i);

            String log = "";
            if (isResetPassword) {
                log = "重置了账号【 " + accountMapper.getUserNameById(accountBO.getId()) + " 】的密码";
            } else {
                log = getAccountLog(accountBO, oldAccountBo, isUpdateStatus);
            }
            //数据库操作日志
            saveDBLog(loginAccount.getId(), log, DB_LOG_TYPE);

            //被的禁用账号,如果有登录,设置session失效
            if (isUpdateStatus && !accountBO.getStatus()) {
                setSessionDisabled(oldAccountBo.getId(), ResultEnum.ACCOUNT_IS_DISABLED);
            }

            //重置密码的账号,如如果登录,设置session失效
            if (isResetPassword) {
                setSessionDisabled(oldAccountBo.getId(), resultEnum);
            }
        }

        return Result.succee();
    }


    /**
     * 作者: cheng fei
     * 创建日期: 2019/5/3 0:13
     * 描述 : 添加账号角色信息
     * @param accountId 账号id
     * @param roleIds 拥有角色
     */
    private void saveAccountRole(Integer accountId, String roleIds) {
        if (StringUtils.isNotBlank(roleIds)) {
            AccountRoleBO accountRoleBO = new AccountRoleBO();
            accountRoleBO.setAccountId(accountId);
            String[] roleIdArrayStr = roleIds.split(",");
            for (String roleIdStr : roleIdArrayStr) {
                if (!StringUtils.isNumeric(roleIdStr)) {
                    throw new MyException(ResultEnum.PARAM_ERROR);
                }
                accountRoleBO.setRoleId(Integer.parseInt(roleIdStr));
                int i = accountRoleMapper.insertSelective(accountRoleBO);
                checkDbInsert(i);
            }
        }
    }

    @Override
    public Result deleteAccount(Integer id) {

        //获取当前登录账号ID
        Integer loginAccountId = getLoginAccountId();
        //检测是否修改超级管理员账号
        String username = accountMapper.getUserNameById(id);
        checkUpdateSystemSuperAdministrator(username);

        AccountBO accountBO = new AccountBO();
        accountBO.setId(id);
        accountBO.setDelete(true);
        accountBO.setUpdateId(loginAccountId);
        accountBO.setUpdateTime(new Date());

        int i = accountMapper.updateByPrimaryKeySelective(accountBO);
        checkDbUpdate(i);

        String log = "删除了账号【 " + accountMapper.getUserNameById(id) + " 】";
        saveDBLog(loginAccountId, log, DB_LOG_TYPE);

        return Result.succee();
    }

    @Override
    public Result checkUsername(String username, Integer excludeId) {

        if (StringUtils.isBlank(username)){
            return Result.build(ResultEnum.ACCOUNT_USERNAME_IS_NULL);
        } else if (!RegexUtils.checkUsername(username)) {
            return Result.build(ResultEnum.ACCOUNT_USERNAME_REGEX_ERROR);
        } else {
            return Result.succee(checkAccountUsername(username, excludeId));
        }
    }

    @Override
    public Result checkPhone(String phone, Integer excludeId) {

        if (StringUtils.isBlank(phone)) {
            return Result.build(ResultEnum.ACCOUNT_PHONE_IS_NULL);
        } else if (!RegexUtils.checkPhone(phone)) {
            return Result.build(ResultEnum.ACCOUNT_PASSWORD_REGEX_ERROR);
        } else {
            return Result.succee(checkAccountPhone(phone, excludeId));
        }
    }

    @Override
    public Result checkEmail(String email, Integer excludeId) {

        if (StringUtils.isBlank(email)) {
            return Result.build(ResultEnum.ACCOUNT_EMAIL_IS_NULL);
        } else if (!RegexUtils.checkEmail(email)) {
            return Result.build(ResultEnum.ACCOUNT_EMAIL_FORMAT_ERROR);
        } else {
            return Result.succee(checkAccountEmail(email, excludeId));
        }
    }

    @Override
    public Result updateAccountRole(String ids, String roleIds) {
        if (StringUtils.isNotBlank(ids)){
            String[] accountIdArrayStr = ids.split(",");
            for (String accountIdStr : accountIdArrayStr) {
                if (!StringUtils.isNumeric(accountIdStr)){
                    throw new MyException(ResultEnum.PARAM_ERROR);
                }
                Integer accountId = Integer.parseInt(accountIdStr);
                //检查是是否修改超级管理员账号
                String username = accountMapper.getUserNameById(accountId);
                checkUpdateSystemSuperAdministrator(username);
                //删除账号原拥有角色
                accountRoleMapper.deleteAccountRoleByAccountId(accountId);
                //添加账号角色
                saveAccountRole(accountId, roleIds);

                String log = "修改账号【 " + accountMapper.getUserNameById(accountId) + " 】拥有的角色【 " + roleIds + " 】";

                saveDBLog(getLoginAccountId(), log, DB_LOG_TYPE);

                //删除用户角色缓存数据
                jedisUtil.del(appCacheDb,RoleConstant.ACCOUNT_ROLES_KEY_PRE + username);
            }
        }

        return Result.succee();
    }

    @Override
    public Result updateAccountPassword(String oldPassword, String newPassword) {

        AccountBO loginAccount = getLoginAccount();

        logger.debug("修改账号密码: loginAccount=【 {} 】", loginAccount);

        //检测参数
        if (StringUtils.isBlank(oldPassword)) {
            return Result.build(ResultEnum.ACCOUNT_OLD_PASSWORD_IS_NULL);
        }
        if (StringUtils.isBlank(newPassword)) {
            return Result.build(ResultEnum.ACCOUNT_NEW_PASSWORD_IS_NULL);
        }

        //处理密码
        oldPassword = disposePassword(oldPassword, loginAccount.getUsername(), false);

        //查询旧密码
        String password = accountMapper.getPasswordById(loginAccount.getId());
        //对比密码
        if (!oldPassword.equals(password)) {
            return Result.build(ResultEnum.ACCOUNT_OLD_PASSWORD_IS_ERROR);
        }
        //检测新密码
        newPassword = disposePassword(newPassword, loginAccount.getUsername(), true);

        //修改密码
        int i = accountMapper.updatePasswordById(loginAccount.getId(), newPassword);
        checkDbUpdate(i);

        String log = "【 " + loginAccount.getUsername() + " 】修改了密码！";
        saveDBLog(loginAccount.getId(), log, DB_LOG_TYPE);

        return Result.succee();
    }

    @Override
    public Result updateHeadPortrait(Integer fileId) {

        Integer loginAccountId = getLoginAccountId();

        AccountBO oldAccountBO = accountMapper.selectByPrimaryKey(loginAccountId);

        AccountBO accountBO = new AccountBO();
        accountBO.setId(loginAccountId);
        accountBO.setHeadPortrait(fileId);
        int i = accountMapper.updateByPrimaryKeySelective(accountBO);
        checkDbUpdate(i);
        addUseFileAndDeleteUseFile(fileId, oldAccountBO.getHeadPortrait(), "account", loginAccountId);

        return Result.succee(getFilePath(fileId));
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/5/3 0:36
     * 描述 : 检测修改超级管理员账号
     * @param username 账号
     */
    private void checkUpdateSystemSuperAdministrator (String username){
        if (systemSuperAdministrator.equals(username)){
            throw new MyException(ResultEnum.ACCOUNT_ADMIN_CONT_UPDATE);
        }
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/30 22:52
     * 描述 : 获取账号数据库操作日志
     * @param accountBO 修改账号
     * @param oldAccountBo 数据库存储账号
     * @param isUpdateStatus 是否是修改状态
     * @return
     */
    private String getAccountLog(AccountBO accountBO, AccountBO oldAccountBo, boolean isUpdateStatus) {
        StringBuffer log = new StringBuffer();
        if (!isUpdateStatus) {

            //用户名
            if (!equalsString(accountBO.getUsername(), oldAccountBo.getUsername())){
                //修改用户名,如改用户已登录,设置session失效
                setSessionDisabled(oldAccountBo.getId(), ResultEnum.ACCOUNT_UPDATE_USERNAME);
                log.append("用户名：").append(oldAccountBo.getUsername()).append("==>").append(accountBO.getUsername()).append("，");
            }

            //姓名
            if (!equalsString(accountBO.getName(), oldAccountBo.getName())) {
                log.append("姓名：").append(oldAccountBo.getName()).append("==>").append(accountBO.getName()).append("，");
            }

            //电话
            if (!equalsString(accountBO.getPhone(), oldAccountBo.getPhone())) {
                log.append("电话：").append(oldAccountBo.getPhone()).append("==>").append(accountBO.getPhone()).append("，");
            }

            //邮箱
            if (!equalsString(accountBO.getEmail(), oldAccountBo.getEmail())) {
                log.append("邮箱：").append(oldAccountBo.getEmail()).append("==>").append(accountBO.getEmail()).append("，");
            }
        } else {
            if (!equalsBoolean(accountBO.getStatus(), oldAccountBo.getStatus())){
                log.append("状态：").append(oldAccountBo.getStatus() ? "启用" : "禁用").append("==>").append(accountBO.getStatus() ? "启用" : "禁用").append("，");
            }
        }

        String logStr = log.toString();

        if (StringUtils.isBlank(logStr)){
            return "";
        } else {
            StringBuffer data = new StringBuffer();
            data.append("修改了账号【 ").append(oldAccountBo.getUsername()).append(" 】==>")
                    .append("【 ").append(logStr.substring(0, logStr.lastIndexOf("，"))).append(" 】");
            return data.toString();
        }

    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/30 22:42
     * 描述 : 获取账号数据库操作日志
     * @param accountBO 新增账号
     * @param roleIds 账号拥有角色
     * @return
     */
    private String getAccountLog(AccountBO accountBO, String roleIds) {
        StringBuffer log = new StringBuffer();
        log.append("添加了账号【 ")
                .append("用户名=").append(accountBO.getUsername()).append("，")
                .append("姓名=").append(accountBO.getName()).append("，")
                .append("手机号=").append(accountBO.getPhone()).append("，")
                .append("邮箱=").append(accountBO.getEmail()).append("，")
                .append("拥有角色Id为=").append(roleIds).append(" 】");
        return log.toString();
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/30 10:47
     * 描述 : 检测账号参数
     *
     * @param accountBO      新增/修改的账号
     * @param oldAccountBo   数据存储的账号数据
     * @param isResetPassword 是否是重置密码
     * @param isUpdateStatus 是否是修改状态
     */
    private void checkAccountBO(AccountBO accountBO, AccountBO oldAccountBo, boolean isResetPassword, boolean isUpdateStatus) {

        if (!isUpdateStatus  && !isResetPassword) {
            //不是修改账号状态以及重置密码
            //检测用户名
            if (oldAccountBo == null) {
                checkUsername(accountBO, null);
            }

            //检测密码
            if (oldAccountBo == null && StringUtils.isBlank(accountBO.getPassword())) {
                throw new MyException(ResultEnum.ACCOUNT_PASSWORD_IS_NULL);
            } else if (oldAccountBo == null && StringUtils.isNotBlank(accountBO.getPassword())) {
                //处理密码
                accountBO.setPassword(disposePassword(accountBO.getPassword(), accountBO.getUsername(), false));
            }

            //检测姓名
            if (StringUtils.isBlank(accountBO.getName())) {
                throw new MyException(ResultEnum.ACCOUNT_NAME_IS_NULL);
            }

            //检测手机号
            if (StringUtils.isBlank(accountBO.getPhone())){
                throw new MyException(ResultEnum.ACCOUNT_PHONE_IS_NULL);
            } else if (!RegexUtils.checkMobile(accountBO.getPhone())) {
                throw new MyException(ResultEnum.ACCOUNT_PHONE_FORMAT_ERROR);
            }else if (!checkAccountPhone(accountBO.getPhone(), oldAccountBo == null ? null : accountBO.getId())) {
                throw new MyException(ResultEnum.ACCOUNT_PHONE_IS_EXIST);
            }

            //检测邮箱
            if (StringUtils.isBlank(accountBO.getEmail())){
                throw new MyException(ResultEnum.ACCOUNT_EMAIL_IS_NULL);
            } else if (!RegexUtils.checkEmail(accountBO.getEmail())) {
                throw new MyException(ResultEnum.ACCOUNT_EMAIL_FORMAT_ERROR);
            }else if (!checkAccountEmail(accountBO.getEmail(), oldAccountBo == null ? null : accountBO.getId())) {
                throw new MyException(ResultEnum.ACCOUNT_EMAIL_IS_EXIST);
            }
        } else if (isUpdateStatus){
            //修改账号状态
            if (accountBO.getStatus() == null) {
                throw new MyException(ResultEnum.ACCOUNT_STATUS_IS_NULL);
            }
        } else {
            //重置账号密码
            //检测账号
            checkUsername(accountBO, oldAccountBo);
            if (StringUtils.isBlank(accountBO.getPassword())) {
                throw new MyException(ResultEnum.ACCOUNT_PASSWORD_IS_NULL);
            } else {
                //处理密码
                accountBO.setPassword(disposePassword(accountBO.getPassword(), oldAccountBo.getUsername(), false));
            }
        }

        //检测修改时间
        if (oldAccountBo != null){
            checkUpdateTime(accountBO.getUpdateTime(), oldAccountBo.getUpdateTime());
        }
    }


    /**
     * 作者: cheng fei
     * 创建日期: 2019/6/9 17:23
     * 描述 : 获取重置账号密码详情
     * @param accountBO
     * @param oldAccountBO
     * @return
     */
    private ResultEnum checkUsernameAndPassword (AccountBO accountBO, AccountBO oldAccountBO)  {
        if (accountBO.getUsername().equals(oldAccountBO.getUsername()) && accountBO.getPassword().equals(oldAccountBO.getPassword())) {
            throw new MyException(ResultEnum.ACCOUNT_OLD_USERNAME_AND_OLD_PASSWORD_EQUAL);
        } else if (accountBO.getUsername().equals(oldAccountBO.getUsername()) && !accountBO.getPassword().equals(oldAccountBO.getPassword())) {
            return ResultEnum.ACCOUNT_UPDATE_PASSWORD;
        } else if (!accountBO.getUsername().equals(oldAccountBO.getUsername()) && accountBO.getPassword().equals(oldAccountBO.getPassword())) {
            return ResultEnum.ACCOUNT_UPDATE_USERNAME;
        } else {
            return ResultEnum.ACCOUNT_UPDATE_USERNAME_AND_PASSWORD;
        }
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/6/8 16:28
     * 描述 : 检测用户名
     * @param accountBO 账号信息
     * @param oldAccountBO 旧账号信息
     */
    private void  checkUsername (AccountBO accountBO, AccountBO oldAccountBO) {
        if (StringUtils.isBlank(accountBO.getUsername())) {
            throw new MyException(ResultEnum.ACCOUNT_USERNAME_IS_NULL);
        } else if (!RegexUtils.checkUsername(accountBO.getUsername())) {
            throw new MyException(ResultEnum.ACCOUNT_USERNAME_REGEX_ERROR);
        } else if (!checkAccountUsername(accountBO.getUsername(), oldAccountBO == null ? null : oldAccountBO.getId())) {
            throw new MyException(ResultEnum.ACCOUNT_USERNAME_IS_EXIST);
        }
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/30 21:22
     * 描述 : 检测账号邮箱
     * @param email 邮箱
     * @param excludeId 需要排除的id
     * @return
     */
    private boolean checkAccountEmail(String email, Integer excludeId) {

        int count = accountMapper.countByEmail(email, excludeId);

        return count <= 0;
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/30 21:10
     * 描述 : 检测账号手机号码
     * @param phone 电话号码
     * @param excludeId 需要排除的ID
     * @return
     */
    private boolean checkAccountPhone(String phone, Integer excludeId) {

        int count = accountMapper.countByPhone(phone, excludeId);

        return count <= 0;
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/30 11:08
     * 描述 : 处理密码
     * @param password 密码
     * @param username 用户名
     */
    private String disposePassword(String password, String username, boolean isCheckRegEx) {

        //获取密码 前端需要将密码的明文根据用户名的长度,通过base64 加密
        BASE64Decoder dn = new BASE64Decoder();

        int count = username.length();
        try {
            for (int i = 0; i < count; i ++) {
                password = new String(dn.decodeBuffer(password), StandardCharsets.UTF_8);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("密码解码异常:" + e.getMessage(), e);
            throw  new MyException(ResultEnum.ACCOUNT_DECODER_PASSWORD_ERROR);
        }

        if (isCheckRegEx) {
            if (!RegexUtils.checkPassword(password)){
                throw new MyException(ResultEnum.ACCOUNT_PASSWORD_REGEX_ERROR);
            }
        }

        //密码加密
        for (int i = 0; i < count; i ++) {
            password = MD5Utils.MD5(password);
        }

        return password;
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/30 10:59
     * 描述 : 检测账号用户名是否可用
     *
     * @param username  用户名
     * @param excludeId 需要排除的Id
     * @return
     */
    private boolean checkAccountUsername(String username, Integer excludeId) {

        int count = accountMapper.countByUsername(username, excludeId);

        return count <= 0;
    }
}
