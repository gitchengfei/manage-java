package com.cheng.manage.config.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Description : web 配置
 * @Author : cheng fei
 * @Date : 2019/3/24 23:25
 */

@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * 设置跨域访问
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
                //设置跨域的地址映射
        registry.addMapping("/**")
                //设置跨域可访问的域名
                .allowedOrigins("*")
                //设置跨域的方法
                .allowedMethods("*")
                //设置跨域的请求头
                .allowedHeaders("*")
                .allowCredentials(true);
    }

    /**
     * 设置拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new CrossDomainInterceptor()).addPathPatterns("/*");
    }

}
