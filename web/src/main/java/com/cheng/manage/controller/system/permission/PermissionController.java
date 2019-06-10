package com.cheng.manage.controller.system.permission;

import com.cheng.manage.common.result.Result;
import com.cheng.manage.controller.system.base.SystemBaseController;
import com.cheng.manage.entity.system.permission.PermissionBO;
import com.cheng.manage.service.system.permission.PermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;


/**
 * @Description : 权限管理Controller
 * @Author : cheng fei
 * @Date : 2019/4/17 22:40
 */
@RestController
@RequestMapping("/system/permission")
@Api(tags = "system.permission.PermissionController", description = "系统-权限管理模块")
public class PermissionController extends SystemBaseController {

    @Autowired
    private PermissionService permissionService;

    @PostMapping("/save")
    @ApiOperation(value = "添加权限接口", httpMethod = "POST", produces = "application/json")
    @RequiresPermissions("/system/permission/save")
    public Result savePermission(
            @ApiParam(value = "菜单ID", required = true) @RequestParam(required = true) Integer menuId,
            @ApiParam(value = "权限名称", required = true) @RequestParam(required = true) String name,
            @ApiParam(value = "url", required = true) @RequestParam(required = true) String url,
            @ApiParam(value = "排序码", required = true) @RequestParam(required = true) Integer displayOrder,
            @ApiParam(value = "备注", required = true) @RequestParam(required = true) String remark
    ) {
        PermissionBO permission = new PermissionBO();
        permission.setMenuId(menuId);
        permission.setName(name);
        permission.setUrl(url);
        permission.setDisplayOrder(displayOrder);
        permission.setRemark(remark);

        return permissionService.saveOrUpdatePermission(permission, false);
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改权限接口", httpMethod = "POST", produces = "application/json")
    @RequiresPermissions("/system/permission/update")
    public Result updatePermission(
            @ApiParam(value = "权限id", required = true) @RequestParam(required = true) Integer id,
            @ApiParam(value = "菜单ID", required = true) @RequestParam(required = true) Integer menuId,
            @ApiParam(value = "权限名称", required = true) @RequestParam(required = true) String name,
            @ApiParam(value = "url", required = true) @RequestParam(required = true) String url,
            @ApiParam(value = "排序码", required = true) @RequestParam(required = true) Integer displayOrder,
            @ApiParam(value = "备注", required = true) @RequestParam(required = true) String remark,
            @ApiParam(value = "备注", required = true) @RequestParam(required = true) Long updateTime

    ) {
        PermissionBO permission = new PermissionBO();
        permission.setId(id);
        permission.setMenuId(menuId);
        permission.setName(name);
        permission.setDisplayOrder(displayOrder);
        permission.setUrl(url);
        permission.setRemark(remark);
        permission.setUpdateTime(updateTime == null ? null : new Date(updateTime));
        return permissionService.saveOrUpdatePermission(permission, false);
    }

    @PostMapping("/update/status")
    @ApiOperation(value = "修改权限状态接口", httpMethod = "POST", produces = "application/json")
    @RequiresPermissions("/system/permission/update/status")
    public Result updatePermissionStatus(
            @ApiParam(value = "权限id", required = true) @RequestParam(required = true) Integer id,
            @ApiParam(value = "状态", required = true) @RequestParam(required = true) Boolean status,
            @ApiParam(value = "备注", required = true) @RequestParam(required = true) Long updateTime
    ) {
        PermissionBO permission = new PermissionBO();
        permission.setId(id);
        permission.setStatus(status);
        permission.setUpdateTime(updateTime == null ? null : new Date(updateTime));

        return permissionService.saveOrUpdatePermission(permission, true);
    }

    @PostMapping("/list")
    @ApiOperation(value = "查询权限列表接口", httpMethod = "POST", produces = "application/json")
    @RequiresPermissions("/system/permission/list")
    public Result getPermissionList(
            @ApiParam(value = "所属菜单ID", required = true) @RequestParam(required = true) Integer menuId,
            @ApiParam(value = "页码", required = true) @RequestParam(required = true) Integer page,
            @ApiParam(value = "每页加载条数", required = true) @RequestParam(required = true) Integer pageSize
    ) {
        return permissionService.getPermissionList(menuId, page, pageSize);
    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除权限列表接口", httpMethod = "POST", produces = "application/json")
    @RequiresPermissions("/system/permission/delete")
    public Result deletePermission(
            @ApiParam(value = "权限ID", required = true) @RequestParam(required = true) Integer id
    ) {
        return permissionService.deletePermission(id);
    }

    @PostMapping("/check/name")
    @ApiOperation(value = "检测权限名称", httpMethod = "POST", produces = "application/json")

    public Result checkName(
            @ApiParam(value = "菜单ID", required = true) @RequestParam(required = true) Integer menuId,
            @ApiParam(value = "权限名称", required = true) @RequestParam(required = true) String name,
            @ApiParam(value = "排除ID", required = true) @RequestParam(required = true) Integer excludeId

    ) {
        return permissionService.checkName(menuId, name, excludeId);
    }

    @PostMapping("/check/url")
    @ApiOperation(value = "检测权限url", httpMethod = "POST", produces = "application/json")
    public Result checkUrl(
            @ApiParam(value = "权限url", required = true) @RequestParam(required = true) String url,
            @ApiParam(value = "排除ID", required = true) @RequestParam(required = true) Integer excludeId

    ) {
        return permissionService.checkUrl(url, excludeId);
    }
}
