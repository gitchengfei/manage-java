package com.cheng.manage.dao.account.account;

import com.cheng.manage.common.mapper.BaseMapper;
import com.cheng.manage.entity.account.account.AccountRoleBO;
import com.cheng.manage.entity.account.role.RoleBO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: cheng fei
 * @Date: 2019/5/2 18:46
 * @Description:  账号角色Mapper
 */

@Repository
public interface AccountRoleMapper extends BaseMapper<AccountRoleBO> {

    /**
     * 作者: cheng fei
     * 创建日期: 2019/5/2 18:47
     * 描述 : 通过账号ID查询角色列表
     * @param accountId 账号Id
     * @return
     */
    List<RoleBO> getRoleListByAccountId(@Param("accountId") Integer accountId);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/5/3 0:42
     * 描述 : 删除账号拥有角色
     * @param accountId 账号id
     * @return
     */
    int deleteAccountRoleByAccountId(@Param("accountId") int accountId);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/5/4 16:26
     * 描述 : 通过账号id查询角色Id列表
     * @param accountId 账号Id
     * @return
     */
    List<Integer> getRoleIdsByAccountId(@Param("accountId") Integer accountId);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/5/30 0:07
     * 描述 : 删除账号拥有角色
     * @param roleId 角色Id
     * @return
     */
    int deleteAccountRoleByROleId(@Param("roleId") Integer roleId);
}