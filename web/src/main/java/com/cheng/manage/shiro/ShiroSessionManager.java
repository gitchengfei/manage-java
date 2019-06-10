package com.cheng.manage.shiro;

import com.cheng.manage.common.exception.MyException;
import com.cheng.manage.common.result.ResultEnum;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.session.mgt.WebSessionKey;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.Properties;


/**
 * @Description :
 * @Author : cheng fei
 * @Date : 2019/3/23 19:18
 */

public class ShiroSessionManager extends DefaultWebSessionManager {

    private Logger logger = LoggerFactory.getLogger(ShiroSessionManager.class);

    /**
     * token请求头Key
     */
    private String token;

    /**
     * session有效期
     */
    private long sessionExpire;


    private final String REFERENCED_SESSION_ID_SOURCE = "Stateless request";

    public ShiroSessionManager() {
        super();
        setGlobalSessionTimeout(sessionExpire);
    }

    public ShiroSessionManager(String token, long sessionExpire) {
        super();
        setGlobalSessionTimeout(sessionExpire);
        this.token = token;
        this.sessionExpire = sessionExpire;
    }

    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        logger.debug("ShiroSessionManager.getSessionId==>获取sessionID, request = [{}], response = [{}]", request, response);
        try {
            //如果请求头中有 token 则其值为sessionId
            HttpServletRequest req = WebUtils.toHttp(request);
            String id = req.getHeader(token);
            if (StringUtils.isBlank(id)) {
                id = req.getParameter(token);
            }
            logger.debug("ShiroSessionManager.getSessionID==>请求头中获取sessionId, sessionID = [{}]", id);
            if (StringUtils.isBlank(id)) {
                //如果没有携带id参数则按照父类的方式在cookie进行获取
                Serializable sessionId = super.getSessionId(request, response);
                logger.debug("ShiroSessionManager.getSessionID==>super获取sessionId, sessionID = [{}]", sessionId);
                return sessionId;
            } else {
                request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, REFERENCED_SESSION_ID_SOURCE);
                request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, id);
                request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
                return id;
            }
        } catch (Exception e) {
            logger.error("ShiroSessionManager.getSessionId==>获取sessionID异常:" + e.getMessage(), e);
            throw new MyException(ResultEnum.SHIRO_SESSION_MANAGER_GET_SESSION_ID_ERROR);
        }
    }


    /**
                     * 重写获取session的方法,不需要重复从reids中取session信息
                     *
                     * @param sessionKey
                     * @return
                     * @throws UnknownSessionException
                     */
                    @Override
                    protected Session retrieveSession(SessionKey sessionKey) throws UnknownSessionException {
                        logger.debug("ShiroSessionManager.retrieveSession==>获取session, sessionKey = [{}]", sessionKey);
                        try {
                            Serializable sessionId = getSessionId(sessionKey);
                            ServletRequest request = null;
                            if (sessionKey instanceof WebSessionKey) {
                                request = ((WebSessionKey) sessionKey).getServletRequest();
                            }
                            if (request != null && sessionId != null) {
                                Object attribute = request.getAttribute(sessionId.toString());
                                if (attribute != null) {
                                    if (attribute instanceof Session) {
                                        return (Session) attribute;
                                    }
                }
            }
            Session session = null;
            try {
                session = super.retrieveSession(sessionKey);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("获取session异常:" + e.getMessage(), e);
            }
            if (session != null) {
                request.setAttribute(sessionId.toString(), session);
            }
            return session;
        } catch (Exception e) {
            logger.error("ShiroSessionManager.retrieveSession==>获取session异常:" + e.getMessage(), e);
            throw new MyException(ResultEnum.SHIRO_SESSION_MANAGER_RETRIEVE_SESSION_ERROR);
        }
    }
}
