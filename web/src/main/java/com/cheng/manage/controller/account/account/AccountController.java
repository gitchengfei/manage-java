package com.cheng.manage.controller.account.account;

import com.cheng.manage.common.result.Result;
import com.cheng.manage.controller.account.base.AccountBaseController;
import com.cheng.manage.entity.account.account.AccountBO;
import com.cheng.manage.service.account.account.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;


/**
 * @Description : 账号Controller
 * @Author : cheng fei
 * @Date : 2019/3/21 00:48
 */
@RestController
@Api(tags = "account.account.AccountController", description = "账号-账号模块")
@RequestMapping("/account/account")
public class AccountController extends AccountBaseController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/save")
    @ApiOperation(value = "添加账号", httpMethod = "POST", produces = "application/json")
    @RequiresPermissions("/account/account/save")
    public Result saveAccount(
            @ApiParam(value = "用户名", required = true) @RequestParam(required = true) String username,
            @ApiParam(value = "密码", required = true) @RequestParam(required = true) String password,
            @ApiParam(value = "姓名", required = true) @RequestParam(required = true) String name,
            @ApiParam(value = "手机号", required = true) @RequestParam(required = true) String phone,
            @ApiParam(value = "邮箱", required = true) @RequestParam(required = true) String email,
            @ApiParam(value = "角色id列表, 用\",\"号隔开", required = false) @RequestParam(required = false, defaultValue = "") String roleIds
    ) {
        AccountBO accountBO = new AccountBO();
        accountBO.setUsername(username);
        accountBO.setPassword(password);
        accountBO.setName(name);
        accountBO.setPhone(phone);
        accountBO.setEmail(email);

        return accountService.saveOrUpdateAccount(accountBO, roleIds, false, false);
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改账号", httpMethod = "POST", produces = "application/json")
    @RequiresPermissions("/account/account/update")
    public Result updateAccount(
            @ApiParam(value = "账号id", required = true) @RequestParam(required = true) Integer id,
            @ApiParam(value = "姓名", required = true) @RequestParam(required = true) String name,
            @ApiParam(value = "手机号", required = true) @RequestParam(required = true) String phone,
            @ApiParam(value = "邮箱", required = true) @RequestParam(required = true) String email,
            @ApiParam(value = "上次修改时间", required = true) @RequestParam(required = true) Long updateTime
    ) {
        AccountBO accountBO = new AccountBO();
        accountBO.setId(id);
        accountBO.setName(name);
        accountBO.setPhone(phone);
        accountBO.setEmail(email);
        accountBO.setUpdateTime(updateTime == null ? null : new Date(updateTime));

        return accountService.saveOrUpdateAccount(accountBO, null, false, false);
    }

    @PostMapping("/update/status")
    @ApiOperation(value = "修改账号状态", httpMethod = "POST", produces = "application/json")
    @RequiresPermissions("/account/account/update/status")
    public Result updateAccountStatus(
            @ApiParam(value = "账号id", required = true) @RequestParam(required = true) Integer id,
            @ApiParam(value = "状态", required = true) @RequestParam(required = true) Boolean status,
            @ApiParam(value = "上次修改时间", required = true) @RequestParam(required = true) Long updateTime
    ) {
        AccountBO accountBO = new AccountBO();
        accountBO.setId(id);
        accountBO.setStatus(status);
        accountBO.setUpdateTime(updateTime == null ? null : new Date(updateTime));

        return accountService.saveOrUpdateAccount(accountBO, null, false, true);
    }

    @PostMapping("/reset")
    @ApiOperation(value = "重置账号密码", httpMethod = "POST", produces = "application/json")
    @RequiresPermissions("/account/account/reset")
    public Result updateAccountPassword(
            @ApiParam(value = "账号id", required = true) @RequestParam(required = true) Integer id,
            @ApiParam(value = "用户名", required = true) @RequestParam(required = true) String username,
            @ApiParam(value = "密码", required = true) @RequestParam(required = true) String password,
            @ApiParam(value = "上次修改时间", required = true) @RequestParam(required = true) Long updateTime
    ) {
        AccountBO accountBO = new AccountBO();
        accountBO.setId(id);
        accountBO.setUsername(username);
        accountBO.setPassword(password);
        accountBO.setUpdateTime(updateTime == null ? null : new Date(updateTime));
        return accountService.saveOrUpdateAccount(accountBO, null, true, false);
    }

    @PostMapping("/password")
    @ApiOperation(value = "修改账号密码", httpMethod = "POST", produces = "application/json")
    public Result updateAccountPassword(
            @ApiParam(value = "旧密码", required = true) @RequestParam(required = true) String oldPassword,
            @ApiParam(value = "新密码", required = true) @RequestParam(required = true) String newPassword
    ) {
        return accountService.updateAccountPassword(oldPassword, newPassword);
    }

    @PostMapping("/update/role")
    @ApiOperation(value = "设置账号拥有角色", httpMethod = "POST", produces = "application/json")
    @RequiresPermissions("/account/account/update/role")
    public Result updateAccountRole(
            @ApiParam(value = "账号id列表,用\",\"号隔开", required = true) @RequestParam(required = true) String ids,
            @ApiParam(value = "角色id列表,用\",\"号隔开", required = true) @RequestParam(required = true) String roleIds
    ) {
        return accountService.updateAccountRole(ids, roleIds);
    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除账号", httpMethod = "POST", produces = "application/json")
    @RequiresPermissions("/account/account/delete")
    public Result deleteAccount(
            @ApiParam(value = "账号id", required = true) @RequestParam(required = true) Integer id
    ) {
        return accountService.deleteAccount(id);
    }

    @PostMapping("/list")
    @ApiOperation(value = "获取账号列表", httpMethod = "POST", produces = "application/json")
    @RequiresPermissions("/account/account/list")
    public Result getAccountList(
            @ApiParam(value = "用户名", required = false) @RequestParam(required = false, defaultValue = "") String username,
            @ApiParam(value = "姓名", required = false) @RequestParam(required = false, defaultValue = "") String name,
            @ApiParam(value = "手机号", required = false) @RequestParam(required = false, defaultValue = "") String phone,
            @ApiParam(value = "邮箱", required = false) @RequestParam(required = false, defaultValue = "") String email,
            @ApiParam(value = "角色id", required = false) @RequestParam(required = false, defaultValue = "") String roleId,
            @ApiParam(value = "页码", required = false) @RequestParam(required = false, defaultValue = "1") Integer page,
            @ApiParam(value = "每页加载记录数", required = false) @RequestParam(required = false, defaultValue = "10") Integer pageSize
    ) {
        return accountService.getAccountList(username, name, phone, email, roleId, page, pageSize);
    }

    @PostMapping("/check/username")
    @ApiOperation(value = "检测用户名", httpMethod = "POST", produces = "application/json")
    public Result checkUsername(
            @ApiParam(value = "用户名", required = true) @RequestParam(required = true) String username,
            @ApiParam(value = "需要排除的Id", required = false) @RequestParam(required = false, defaultValue = "") Integer excludeId
    ) {
        return accountService.checkUsername(username, excludeId);
    }

    @PostMapping("/check/phone")
    @ApiOperation(value = "检测手机号", httpMethod = "POST", produces = "application/json")
    public Result checkPhone(
            @ApiParam(value = "手机号", required = true) @RequestParam(required = true) String phone,
            @ApiParam(value = "需要排除的Id", required = false) @RequestParam(required = false, defaultValue = "") Integer excludeId
    ) {
        return accountService.checkPhone(phone, excludeId);
    }

    @PostMapping("/check/email")
    @ApiOperation(value = "检测邮箱", httpMethod = "POST", produces = "application/json")
    public Result checkEmail(
            @ApiParam(value = "邮箱", required = true) @RequestParam(required = true) String email,
            @ApiParam(value = "需要排除的Id", required = false) @RequestParam(required = false, defaultValue = "") Integer excludeId
    ) {
        return accountService.checkEmail(email, excludeId);
    }

    @PostMapping("/head/portrait")
    @ApiOperation(value = "修改账号头像", httpMethod = "POST", produces = "application/json")
    public Result updateHeadPortrait(
            @ApiParam(value = "头像文件Id", required = true) @RequestParam(required = true) Integer fileId
    ) {
        return accountService.updateHeadPortrait(fileId);
    }
}
