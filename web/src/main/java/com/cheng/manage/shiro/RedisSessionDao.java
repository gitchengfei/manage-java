package com.cheng.manage.shiro;

import com.cheng.manage.common.exception.MyException;
import com.cheng.manage.common.redis.JedisUtil;
import com.cheng.manage.common.result.ResultEnum;
import com.cheng.manage.constant.app.system.login.LoginConstant;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.SerializationUtils;

import javax.xml.ws.soap.Addressing;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @Description : shiro 自定义session实现
 * @Author : cheng fei
 * @Date : 2019/3/23 03:24
 */


public class RedisSessionDao extends AbstractSessionDAO {

    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(RedisSessionDao.class);

    @Autowired
    private JedisUtil jedisUtil;

    @Value("${session.expire}")
    private int sessionExpire;

    @Value("${spring.redis.session.db}")
    private int redisSessionDb;

    private int expire;

    private int getExpire() {
        if (this.expire == 0) {
            this.expire = sessionExpire / 1000;
        }
        return expire;
    }


    private byte[] getKey(String key) {
        return (LoginConstant.ACCOUNT_SESSION_PRE + key).getBytes();
    }

    @Override
    public void update(Session session) throws MyException {
        if (session != null && session.getId() != null) {
            logger.debug("RedisSessionDao.update==>更新session,sessionID=[{}]", session.getId());
            try {
                byte[] serialize = SerializationUtils.serialize(session);
                jedisUtil.set(redisSessionDb, getKey(session.getId().toString()), serialize, getExpire());
            } catch (Exception e) {
                logger.error("RedisSessionDao.update==>更新session异常:" + e.getMessage(), e);
                throw new MyException(ResultEnum.SHIRO_REDIS_SESSION_UPDATE_ERROR);
            }
        }
    }

    @Override
    public void delete(Session session) {
        if (session != null && session.getId() != null) {
            logger.debug("RedisSessionDao.delete==>删除session,sessionID=[{}]", session.getId());
            try {
                jedisUtil.del(redisSessionDb, getKey(session.getId().toString()));
            } catch (Exception e) {
                logger.error("RedisSessionDao.delete==>删除session异常:" + e.getMessage(), e);
                throw new MyException(ResultEnum.SHIRO_REDIS_SESSION_DELETE_ERROR);
            }
        }

    }

    @Override
    public Collection<Session> getActiveSessions() {
        logger.debug("RedisSessionDao.getActiveSessions==>获取存活的session");
        Set<Session> sessions = new HashSet<>();
        try {
            Set<byte[]> keys = jedisUtil.keys(redisSessionDb, "*".getBytes());
            for (byte[] key : keys) {
                byte[] bytes = jedisUtil.get(redisSessionDb, key, getExpire());
                sessions.add((Session) SerializationUtils.deserialize(bytes));
            }
        } catch (Exception e) {
            logger.error("RedisSessionDao.getActiveSessions==>获取存活的session异常:" + e.getMessage(), e);
            throw new MyException(ResultEnum.SHIRO_REDIS_SESSION_SELECT_ACTIVE_ERROR);
        }
        return sessions;
    }

    @Override
    protected Serializable doCreate(Session session) {
        if (session != null) {
            logger.debug("RedisSessionDao.doCreate==>创建session,session=[{}]", session);
            try {
                Serializable sessionId = generateSessionId(session);
                assignSessionId(session, sessionId);
                jedisUtil.set(redisSessionDb, getKey(session.getId().toString()), SerializationUtils.serialize(session), getExpire());
                return sessionId;
            } catch (Exception e) {
                logger.error("RedisSessionDao.doCreate==>创建session异常," + e.getMessage(), e);
                throw new MyException(ResultEnum.SHIRO_REDIS_SESSION_CREATE_ERROR);
            }
        } else {
            logger.debug("RedisSessionDao.doCreate==>创建session,session为空");
            throw new MyException(ResultEnum.SHIRO_REDIS_SESSION_CREATE_ERROR);
        }
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        logger.debug("RedisSessionDao.doCreate==>获取session,sessionID=[{}]", sessionId);
        if (sessionId == null) {
            return null;
        }
        try {
            byte[] bytes = jedisUtil.get(redisSessionDb, getKey(sessionId.toString()), getExpire());
            return  (Session) SerializationUtils.deserialize(bytes);
        } catch (Exception e) {
            logger.error("RedisSessionDao.doCreate==>获取session异常:" + e.getMessage(), e);
            throw new MyException(ResultEnum.SHIRO_REDIS_SESSION_SELECT_ERROR);
        }
    }

}
