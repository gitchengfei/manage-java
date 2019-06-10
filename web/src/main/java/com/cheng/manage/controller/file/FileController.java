package com.cheng.manage.controller.file;

import com.cheng.manage.common.aliyun.AliyunOSSClientUtil;
import com.cheng.manage.common.result.Result;
import com.cheng.manage.controller.base.BaseController;
import com.cheng.manage.entity.file.FileBO;
import com.cheng.manage.service.file.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Id;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.Date;

/**
 * @Author: cheng fei
 * @Date: 2019/6/1 21:24
 * @Description:
 */
@RestController
@RequestMapping("/file")
@Api(tags = "FileController", description = "文件模块")
public class FileController extends BaseController {

    @Autowired
    private FileService fileService;

    @Autowired
    private AliyunOSSClientUtil aliyunOSSClientUtil;

    @PostMapping("/save")
    @ApiOperation(value = "保存文件(用于nodeJS上传文件至阿里云OSS后, 保存文件)", httpMethod = "POST", produces = "application/json")
    public Result saveFile(
            @ApiParam(value = "阿里云上传的文件名称", required = true) @RequestParam(required = true) String uploadFileName
    ) {
        return fileService.saveFile(uploadFileName);
    }

    @RequiresPermissions("/file/list")
    @PostMapping("/list")
    @ApiOperation(value = "获取文件列表", httpMethod = "POST", produces = "application/json")
    public Result getFileList(
        @ApiParam(value = "文件名称(模糊查询)", required = false) @RequestParam(required = false, defaultValue = "") String fileName,
        @ApiParam(value = "类型(数据字典中的类型)", required = false) @RequestParam(required = false, defaultValue = "") String type,
        @ApiParam(value = "文件用途(数据字典中的类型)", required = false) @RequestParam(required = false, defaultValue = "") String filePurpose,
        @ApiParam(value = "上传开始时间", required = false) @RequestParam(required = false, defaultValue = "") String startTime,
        @ApiParam(value = "上传结束时间", required = false) @RequestParam(required = false, defaultValue = "") String endTime,
        @ApiParam(value = "页码", required = false) @RequestParam(required = false, defaultValue = "") Integer page,
        @ApiParam(value = "每页加载条数", required = false) @RequestParam(required = false, defaultValue = "") Integer pageSize
    ) {
        return fileService.getFileList(fileName, type, filePurpose, startTime, endTime, page, pageSize);
    }

    @RequiresPermissions("/file/update/name")
    @PostMapping("/update/name")
    @ApiOperation(value = "修改文件名称", httpMethod = "POST", produces = "application/json")
    public Result updateFileName (
            @ApiParam(value = "文件Id", required = true) @RequestParam(required = true) Integer fileId,
            @ApiParam(value = "文件名称", required = true) @RequestParam(required = true) String fileName,
            @ApiParam(value = "上次修改时间", required = true) @RequestParam(required = true) Long updateTime
    ) {

        FileBO fileBO = new FileBO();
        fileBO.setId(fileId);
        fileBO.setFileName(fileName);
        fileBO.setUpdateTime(updateTime == null ? null : new Date(updateTime));

        return fileService.updateFileName(fileBO);
    }

    @RequiresPermissions("/file/delete")
    @PostMapping("/delete")
    @ApiOperation(value = "删除文件", httpMethod = "POST", produces = "application/json")
    public Result deleteFile (
            @ApiParam(value = "文件Id列表, 用\",\"号隔开", required = true) @RequestParam(required = true) String fileIds
    ) {
        return fileService.deleteFile(fileIds);
    }

    @RequiresPermissions("/file/download")
    @GetMapping("/download")
    @ApiOperation(value = "下载文件", httpMethod = "POST", produces = "application/json")
    public Result downloadFile (
            @ApiParam(value = "文件Id", required = true) @RequestParam(required = true) Integer fileId,
            @ApiParam(hidden = true) HttpServletResponse response
    ) {
        FileBO fileBO = fileService.downloadFile(fileId);
        InputStream inputStream = aliyunOSSClientUtil.downLoadFile(fileBO.getPath());

        download(response, inputStream, fileBO.getFileName(), fileBO.getSize());
        aliyunOSSClientUtil.closeOSSClient();
        return null;
    }

    @RequiresPermissions("/file/list")
    @PostMapping("/use/info")
    @ApiOperation(value = "文件使用信息", httpMethod = "POST", produces = "application/json")
    public Result getFileUseInfo (
            @ApiParam(value = "文件Id", required = true) @RequestParam(required = true) Integer fileId
    ) {
        return fileService.getFileUseInfo(fileId);
    }

}
