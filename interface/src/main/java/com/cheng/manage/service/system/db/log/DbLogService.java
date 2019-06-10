package com.cheng.manage.service.system.db.log;

import com.cheng.manage.common.result.Result;
import com.cheng.manage.entity.account.account.AccountBO;

/**
 * @Description : 数据库操作日志接口
 * @Author : cheng fei
 * @Date : 2019/4/16 22:34
 */
public interface DbLogService {

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/16 22:39
     * 描述 : 查询数据字典操作日志
     * @param type 操作类型
     * @param name 操作人名称(模糊)
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param page 页码
     * @param pageSize 每页条数
     * @return
     */
    Result getDbLogList(String type, String name, String startTime, String endTime, Integer page, Integer pageSize);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/16 23:25
     * 描述 : 删除数据库操作日志
     * @param key 数据字典中删除数据库操作日志选项的key
     * @return
     */
    Result deleteBbLog(String key);
}
