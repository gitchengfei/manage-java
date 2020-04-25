package com.cheng.manage.controller.account.role;

import com.cheng.manage.common.result.Result;
import com.cheng.manage.controller.account.base.AccountBaseController;
import com.cheng.manage.entity.account.role.RoleBO;
import com.cheng.manage.service.account.role.RoleService;
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
 * @Author: cheng fei
 * @Date: 2019/4/23 21:57
 * @Description: 角色Controller
 */

@RestController
@RequestMapping("/account/role")
@Api(tags = "account.role.RoleController", description = "账号-角色模块")
public class RoleController extends AccountBaseController {

    @Autowired
    private RoleService roleService;

    @PostMapping("/save")
    @ApiOperation(value = "添加角色", httpMethod = "POST", produces = "application/json")
    @RequiresPermissions("/account/role/save")
    public Result saveRole(
            @ApiParam(value = "角色名称") @RequestParam(required = true) String name,
            @ApiParam(value = "角色编码") @RequestParam(required = true) String roleCode,
            @ApiParam(value = "排序码") @RequestParam(required = true) Integer displayOrder,
            @ApiParam(value = "备注") @RequestParam(required = false, defaultValue = "") String remark
    ) {
        RoleBO roleBO = new RoleBO();
        roleBO.setName(name);
        roleBO.setRoleCode(roleCode);
        roleBO.setDisplayOrder(displayOrder);
        roleBO.setRemark(remark);
        return roleService.saveOrUpdateRole(roleBO, false);
    }


    @PostMapping("/update")
    @ApiOperation(value = "修改角色", httpMethod = "POST", produces = "application/json")
    @RequiresPermissions("/account/role/update")
    public Result updateRole(
            @ApiParam(value = "角色id") @RequestParam(required = true) Integer id,
            @ApiParam(value = "角色名称") @RequestParam(required = true) String name,
            @ApiParam(value = "角色编码") @RequestParam(required = true) String roleCode,
            @ApiParam(value = "排序码") @RequestParam(required = true) Integer displayOrder,
            @ApiParam(value = "备注") @RequestParam(required = false, defaultValue = "") String remark,
            @ApiParam(value = "历史修改时间") @RequestParam(required = false, defaultValue = "") Long updateTime
    ) {
        RoleBO roleBO = new RoleBO();
        roleBO.setId(id);
        roleBO.setName(name);
        roleBO.setRoleCode(roleCode);
        roleBO.setDisplayOrder(displayOrder);
        roleBO.setRemark(remark);
        roleBO.setUpdateTime(updateTime == null ? null : new Date(updateTime));

        return roleService.saveOrUpdateRole(roleBO, false);
    }

    @PostMapping("/update/status")
    @ApiOperation(value = "修改角色状态", httpMethod = "POST", produces = "application/json")
    @RequiresPermissions("/account/role/update/status")
    public Result updateRoleStatus(
            @ApiParam(value = "角色id") @RequestParam(required = true) Integer id,
            @ApiParam(value = "角色状态") @RequestParam(required = true) Boolean status,
            @ApiParam(value = "历史修改时间") @RequestParam(required = false, defaultValue = "") Long updateTime
    ) {
        RoleBO roleBO = new RoleBO();
        roleBO.setId(id);
        roleBO.setStatus(status);
        roleBO.setUpdateTime(updateTime == null ? null : new Date(updateTime));

        return roleService.saveOrUpdateRole(roleBO, true);
    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除角色接口", httpMethod = "POST", produces = "application/json")
    @RequiresPermissions("/account/role/delete")
    public Result deleteRole(
            @ApiParam(value = "角色ID", required = true) @RequestParam(required = true) Integer id
    ){
        return roleService.deleteRole(id);
    }

    @PostMapping("list")
    @ApiOperation(value = "获取角色列表", httpMethod = "POST", produces = "application/json")
    @RequiresPermissions("/account/role/list")
    public Result getRoleList(
            @ApiParam(value = "角色名称(模糊)") @RequestParam(required = true) String name,
            @ApiParam(value = "角色编码(模糊)") @RequestParam(required = true) String roleCode,
            @ApiParam(value = "页码") @RequestParam(required = false,defaultValue = "1") Integer page,
            @ApiParam(value = "每页加载条数") @RequestParam(required = false,defaultValue = "10") Integer pageSize
    ) {
        return roleService.getRoleList(name, roleCode, page, pageSize);
    }

    @PostMapping("check/name")
    @ApiOperation(value = "检测角色名称", httpMethod = "POST", produces = "application/json")
    public Result checkRoleName(
            @ApiParam(value = "角色名称") @RequestParam(required = true) String name,
            @ApiParam(value = "需要排除的Id的") @RequestParam(required = false, defaultValue = "") Integer excludeId
    ) {
        return roleService.checkName(name, excludeId);
    }

    @PostMapping("/permission/update")
    @ApiOperation(value = "修改角色权限", httpMethod = "POST", produces = "application/json")
    @RequiresPermissions("/account/role/permission/update")
    public Result updateRolePermission(
            @ApiParam(value = "角色id") @RequestParam(required = true) Integer id,
            @ApiParam(value = "权限列表,用\",\"号隔开,为空时无权限") @RequestParam(required = false, defaultValue = "") String permissions
    ) {
        return roleService.updateRolePermission(id, permissions);
    }

    @PostMapping("/permission/get")
    @ApiOperation(value = "查询角色权限", httpMethod = "POST", produces = "application/json")
    public Result getRolePermission(
            @ApiParam(value = "角色id") @RequestParam(required = true) Integer id
    ) {
        return roleService.getRolePermission(id);
    }

    @PostMapping("/checkbox")
    @ApiOperation(value = "查询角色权限", httpMethod = "POST", produces = "application/json")
    public Result getRoleCheckbox(
    ) {
        return roleService.getRoleCheckbox();
    }


    @PostMapping("/check/code")
    @ApiOperation(value = "检测角色编码", httpMethod = "POST", produces = "application/json")
    public Result checkRoleCode(
            @ApiParam(value = "编码", required = true) @RequestParam(required = true) String code,
            @ApiParam(value = "需要排除的Id", required = false) @RequestParam(required = false, defaultValue = "") Integer excludeId
    ) {
        return roleService.checkRoleCode(code, excludeId);
    }


}
