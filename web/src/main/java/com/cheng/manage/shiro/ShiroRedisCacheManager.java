package com.cheng.manage.shiro;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description : 自定义shiro 缓存管理类
 * @Author : cheng fei
 * @Date : 2019/3/24 03:06
 */
public class ShiroRedisCacheManager implements CacheManager {

    @Autowired
    private  ShiroRedisCache shiroRedisCache;

    @Override
    public <K, V> Cache<K, V> getCache(String s) throws CacheException {
        return shiroRedisCache;
    }
}
