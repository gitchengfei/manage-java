package com.cheng.manage.config.quartz;

import com.cheng.manage.quartz.file.AutoDeleteFileQuartz;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * @Author: cheng fei
 * @Date: 2019/6/8 11:15
 * @Description: quartz 任务调度配置
 */
@Configuration
public class QuartzConfig {


    @Autowired
    private AutoDeleteFileQuartz autoDeleteFileQuartz;

    /**
     * 配置任务
     * @return
     */
    @Bean(name = "autoDeleteFile")
    public MethodInvokingJobDetailFactoryBean detailFactoryBean() {

        MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();

        // 是否并发执行
        jobDetail.setConcurrent(false);

        // 设置任务的名字
        jobDetail.setName("AutoDeleteFile");

        // 设置任务的分组，在多任务的时候使用
        jobDetail.setGroup("File");

        // 需要执行的对象
        jobDetail.setTargetObject(this.autoDeleteFileQuartz);

        /*
         * 执行AutoDeleteFileQuartz类中的需要执行方法
         */
        jobDetail.setTargetMethod("doDeleteFile");
        return jobDetail;
    }

    /**
     * 定时触发器
     * @param autoDeleteFile 任务
     * @return
     */
    @Bean(name = "autoDeleteFileTrigger")
    public CronTriggerFactoryBean cronJobTrigger(JobDetail autoDeleteFile){

        CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();

        trigger.setJobDetail(autoDeleteFile);

        //cron表达式，每日凌晨4点执行
        trigger.setCronExpression("0 0 4 * * ? *");
        //cron表达式，从0秒开始，每隔3秒执行一次，测试使用
        //trigger.setCronExpression("0/3 * * * * ?");
        trigger.setName("autoDeleteFileTrigger");
        return trigger;
    }

    /**
     * 调度工厂
     * @param autoDeleteFileTrigger 触发器
     * @return
     */
    @Bean(name = "scheduler")
    public SchedulerFactoryBean schedulerFactory(Trigger autoDeleteFileTrigger) {

        SchedulerFactoryBean factoryBean = new SchedulerFactoryBean();

        // 用于quartz集群,QuartzScheduler 启动时更新己存在的Job
        factoryBean.setOverwriteExistingJobs(true);

        // 延时启动，应用启动1秒后
        factoryBean.setStartupDelay(1);

        // 注册触发器
        factoryBean.setTriggers(autoDeleteFileTrigger);
        return factoryBean;
    }
}
