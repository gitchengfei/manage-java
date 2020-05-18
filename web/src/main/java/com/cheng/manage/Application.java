package com.cheng.manage;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author cheng fei
 * 说明:
 * SpringBootApplication : springboot应用启动注解
 * EnableSwagger2 : 开启Swagger2接口文档注解
 * MapperScan : 扫描mapper
 */

@SpringBootApplication
@EnableSwagger2
@EnableTransactionManagement
@MapperScan(basePackages = "com.cheng.manage.dao.*", annotationClass = Repository.class)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
