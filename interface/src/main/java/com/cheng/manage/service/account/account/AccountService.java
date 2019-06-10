package com.cheng.manage.service.account.account;


import com.cheng.manage.common.result.Result;
import com.cheng.manage.entity.account.account.AccountBO;

/**
 * @Description : 账号接口
 * @Author : cheng fei
 * @Date : 2019/3/21 01:01
 */
public interface AccountService {

    /**
     * 作者: cheng fei
     * 创建日期: 2019/3/21 1:14
     * 描述 : 查询账号列表
     * @param username 模糊查询用户名
     * @param name 模糊查询姓名
     * @param phone 模糊查询手机号
     * @param email 模糊查询邮箱
     * @param roleId 角色id
     * @param page 页码
     * @param pageSize 每页加载条数
     * @return
     */

    Result getAccountList(String username, String name, String phone, String email, String roleId, Integer page, Integer pageSize);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/3/26 1:48
     * 描述 : 获取账号
     * @param accountID
     * @return
     */
    AccountBO getAccountByID(Long accountID);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/30 10:41
     * 描述 : 新增/修改账号
     * @param accountBO 新增/修改的账号
     * @param roleIds 角色id列表
     * @param isResetPassword 是否是重置密码
     * @param isUpdateStatus 是否是修改状态
     * @return
     */
    Result saveOrUpdateAccount(AccountBO accountBO, String roleIds, boolean isResetPassword, boolean isUpdateStatus);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/30 23:22
     * 描述 : 删除账号
     * @param id 账号Id
     * @return
     */
    Result deleteAccount(Integer id);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/5/1 21:57
     * 描述 : 检测账号名是否可用
     * @param username 用户名
     * @param excludeId 需要排除的Id
     * @return
     */
    Result checkUsername(String username, Integer excludeId);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/5/1 22:02
     * 描述 : 检测手机号
     * @param phone 手机号
     * @param excludeId 需要排除的Id
     * @return
     */
    Result checkPhone(String phone, Integer excludeId);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/5/1 22:20
     * 描述 : 检测email
     * @param email
     * @param excludeId
     * @return
     */
    Result checkEmail(String email, Integer excludeId);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/5/3 0:31
     * 描述 : 修改账号拥有角色
     * @param ids 账号Id列表
     * @param roleIds 角色列表
     * @return
     */
    Result updateAccountRole(String ids, String roleIds);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/5/24 15:25
     * 描述 : 修改账号密码
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return
     */
    Result updateAccountPassword(String oldPassword, String newPassword);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/6/1 23:56
     * 描述 : 修改账号头像
     * @param fileId 文件Id
     * @return
     */
    Result updateHeadPortrait(Integer fileId);
}
