package com.cheng.manage.shiro;

import com.cheng.manage.common.exception.MyException;
import com.cheng.manage.common.redis.JedisUtil;
import com.cheng.manage.common.result.Result;
import com.cheng.manage.common.result.ResultEnum;
import com.cheng.manage.common.util.MD5Utils;
import com.cheng.manage.common.json.JsonUtils;
import com.cheng.manage.constant.app.ApplicationConstant;
import com.cheng.manage.constant.app.system.login.LoginConstant;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author: cheng fei
 * @Date: 2019/5/17 15:14
 * @Description:
 */
public class MyAuthenticationFilter extends FormAuthenticationFilter {

    private static final Logger logger = LoggerFactory.getLogger(MyAuthenticationFilter.class);

    private static ResultEnum errorMsg = null;

    @Autowired
    private JedisUtil jedisUtil;

    @Value("${spring.redis.session.db}")
    protected int sessionDb;

    @Value("${app.pc.key}")
    private String appPcKey;

    @Value("${app.mobile.key}")
    private String appMobileKey;

    @Value("${over.time.header}")
    private String overTimeHeader;

    @Value("${sign.header}")
    private String signHeader;

    @Value("${debug}")
    private boolean debug;

    public MyAuthenticationFilter() {
        super();
    }


    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        //Always return true if the request's method is OPTIONSif (request instanceof HttpServletRequest) {
        HttpServletRequest req = ((HttpServletRequest) request);
        if (RequestMethod.OPTIONS.name().equals(req.getMethod())) {
            return true;
        }

        if (!debug) {
            //检测前端合法性
            String overTime = req.getHeader(overTimeHeader);
            if (StringUtils.isBlank(overTime)) {
                overTime = req.getParameter(overTimeHeader);
                if (StringUtils.isBlank(overTime)) {
                    errorMsg = ResultEnum.UNAUTHORIZED;
                    return false;
                }
            }
            if (!StringUtils.isNumeric(overTime)){
                errorMsg = ResultEnum.HTTP_TIME_OUT_HEADER_ERROR;
                return false;
            }

            long now = System.currentTimeMillis();
            if (Long.parseLong(overTime) < now) {
                errorMsg = ResultEnum.HTTP_TIME_OUT;
                return false;
            }
            String sign = req.getHeader(signHeader);
            if (StringUtils.isBlank(sign)) {
                sign = req.getParameter(signHeader);
            }
            String pcServiceSign = MD5Utils.MD5(overTime + appPcKey);
            String mobileServiceSign = MD5Utils.MD5(overTime + appMobileKey);
            if (!pcServiceSign.equals(sign) && !mobileServiceSign.equals(sign)) {
                errorMsg = ResultEnum.UNAUTHORIZED;
                return false;
            }
        }

        //检测session是否失效
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        if (session.getAttribute(LoginConstant.VALID_SESSION) != null) {
            String attribute = (String) session.getAttribute(LoginConstant.LOGIN_MSG);
            errorMsg = JsonUtils.jsonToPojo(attribute, ResultEnum.class) ;
            return (boolean)session.getAttribute(LoginConstant.VALID_SESSION);
        }

        return super.isAccessAllowed(request, response, mappedValue);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletResponse res = (HttpServletResponse) response;
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setStatus(HttpServletResponse.SC_OK);
        res.setCharacterEncoding("UTF-8");
        res.setContentType("application/json;charset=UTF-8");
        Result result = null;
        if (errorMsg == null){
            result = Result.build(ResultEnum.NOT_LOG_IN);
        } else {
            result = Result.build(errorMsg);
            errorMsg = null;
        }
        PrintWriter writer = response.getWriter();
        String json = JsonUtils.objectToJson(result);
        writer.write(json == null ? "" : json);
        writer.close();
        return false;
    }


    /**
     * 作者: cheng fei
     * 创建日期: 2019/5/24 17:20
     * 描述 : 返回拦截信息
     * @param response
     * @param resultEnum
     * @return
     * @throws IOException
     */
    private boolean getMsg(ServletResponse response, ResultEnum resultEnum) {
        try {
            HttpServletResponse res = (HttpServletResponse) response;
            res.setHeader("Access-Control-Allow-Origin", "*");
            res.setStatus(HttpServletResponse.SC_OK);
            res.setCharacterEncoding(ApplicationConstant.CODED_SET_UTF8);
            res.setContentType("application/json;charset=UTF-8");
            PrintWriter writer = response.getWriter();
            writer.print(JsonUtils.objectToJson(Result.build(resultEnum)));
            return false;
        } catch (Exception e) {
            throw new MyException(ResultEnum.CHECK_UNAUTHORIZED_ERROR);
        }
    }

}
