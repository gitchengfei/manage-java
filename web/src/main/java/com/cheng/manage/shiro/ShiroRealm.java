package com.cheng.manage.shiro;

import com.cheng.manage.common.exception.MyException;
import com.cheng.manage.common.redis.JedisUtil;
import com.cheng.manage.common.result.ResultEnum;
import com.cheng.manage.common.json.JsonUtils;
import com.cheng.manage.constant.app.system.login.LoginConstant;
import com.cheng.manage.entity.account.account.AccountBO;
import com.cheng.manage.entity.account.role.RoleBO;
import com.cheng.manage.service.account.role.RoleService;
import com.cheng.manage.service.system.permission.PermissionService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashSet;
import java.util.List;
import java.util.Set;



/**
 * @Description :
 * @Author : cheng fei
 * @Date : 2019/3/23 00:04
 */

public class ShiroRealm extends AuthorizingRealm {

    private static Logger logger = LoggerFactory.getLogger(ShiroRealm.class);

    @Autowired
    private JedisUtil jedisUtil;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @Value("${spring.redis.app.cache.db}")
    protected int appCacheDb;

    /**
     * 主要用来获取角色,权限信息
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.debug("ShiroRealm.doGetAuthorizationInfo==>shiro获取角色,权限信息, principalCollection=[{}]", principalCollection);
        try {
            //获取用户名
            String username = (String) principalCollection.getPrimaryPrincipal();
            //获取角色信息
            List<RoleBO> roles = roleService.getRoleListByUsername(username);
            Set<Integer> roleIds = new HashSet<>();
            Set<String> roleNames = new HashSet<>();
            for (RoleBO role : roles) {
                roleIds.add(role.getId());
                roleNames.add(role.getName());
            }

            //获取权限信息
            Set<String> permissions = permissionService.getPermissionUrlsByRoleIds(roleIds, username);

            SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
            simpleAuthorizationInfo.setStringPermissions(permissions);
            simpleAuthorizationInfo.setRoles(roleNames);
            return simpleAuthorizationInfo;
        } catch (Exception e){
            logger.error("ShiroRealm.doGetAuthorizationInfo==>shiro获取角色,权限信息异常:" + e.getMessage(), e);
            if (e instanceof MyException){
                throw e;
            }else {
                throw new MyException(ResultEnum.SHIRO_REALM_AUTHORIZATION_ERROR);
            }
        }
    }

    /****
     * 主要是用来进行身份认证的，也就是说验证用户输入的账号和密码是否正确。
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        logger.debug("ShiroRealm.doGetAuthenticationInfo==>shiro身份验证, token=[{}]", token);
        try {
            //获取用户的输入的账号.
            String username = (String) token.getPrincipal();
            String json = (String) jedisUtil.get(appCacheDb, LoginConstant.LOGIN_ACCOUNT_PRE + username);
            AccountBO account = JsonUtils.jsonToPojo(json, AccountBO.class);
            if (account == null){
                return null;
            }

            jedisUtil.del(appCacheDb, LoginConstant.LOGIN_ACCOUNT_PRE + username);

            return new SimpleAuthenticationInfo(account.getUsername(), account.getPassword(), "shiroRealm");
        }catch (Exception e){
            logger.error("ShiroRealm.doGetAuthenticationInfo==>shiro身份验证异常:" + e.getMessage(), e);
            if (e instanceof MyException){
                throw e;
            }else {
                throw new MyException(ResultEnum.SHIRO_REALM_AUTHENTICATION_ERROR);
            }
        }
    }
}
