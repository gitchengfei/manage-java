package com.cheng.manage.controller.system.menu;

import com.cheng.manage.common.result.Result;
import com.cheng.manage.controller.system.base.SystemBaseController;
import com.cheng.manage.entity.account.account.AccountBO;
import com.cheng.manage.entity.system.menu.MenuBO;
import com.cheng.manage.service.system.menu.impl.MenuService;
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
 * @Description :
 * @Author : cheng fei
 * @Date : 2019/3/26 00:39
 */

@RestController
@RequestMapping("/system/menu")
@Api(tags = "system.menu.MenuController", description = "系统-菜单模块")
public class MenuController extends SystemBaseController {

    private final String baseURL = "/system/menu";

    @Autowired
    private MenuService menuService;

    @PostMapping(value = "/save", produces = "application/json;charset=utf-8")
    @ApiOperation(value = "添加菜单", httpMethod = "POST", produces = "application/json")
    @RequiresPermissions("/system/menu/save")
    public Result saveMenu(
            @ApiParam(value = "菜单名称", required = true) @RequestParam(required = true) String name,
            @ApiParam(value = "url", required = true) @RequestParam(required = true) String url,
            @ApiParam(value = "排序码", required = true) @RequestParam(required = true) Integer displayOrder,
            @ApiParam(value = "父菜单ID", required = true) @RequestParam(required = true) Integer parentId
    ){
        MenuBO menu = new MenuBO();
        menu.setName(name);
        menu.setUrl(url);
        menu.setDisplayOrder(displayOrder);
        menu.setParentId(parentId);
        //获取当前登陆人员
        return menuService.saveORUpdateMenu(menu, false);
    }


    @PostMapping("/update")
    @ApiOperation(value = "修改菜单", httpMethod = "POST", produces = "application/json")
    @RequiresPermissions("/system/menu/update")
    public Result saveMenu(
            @ApiParam(value = "菜单id", required = true) @RequestParam(required = true) Integer id,
            @ApiParam(value = "菜单名称", required = true) @RequestParam(required = true) String name,
            @ApiParam(value = "url", required = true) @RequestParam(required = true) String url,
            @ApiParam(value = "排序码", required = true) @RequestParam(required = true) Integer displayOrder,
            @ApiParam(value = "父菜单ID", required = true) @RequestParam(required = true) Integer parentId,
            @ApiParam(value = "updateTime", required = true) @RequestParam(required = true) Long updateTime

    ){
        MenuBO menu = new MenuBO();
        menu.setId(id);
        menu.setName(name);
        menu.setUrl(url);
        menu.setDisplayOrder(displayOrder);
        menu.setParentId(parentId);
        menu.setUpdateTime(updateTime == null ? null : new Date(updateTime));
        //获取当前登陆人员
        return menuService.saveORUpdateMenu(menu, false);
    }

    @PostMapping(value = "/list", produces = "application/json;charset=utf-8")
    @ApiOperation(value = "查询菜单列表", httpMethod = "POST", produces = "application/json")
    @RequiresPermissions("/system/menu/list")
    public Result selectMenuList(
            @ApiParam( value = "父菜单ID", required = false) @RequestParam(required = false, defaultValue = "0") Integer parentId,
            @ApiParam( value = "菜单状态:true/false(不传或传\"\"查询全部)", required = false) @RequestParam(required = false, defaultValue = "") Boolean status
    ){
        //获取当前登陆人员
        return menuService.getMenuList(parentId, status);
    }

    @PostMapping(value = "/tree", produces = "application/json;charset=utf-8")
    @ApiOperation(value = "查询菜单树列表", httpMethod = "POST", produces = "application/json")
    public Result selectMenuTreeList(
            @ApiParam( value = "父菜单ID", required = false) @RequestParam(required = false, defaultValue = "0") Integer parentId,
            @ApiParam( value = "菜单状态:true/false(不传或传\"\"查询全部)", required = false) @RequestParam(required = false, defaultValue = "") Boolean status,
            @ApiParam( value = "需要排除的ID", required = false) @RequestParam(required = false, defaultValue = "0") Integer excludeId
    ){
        //获取当前登陆人员
        return menuService.getMenuTreeList(parentId, status, excludeId);
    }

    @PostMapping("/check/url")
    @ApiOperation(value = "检测菜单URL", httpMethod = "POST", produces = "application/json")
    public Result checkURL(
            @ApiParam( value = "需要排除的id", required = false) @RequestParam(required = false) Integer id,
            @ApiParam( value = "URL", required = true) @RequestParam(required = true) String url
    ){
        //获取当前登陆人员
        return menuService.checkURL(id, url);
    }


    @PostMapping("/check/name")
    @ApiOperation(value = "检测菜单名(同级不能重复)", httpMethod = "POST", produces = "application/json")
    public Result checkName(
            @ApiParam( value = "需要排除的id", required = false) @RequestParam(required = false) Integer id,
            @ApiParam( value = "父菜单ID", required = true) @RequestParam(required = true) Integer parentId,
            @ApiParam( value = "菜单名", required = true) @RequestParam(required = true) String name

    ){
        //获取当前登陆人员
        return menuService.checkName(id, parentId, name);
    }


    @PostMapping("/update/status")
    @ApiOperation(value = "启用/禁用菜单", httpMethod = "POST", produces = "application/json")
    @RequiresPermissions("/system/menu/update/status")
    public Result updateMenuStatus(
            @ApiParam( value = "菜单ID", required = true) @RequestParam(required = true) Integer id,
            @ApiParam( value = "状态", required = true) @RequestParam(required = true) boolean status,
            @ApiParam(value = "updateTime", required = true) @RequestParam(required = true) Long updateTime
    ){
        //获取当前登陆人员
        MenuBO menu = new MenuBO();
        menu.setId(id);
        menu.setStatus(status);
        menu.setUpdateTime(updateTime == null ? null : new Date(updateTime));
        return menuService.saveORUpdateMenu(menu, true);
    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除菜单", httpMethod = "POST", produces = "application/json")
    @RequiresPermissions("/system/menu/delete")
    public Result deleteMenu(
            @ApiParam( value = "菜单ID", required = true) @RequestParam(required = true) Integer id
    ){
        //获取当前登陆人员
        return menuService.deleteMenu(id);
    }

    @PostMapping("/account/authority")
    @ApiOperation(value = "加载用户菜单权限", httpMethod = "POST", produces = "application/json")
    public Result getAccountMenuAuthority(){
        return menuService.getAccountMenuAuthority();
    }

    @PostMapping("/permission/tree")
    @ApiOperation(value = "加载菜单权限树", httpMethod = "POST", produces = "application/json")
    public Result getMenuPermissionTree(){
        return menuService.getMenuPermissionTree();
    }

    @PostMapping("/account/menu/permission")
    @ApiOperation(value = "加载页面按钮权限", httpMethod = "POST", produces = "application/json")
    public Result getAccountMenuPermission(String url){
        return menuService.getAccountMenuPermission(url);
    }


}
