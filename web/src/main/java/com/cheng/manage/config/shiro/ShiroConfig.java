package com.cheng.manage.config.shiro;

import com.cheng.manage.shiro.*;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Description : shiro 配置
 * @Author : cheng fei
 * @Date : 2019/3/22 23:17
 */

@Configuration
public class ShiroConfig {

    private static Logger logger = LoggerFactory.getLogger(ShiroConfig.class);

    /**
     * token请求头Key
     */
    private static String token;

    /**
     * session有效期
     */
    private static long sessionExpire;

    @Value("${token.header}")
    public void setToken(String token){
        ShiroConfig.token = token;
    }

    @Value("${session.expire}")
    public void setToken(long sessionExpire){
        ShiroConfig.sessionExpire = sessionExpire;
    }

    /**
     * Shiro的Web过滤器Factory 命名:shiroFilter
     */
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        logger.debug("ShiroConfig.shiroFilterFactoryBean==>初始化shiroFilterFactoryBean...");

        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);

        //自定义过滤器
        Map<String, Filter> filters = shiroFilter.getFilters();
        filters.put("authc", myAuthenticationFilter());
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

        //配置不会被拦截的链接，顺序判断
        //swagger2
        filterChainDefinitionMap.put("/swagger-ui.html", "anon");
        filterChainDefinitionMap.put("/swagger-resources/**", "anon");
        filterChainDefinitionMap.put("/v2/api-docs", "anon");
        filterChainDefinitionMap.put("/webjars/**", "anon");
        //验证码
        filterChainDefinitionMap.put("/system/verification/code", "anon");
        //登陆
        filterChainDefinitionMap.put("/system/login", "anon");
        filterChainDefinitionMap.put("/system/login/logout", "anon");
        //authc:所有url必须通过认证才能访问，anon:所有url都可以匿名访问
        filterChainDefinitionMap.put("/**", "authc");
        shiroFilter.setFilterChainDefinitionMap(filterChainDefinitionMap);


        return shiroFilter;

    }

    @Bean
    public FilterRegistrationBean registration(MyAuthenticationFilter filter) {
        FilterRegistrationBean registration = new FilterRegistrationBean(filter);
        registration.setEnabled(false);
        return registration;
    }


    @Bean
    public MyAuthenticationFilter myAuthenticationFilter(){
        return new MyAuthenticationFilter();
    }

    @Bean
    public RedisSessionDao redisSessionDao(){
        logger.debug("ShiroConfig.redisSessionDao==>初始化redisSessionDao...");
        RedisSessionDao redisSessionDao = new RedisSessionDao();
        logger.debug("ShiroConfig.redisSessionDao==>初始化redisSessionDao完成!");
        return redisSessionDao;
    }

    /**
     * 自定义 session manager
     * @return
     */
    @Bean
    public ShiroSessionManager shiroSessionManager(){
        logger.debug("ShiroConfig.shiroSessionManager==>初始化shiroSessionManager...");
        ShiroSessionManager shiroSessionManager = new ShiroSessionManager(token,sessionExpire);
        shiroSessionManager.setSessionDAO(redisSessionDao());
        logger.debug("ShiroConfig.shiroSessionManager==>初始化shiroSessionManager完成!");
        return shiroSessionManager;
    }


    /**
     * 自定义 Realm
     * @return
     */
    @Bean
    public ShiroRealm shiroRealm(){
        logger.debug("ShiroConfig.shiroRealm==>初始化shiroRealm...");
        ShiroRealm shiroRealm = new ShiroRealm();
        logger.debug("ShiroConfig.shiroRealm==>初始化shiroRealm完成!");
        return shiroRealm;
    }

    @Bean
    public ShiroRedisCacheManager shiroRedisCacheManager(){
        logger.debug("ShiroConfig.shiroRedisCacheManager==>初始化shiroRedisCacheManager...");
        ShiroRedisCacheManager shiroRedisCacheManager = new ShiroRedisCacheManager();
        logger.debug("ShiroConfig.shiroRedisCacheManager==>初始化shiroRedisCacheManager完成!");
        return shiroRedisCacheManager;
    }

    /**
     * 不指定名字的话，自动创建一个方法名第一个字母小写的bean
     */
    @Bean
    public SecurityManager securityManager() {
        logger.debug("ShiroConfig.securityManager==>初始化securityManager...");
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setSessionManager(shiroSessionManager());
        securityManager.setRealm(shiroRealm());
        securityManager.setCacheManager(shiroRedisCacheManager());
        logger.debug("ShiroConfig.securityManager==>初始化securityManager完成!");
        return securityManager;
    }

    /// 未使用
//    /**
//     * 凭证匹配器
//     * （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了
//     * 所以我们需要修改下doGetAuthenticationInfo中的代码;
//     * ）
//     * 可以扩展凭证匹配器，实现 输入密码错误次数后锁定等功能，下一次
//     */
//    @Bean(name = "credentialsMatcher")
//    public HashedCredentialsMatcher hashedCredentialsMatcher() {
//        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
//        //散列算法:这里使用MD5算法;
//        hashedCredentialsMatcher.setHashAlgorithmName("md5");
//        //散列的次数，比如散列两次，相当于 md5(md5(""));
//        hashedCredentialsMatcher.setHashIterations(2);
//        //storedCredentialsHexEncoded默认是true，此时用的是密码加密用的是Hex编码；false时用Base64编码
//        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);
//        return hashedCredentialsMatcher;
//    }

    /**
     * Shiro生命周期处理器
     */
    @Bean
    public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        logger.debug("ShiroConfig.lifecycleBeanPostProcessor==>初始化lifecycleBeanPostProcessor...");
        LifecycleBeanPostProcessor lifecycleBeanPostProcessor = new LifecycleBeanPostProcessor();
        logger.debug("ShiroConfig.lifecycleBeanPostProcessor==>初始化lifecycleBeanPostProcessor完成!");
        return lifecycleBeanPostProcessor;
    }

    /**
     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator(可选)和AuthorizationAttributeSourceAdvisor)即可实现此功能
     */
    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        logger.debug("ShiroConfig.advisorAutoProxyCreator==>初始化advisorAutoProxyCreator...");
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        logger.debug("ShiroConfig.advisorAutoProxyCreator==>初始化advisorAutoProxyCreator完成!");
        return advisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        logger.debug("ShiroConfig.authorizationAttributeSourceAdvisor==>初始化authorizationAttributeSourceAdvisor...");
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
        logger.debug("ShiroConfig.authorizationAttributeSourceAdvisor==>初始化authorizationAttributeSourceAdvisor完成!");
        return authorizationAttributeSourceAdvisor;
    }

}
