package com.cheng.manage.service.system.db.log.impl;

import com.cheng.manage.common.date.DateUtils;
import com.cheng.manage.common.result.Result;
import com.cheng.manage.common.result.ResultEnum;
import com.cheng.manage.constant.app.system.db.log.DBLogConstant;
import com.cheng.manage.dao.system.dictionary.DictionaryMapper;
import com.cheng.manage.entity.system.db.log.DbLogDTO;
import com.cheng.manage.service.system.base.SystemBaseService;
import com.cheng.manage.service.system.db.log.DbLogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @Description : 数据库操作日志接口实现类
 * @Author : cheng fei
 * @Date : 2019/4/16 22:34
 */
@Service
public class DbLogServiceImpl extends SystemBaseService implements DbLogService {

    /**
     * 数据库操作类型
     */
    private static final String DB_LOG_TYPE = "SYSTEM_DB_LOG_TYPE_DB_LOG";

    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(DbLogServiceImpl.class);

    @Autowired
    private DictionaryMapper dictionaryMapper;

    @Override
    public Result getDbLogList(String type, String name, String startTime, String endTime, Integer page, Integer pageSize) {

        logger.debug("查询数据库操作日志列表: type=【 {} 】, name=【 {} 】, startTime=【 {} 】, endTime=【 {} 】, page=【 {} 】, pageSize=【 {} 】,", type, name, startTime, endTime, page, pageSize);

        //检测参数
        if (page < 1 || pageSize < 1) {
            return Result.build(ResultEnum.PARAM_ERROR);
        }
        type = StringUtils.isBlank(type) ? null : type;
        name = checkSearchString(name);
        startTime = checkCompareDate(startTime);
        endTime = checkCompareDate(endTime);

        //查询
        PageHelper.startPage(page, pageSize);
        List<DbLogDTO> list = dbLogMapper.getDbLogList(type, name, startTime, endTime);
        PageInfo<DbLogDTO> info = new PageInfo<>(list);
        List<DbLogDTO> rows = info.getList();
        for (DbLogDTO dbLogDTO : rows) {
            if (DBLogConstant.SYSTEM_ACCOUNT_ID.equals(dbLogDTO.getAccountId())) {
                dbLogDTO.setAccountName(DBLogConstant.SYSTEM_ACCOUNT_NAME);
            }
        }
        //返回
        return getResult(rows, info.getTotal());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result deleteBbLog(String key) {

        //检测参数
        if (StringUtils.isBlank(key)) {
            return Result.build(ResultEnum.PARAM_ERROR);
        }

        String value = dictionaryMapper.getValueByCode(key);

        if (StringUtils.isBlank(value) || !StringUtils.isNumeric(value)) {
            return Result.build(ResultEnum.DB_DATA_ERROR);
        }

        //计算删除时间
        Date date = DateUtils.dateAdd(new Date(), Integer.parseInt("-" + value), false);
        String beforeDate = DateUtils.dateFormat(date, DateUtils.DATE_PATTERN);
        dbLogMapper.deleteDdLog(beforeDate);

        StringBuffer log = new StringBuffer();
        log.append("删除了【 ").append(beforeDate).append(" 】之前的数据库操作日志");

        saveDBLog(getLoginAccountId(), log.toString(), DB_LOG_TYPE);

        return Result.succee();
    }
}
