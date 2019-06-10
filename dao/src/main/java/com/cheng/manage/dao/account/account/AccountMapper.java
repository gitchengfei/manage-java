package com.cheng.manage.dao.account.account;

import com.cheng.manage.common.mapper.BaseMapper;
import com.cheng.manage.entity.account.account.AccountBO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author cheng fei
 */
@Repository
public interface AccountMapper extends BaseMapper<AccountBO> {

    /**
     * 查询账号- 用户名
     */
    public static final int SELECT_ACCOUNT_TYPE_USERNAME = 1;

    /**
     * 查询账号- 电话
     */
    public static final int SELECT_ACCOUNT_TYPE_PHONE = 2;

    /**
     * 查询账号- 邮箱
     */
    public static final int SELECT_ACCOUNT_TYPE_EMAIL = 3;


    /**
     * 作者: cheng fei
     * 创建日期: 2019/3/22 2:13
     * 描述 : 查询账号信息
     *
     * @param username 匹配的值
     * @param type  查询类型
     * @return
     */
    AccountBO getAccount(@Param("username")String username, @Param("type")int type);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/3/23 2:32
     * 描述 : 通过用户名查询用户ID
     *
     * @param username 用户名
     * @return
     */
    Integer getAccountIDByUserName(@Param("username")String username);


    /**
     * 作者: cheng fei
     * 创建日期: 2019/3/29 21:59
     * 描述 : 查询用户姓名
     *
     * @param id
     * @return
     */
    String getNameById(@Param("id")Integer id);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/30 11:01
     * 描述 : 通过用户名查询账号总数
     * @param username 用户名
     * @param excludeId 需要排除的Id
     * @return
     */
    int countByUsername(@Param("username") String username, @Param("excludeId") Integer excludeId);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/30 21:13
     * 描述 : 根据手机号码查询账号总数
     * @param phone 手机号码
     * @param excludeId 需要排除的ID
     * @return
     */
    int countByPhone(@Param("phone") String phone, @Param("excludeId") Integer excludeId);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/30 21:25
     * 描述 : 根据邮箱查询账号总数
     * @param email 邮箱
     * @param excludeId 需要排除的id
     * @return
     */
    int countByEmail(@Param("email") String email, @Param("excludeId") Integer excludeId);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/30 22:50
     * 描述 : 根据账号ID查询用户名
     * @param id 账号ID
     * @return
     */
    String getUserNameById(@Param("id") Integer id);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/30 23:13
     * 描述 : 根据账号ID查询修改时间
     * @param id 账号ID
     * @return
     */
    Date getUpdateTimeById(@Param("id") Integer id);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/30 23:48
     * 描述 : 获取账号列表
     * @param username 模糊查询:用户名
     * @param name  模糊查询:姓名
     * @param phone 模糊查询:手机号
     * @param email 模糊查询:邮箱
     * @return
     */
    List<AccountBO> getAccountList(@Param("username") String username, @Param("name") String name, @Param("phone") String phone, @Param("email") String email);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/5/3 0:01
     * 描述 : 获取账号列表
     * @param username 模糊查询:用户名
     * @param name  模糊查询:姓名
     * @param phone 模糊查询:手机号
     * @param email 模糊查询:邮箱
     * @param roleId 拥有角色Id
     * @param admin 超级管理员用户名
     * @return
     */
    List<AccountBO> getAccountListByRoleId(@Param("username") String username, @Param("name") String name, @Param("phone") String phone, @Param("email") String email, @Param("roleId") Integer roleId, @Param("admin") String admin);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/5/24 15:30
     * 描述 : 通过账号Id查询密码
     * @param id id
     * @return
     */
    String getPasswordById(@Param("id") Integer id);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/5/24 15:39
     * 描述 : 根据账号id修改账号密码
     * @param id id
     * @param password 密码
     * @return
     */
    int updatePasswordById(@Param("id") Integer id, @Param("password") String password);
}
