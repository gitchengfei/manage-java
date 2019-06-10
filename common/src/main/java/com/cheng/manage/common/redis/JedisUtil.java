package com.cheng.manage.common.redis;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Set;

/**
 * @Description : jedis 工具类
 * @Author : cheng fei
 * @Date : 2019/3/25 22:00
 */

@Component
public class JedisUtil {

    private static final String ASTERISK = "*";

    @Autowired
    private JedisPool jedisPool;

    @Value("${spring.redis.password}")
    private String redisPassword;

    /**
     * 获取jedis连接
     *
     * @param redisDatabase reids数据库
     * @return
     */
    private Jedis getJedis(int redisDatabase) {
        Jedis jedis = jedisPool.getResource();
        if (StringUtils.isNotBlank(redisPassword)) {
            jedis.auth(redisPassword);
        }
        jedis.select(redisDatabase);
        return jedis;
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/3/25 22:11
     * 描述 : redis set key value
     *
     * @param redisDatabase reids数据库
     * @param key           key
     * @param value         value
     * @return
     */
    public String set(int redisDatabase, String key, String value) {
        Jedis jedis = getJedis(redisDatabase);
        String result;
        try {
            result = jedis.set(key, value);
        } catch (Exception e) {
            throw e;
        } finally {
            jedis.close();
        }
        return result;
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/3/25 22:32
     * 描述 : redis set key value 有效期
     *
     * @param redisDatabase reids数据库
     * @param key           key
     * @param value         value
     * @param expire        expire(单位秒)
     */
    public void set(int redisDatabase, String key, String value, int expire) {
        Jedis jedis = getJedis(redisDatabase);
        try {
            jedis.set(key, value);
            jedis.expire(key, expire);
        } catch (Exception e) {
            throw e;
        } finally {
            jedis.close();
        }
    }


    /**
     * 作者: cheng fei
     * 创建日期: 2019/3/25 22:11
     * 描述 : redis set key value
     *
     * @param redisDatabase reids数据库
     * @param key           key
     * @param value         value
     * @return
     */
    public String set(int redisDatabase, byte[] key, byte[] value) {
        Jedis jedis = getJedis(redisDatabase);
        String result;
        try {
            result = jedis.set(key, value);
        } catch (Exception e) {
            throw e;
        } finally {
            jedis.close();
        }
        return result;
    }


    /**
     * 作者: cheng fei
     * 创建日期: 2019/3/25 22:32
     * 描述 : redis set key value 有效期
     *
     * @param redisDatabase reids数据库
     * @param key           key
     * @param value         value
     * @param expire        有效期(秒)
     */
    public void set(int redisDatabase, byte[] key, byte[] value, int expire) {
        Jedis jedis = getJedis(redisDatabase);
        try {
            jedis.set(key, value);
            jedis.expire(key, expire);
        } catch (Exception e) {
            throw e;
        } finally {
            jedis.close();
        }
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/3/25 22:11
     * 描述 : redis获取缓存
     *
     * @param redisDatabase reids数据库
     * @param key           key
     * @return
     */
    public String get(int redisDatabase, String key) {
        Jedis jedis = getJedis(redisDatabase);
        String value;
        try {
            value = jedis.get(key);
        } catch (Exception e) {
            throw e;
        } finally {
            jedis.close();
        }
        return value;
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/5/17 14:23
     * 描述 : 获取reids缓存,并刷新有效时间
     *
     * @param redisDatabase reids数据库
     * @param key           Key
     * @param seconds       有效时间
     * @return
     */
    public String get(int redisDatabase, String key, int seconds) {
        Jedis jedis = getJedis(redisDatabase);
        String value;
        try {
            value = jedis.get(key);
            jedis.expire(key, seconds);
        } catch (Exception e) {
            throw e;
        } finally {
            jedis.close();
        }

        return value;
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/3/25 22:11
     * 描述 : redis get key
     *
     * @param redisDatabase reids数据库
     * @param key           key
     * @return
     */
    public byte[] get(int redisDatabase, byte[] key) {
        Jedis jedis = getJedis(redisDatabase);
        byte[] value;
        try {
            value = jedis.get(key);
        } catch (Exception e) {
            throw e;
        } finally {

            jedis.close();
        }
        return value;
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/3/25 22:11
     * 描述 : redis get key
     *
     * @param redisDatabase reids数据库
     * @param key           key
     * @param expire           有效期
     * @return
     */
    public byte[] get(int redisDatabase, byte[] key, int expire) {
        Jedis jedis = getJedis(redisDatabase);
        byte[] value;
        try {
            value = jedis.get(key);
            jedis.expire(key, expire);
        } catch (Exception e) {
            throw e;
        } finally {

            jedis.close();
        }
        return value;
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/3/25 22:16
     * 描述 : redis del key
     *
     * @param redisDatabase reids数据库
     * @param key           key
     * @return
     */
    public Long del(int redisDatabase, String key) {
        Jedis jedis = getJedis(redisDatabase);
        try {
            if (jedis.exists(key)) {
                Long del = jedis.del(key);
                return del;
            }
        } catch (Exception e) {
            throw e;
        } finally {
            jedis.close();
        }
        return 0L;
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/5/17 11:45
     * 描述 :  删除相同前缀的缓存
     *
     * @param redisDatabase reids数据库
     * @param pattern       匹配的key
     * @return
     */
    public Long delAll(int redisDatabase, String pattern) {
        Jedis jedis = getJedis(redisDatabase);
        Long i = 0L;
        try {
            Set<String> keys = jedis.keys(pattern);
            for (String key : keys) {
                if (jedis.exists(key)) {
                    Long del = jedis.del(key);
                    i += del;
                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            jedis.close();
        }
        return i;
    }


    /**
     * 作者: cheng fei
     * 创建日期: 2019/3/25 22:49
     * 描述 : redis remove
     *
     * @param redisDatabase reids数据库
     * @param key           key
     * @return
     */
    public String remove(int redisDatabase, String key) {
        Jedis jedis = getJedis(redisDatabase);
        String value = null;
        try {
            if (jedis.exists(key)) {
                value = jedis.get(key);
                jedis.del(key);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            jedis.close();
        }
        return value;
    }


    /**
     * 作者: cheng fei
     * 创建日期: 2019/3/25 22:16
     * 描述 : redis del key
     *
     * @param redisDatabase reids数据库
     * @param key
     * @return
     */
    public Long del(int redisDatabase, byte[] key) {
        Jedis jedis = getJedis(redisDatabase);
        Long del = 0L;
        try {
            if (jedis.exists(key)) {
                del = jedis.del(key);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            jedis.close();
        }
        return del;
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/3/25 22:49
     * 描述 : redis remove
     *
     * @param redisDatabase reids数据库
     * @param key
     * @return
     */
    public byte[] remove(int redisDatabase, byte[] key) {
        Jedis jedis = getJedis(redisDatabase);
        byte[] value = null;
        try {
            if (jedis.exists(key)) {
                value = jedis.get(key);
                jedis.del(key);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            jedis.close();
        }
        return value;
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/3/25 22:18
     * 描述 : reids 设置过期时间
     *
     * @param redisDatabase reids数据库
     * @param key
     * @param seconds
     * @return
     */
    public Long expire(int redisDatabase, String key, int seconds) {
        Jedis jedis = getJedis(redisDatabase);
        Long expire;
        try {
            expire = jedis.expire(key, seconds);
        } catch (Exception e) {
            throw e;
        } finally {
            jedis.close();
        }
        return expire;
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/3/25 22:18
     * 描述 : reids 设置过期时间
     *
     * @param redisDatabase reids数据库
     * @param key
     * @param seconds
     * @return
     */
    public Long expire(int redisDatabase, byte[] key, int seconds) {
        Jedis jedis = getJedis(redisDatabase);
        Long expire;
        try {
            expire = jedis.expire(key, seconds);
        } catch (Exception e) {
            throw e;
        } finally {
            jedis.close();
        }
        return expire;
    }


    /**
     * 作者: cheng fei
     * 创建日期: 2019/3/25 22:22
     * 描述 : reids 检测是否存在key
     *
     * @param redisDatabase reids数据库
     * @param key
     * @return
     */
    public boolean exists(int redisDatabase, String key) {
        Jedis jedis = getJedis(redisDatabase);
        Boolean exists;
        try {
            exists = jedis.exists(key);
        } catch (Exception e) {
            throw e;
        } finally {
            jedis.close();
        }
        return exists;
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/3/25 22:22
     * 描述 : reids 检测是否存在key
     *
     * @param redisDatabase reids数据库
     * @param key
     * @return
     */
    public Boolean exists(int redisDatabase, byte[] key) {
        Jedis jedis = getJedis(redisDatabase);
        Boolean exists;
        try {
            exists = jedis.exists(key);
        } catch (Exception e) {
            throw e;
        } finally {
            jedis.close();
        }
        return exists;
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/3/25 22:26
     * 描述 : 获取reids 指定前缀key set
     *
     * @param redisDatabase reids数据库
     * @param pattern
     * @return
     */
    public Set<String> keys(int redisDatabase, String pattern) {
        Jedis jedis = getJedis(redisDatabase);
        Set<String> keys;
        try {
            keys = jedis.keys(pattern);
        } catch (Exception e) {
            throw e;
        } finally {
            jedis.close();
        }
        return keys;
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/3/25 22:26
     * 描述 : 获取reids 指定前缀key set
     *
     * @param redisDatabase reids数据库
     * @param pattern
     * @return
     */
    public Set<byte[]> keys(int redisDatabase, byte[] pattern) {
        Jedis jedis = getJedis(redisDatabase);
        Set<byte[]> keys;
        try {
            keys = jedis.keys(pattern);
        } catch (Exception e) {
            throw e;
        } finally {
            jedis.close();
        }
        return keys;
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/5/27 22:53
     * 描述 : 清空选中数据库
     *
     * @param redisDatabase reids数据库
     * @return
     */
    public String flushDB(int redisDatabase) {
        Jedis jedis = getJedis(redisDatabase);
        String s;
        try {
            s = jedis.flushDB();
        } catch (Exception e) {
            throw e;
        } finally {
            jedis.close();
        }
        return s;
    }


}
