package com.cheng.manage.controller.system.db.log;

import com.cheng.manage.common.result.Result;
import com.cheng.manage.controller.system.base.SystemBaseController;
import com.cheng.manage.entity.account.account.AccountBO;
import com.cheng.manage.service.system.db.log.DbLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description : 数据库操作日志Controller
 * @Author : cheng fei
 * @Date : 2019/4/16 22:27
 */
@RestController
@RequestMapping("/system/db/log")
@Api(tags = "system.db.log.DbLogController", description = "系统-数据库操作日志模块")
public class DbLogController extends SystemBaseController {

    @Autowired
    private DbLogService dbLogService;

    @PostMapping(value = "/list", produces = "application/json;charset=utf-8")
    @ApiOperation(value = "查询数据库操作日志列表", httpMethod = "POST", produces = "application/json")
    @RequiresPermissions("/system/db/log/list")
    public Result getDbLogList(
            @ApiParam(value = "类型", required = false) @RequestParam(required = false, defaultValue = "") String type,
            @ApiParam(value = "操作人", required = false) @RequestParam(required = false, defaultValue = "") String name,
            @ApiParam(value = "开始时间", required = false) @RequestParam(required = false, defaultValue = "") String startTime,
            @ApiParam(value = "结束时间", required = false) @RequestParam(required = false, defaultValue = "") String endTime,
            @ApiParam(value = "页码", required = false) @RequestParam(required = false, defaultValue = "1") Integer page,
            @ApiParam(value = "每页加载条数", required = false) @RequestParam(required = false, defaultValue = "10") Integer pageSize
    ){
        return dbLogService.getDbLogList(type, name, startTime, endTime, page, pageSize);
    }

    @PostMapping(value = "/delete", produces = "application/json;charset=utf-8")
    @ApiOperation(value = "删除数据库操作日志", httpMethod = "POST", produces = "application/json")
    @RequiresPermissions("/system/db/log/delete")
    public Result deleteDbLog(
            @ApiParam(value = "数据字典中删除数据库操作日志选项的key") @RequestParam(required = true) String key
    ){
        return dbLogService.deleteBbLog(key);
    }
}
