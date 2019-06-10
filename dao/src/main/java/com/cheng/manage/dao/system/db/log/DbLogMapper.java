package com.cheng.manage.dao.system.db.log;

import com.cheng.manage.common.mapper.BaseMapper;
import com.cheng.manage.entity.system.db.log.DbLogBO;
import com.cheng.manage.entity.system.db.log.DbLogDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface DbLogMapper extends BaseMapper<DbLogBO> {

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/16 22:42
     * 描述 : 查询数据库操作日志列表
     *
     * @param type      操作日志类型
     * @param name      操作人名称
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
     List<DbLogDTO> getDbLogList(@Param("type")String type, @Param("name")String name, @Param("startTime")String startTime, @Param("endTime")String endTime);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/16 23:49
     * 描述 : 删除数据库操作日志
     *
     * @param beforeDate 删除此时间之前的操作日志
     * @return
     */
    int deleteDdLog(@Param("beforeDate")String beforeDate);
}
