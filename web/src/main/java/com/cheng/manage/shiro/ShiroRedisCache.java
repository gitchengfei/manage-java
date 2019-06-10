package com.cheng.manage.shiro;


import com.cheng.manage.common.exception.MyException;
import com.cheng.manage.common.redis.JedisUtil;
import com.cheng.manage.common.result.ResultEnum;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

import java.util.*;

/**
 * @Description :
 * @Author : cheng fei
 * @Date : 2019/3/23 21:04
 */

@Component
public class ShiroRedisCache<K, V> implements Cache<K, V> {


    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(ShiroRedisCache.class);

    /**
     * shiro缓存前缀
     */
    private final String SHIRO_REDIS_CACHE_PREFIX = "shiro_redis_cache_pre_";


    @Autowired
    private JedisUtil jedisUtil;

    @Value("${spring.redis.shiro.cache.db}")
    private int redisShiroCacheDb;

    @Value("${shiro.cache.expire}")
    private int shiroCacheExpire;


    @Override
    public V get(K k) throws CacheException {
        logger.debug("ShiroRedisCache.get==>shiro获取缓存, k=[{}]", k);
        try {
            byte[] bytes = jedisUtil.get(redisShiroCacheDb, getKey(k), shiroCacheExpire);
            if (bytes != null){
                return (V) SerializationUtils.deserialize(bytes);
            }
            return null;
        } catch (Exception e) {
            logger.error("ShiroRedisCache.get==>shiro获取缓存异常:" + e.getMessage(), e);
            throw new MyException(ResultEnum.SHIRO_REDIS_CACHE_GET_ERROR);
        }
    }

    @Override
    public V put(K k, V v) throws CacheException {
        logger.debug("ShiroRedisCache.put==>shiro添加缓存, k=[{}],v=[{}]", k, v);
        try {
            jedisUtil.set(redisShiroCacheDb, getKey(k), SerializationUtils.serialize(v), shiroCacheExpire);
            return v;
        } catch (Exception e) {
            logger.error("ShiroRedisCache.put==>shiro添加缓存异常:" + e.getMessage(), e);
            throw new MyException(ResultEnum.SHIRO_REDIS_CACHE_PUT_ERROR);
        }
    }

    @Override
    public V remove(K k) throws CacheException {
        logger.debug("ShiroRedisCache.remove==>shiro删除缓存, k=[{}]", k);
        try {
            byte[] value = jedisUtil.remove(redisShiroCacheDb, getKey(k));
            if (value != null){
                return (V) SerializationUtils.deserialize(value);
            }
            return null;
        } catch (Exception e) {
            logger.error("ShiroRedisCache.remove==>shiro删除缓存异常:" + e.getMessage(), e);
            throw new MyException(ResultEnum.SHIRO_REDIS_CACHE_REMOVE_ERROR);
        }
    }

    @Override
    public void clear() throws CacheException {
        logger.debug("ShiroRedisCache.clear==>shiro清空缓存");
        try {
            jedisUtil.flushDB(redisShiroCacheDb);
        } catch (Exception e) {
            logger.error("ShiroRedisCache.clear==>shiro清空缓存异常:" + e.getMessage(), e);
            throw new MyException(ResultEnum.SHIRO_REDIS_CACHE_CLEAR_ERROR);
        }

    }

    @Override
    public int size() {
        logger.debug("ShiroRedisCache.size==>shiro获取缓存总数");
        try {
            Set<byte[]> keys = jedisUtil.keys(redisShiroCacheDb, "*".getBytes());
            return keys.size();
        } catch (Exception e) {
            logger.error("ShiroRedisCache.size==>shiro获取缓存总数异常:" + e.getMessage(), e);
            throw new MyException(ResultEnum.SHIRO_REDIS_CACHE_SIZE_ERROR);
        }
    }

    @Override
    public Set<K> keys() {
        logger.debug("ShiroRedisCache.keys==>shiro获取key Set");
        Set<K> data = new HashSet<>();
        try {
            Set<byte[]> keys = jedisUtil.keys(redisShiroCacheDb, "*".getBytes());
            for (byte[] key : keys) {
                data.add((K) SerializationUtils.deserialize(jedisUtil.get(redisShiroCacheDb, key, shiroCacheExpire)));
            }
        } catch (Exception e) {
            logger.error("ShiroRedisCache.keys==>shiro获取key Set异常:" + e.getMessage(), e);
            throw new MyException(ResultEnum.SHIRO_REDIS_CACHE_KEY_SET_ERROR);
        }
        return data;
    }

    @Override
    public Collection<V> values() {
        logger.debug("ShiroRedisCache.values==>shiro获取value list");
        List<V> list = new ArrayList<>();
        try {
            Set<byte[]> keys = jedisUtil.keys(redisShiroCacheDb, ("*").getBytes());
            for (byte[] key : keys) {
                byte[] bytes = jedisUtil.get(redisShiroCacheDb, key);
                list.add((V) SerializationUtils.deserialize(bytes));
            }
        } catch (Exception e) {
            logger.error("ShiroRedisCache.values==>shiro获取value list异常:" + e.getMessage(), e);
            throw new MyException(ResultEnum.SHIRO_REDIS_CACHE_VULE_COLLECTION_ERROR);
        }
        return list;
    }

    /**
     * 获取key
     *
     * @param key
     * @return
     */
    private byte[] getKey(K key) {

        if (key == null || "".equals(key) || "null".equals(key)) {
            logger.error("ShiroRedisCache.getKey==>shiro redis缓存传入的key为空");
            throw new MyException(ResultEnum.SHIRO_REDIS_CACHE_KEY_IS_NULL);
        }

        if (key instanceof String) {
            return (SHIRO_REDIS_CACHE_PREFIX + key).getBytes();
        } else {
            return (SHIRO_REDIS_CACHE_PREFIX + SerializationUtils.serialize(key).toString()).getBytes();
        }
    }
}
