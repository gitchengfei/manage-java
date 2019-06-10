package com.cheng.manage.controller.system.dictionary;

import com.cheng.manage.common.result.Result;
import com.cheng.manage.controller.system.base.SystemBaseController;
import com.cheng.manage.entity.account.account.AccountBO;
import com.cheng.manage.entity.system.dictionary.DictionaryBO;
import com.cheng.manage.service.system.dictionary.DictionaryService;
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
 * @Description : 数据字典Controller
 * @Author : cheng fei
 * @Date : 2019/4/11 00:02
 */
@RestController
@RequestMapping("/system/dictionary")
@Api(tags = "system.dictionary.DictionaryController", description = "系统-数据字典模块")
public class DictionaryController extends SystemBaseController {

    @Autowired
    private DictionaryService dictionaryService;

    @PostMapping("/save")
    @ApiOperation(value = "添加数据字典", httpMethod = "POST", produces = "application/json")
    @RequiresPermissions("/system/dictionary/save")
    public Result saveDictionary(
            @ApiParam(value = "名称", required = true) @RequestParam(required = true) String name,
            @ApiParam(value = "key", required = true) @RequestParam(required = true) String key,
            @ApiParam(value = "value", required = false) @RequestParam(required = false) String value,
            @ApiParam(value = "排序码", required = true) @RequestParam(required = true) Integer displayOrder,
            @ApiParam(value = "父节点", required = false) @RequestParam(required = false, defaultValue = "0") Integer parentId
    ) {
        DictionaryBO dictionary = new DictionaryBO();
        dictionary.setName(name);
        dictionary.setKey(key);
        dictionary.setValue(value);
        dictionary.setDisplayOrder(displayOrder);
        dictionary.setParentId(parentId);

        return dictionaryService.saveOrUpdateDictionary(dictionary, false);
    }


    @PostMapping("/list")
    @ApiOperation(value = "查询数据字典列表", httpMethod = "POST", produces = "application/json")
    @RequiresPermissions("/system/dictionary/list")
    public Result getDictionaryList(
            @ApiParam(value = "父节点", required = false) @RequestParam(required = false, defaultValue = "0") Integer parentId,
            @ApiParam(value = "页码", required = false) @RequestParam(required = false, defaultValue = "1") Integer page,
            @ApiParam(value = "每页加载条数", required = false) @RequestParam(required = false, defaultValue = "10") Integer pageSize
    ) {
        return dictionaryService.getDictionaryList(parentId, page, pageSize);
    }

    @PostMapping("/update/status")
    @ApiOperation(value = "修改数据字典状态", httpMethod = "POST", produces = "application/json")
    @RequiresPermissions("/system/dictionary/update/status")
    public Result updateDictionaryStatus(
            @ApiParam(value = "id", required = true) @RequestParam(required = true) Integer id,
            @ApiParam(value = "状态:true/false", required = true) @RequestParam(required = true) Boolean status,
            @ApiParam(value = "上次修改时间", required = true) @RequestParam(required = true) Long updateTime
    ) {
        DictionaryBO dictionary = new DictionaryBO();
        dictionary.setId(id);
        dictionary.setStatus(status);
        dictionary.setUpdateTime(updateTime == null ? null : new Date(updateTime));
        return dictionaryService.saveOrUpdateDictionary(dictionary, true);
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改数据字典", httpMethod = "POST", produces = "application/json")
    @RequiresPermissions("/system/dictionary/update")
    public Result updateDictionary(
            @ApiParam(value = "id", required = true) @RequestParam(required = true) Integer id,
            @ApiParam(value = "名称", required = true) @RequestParam(required = true) String name,
            @ApiParam(value = "key", required = true) @RequestParam(required = true) String key,
            @ApiParam(value = "value", required = false) @RequestParam(required = false) String value,
            @ApiParam(value = "排序码", required = true) @RequestParam(required = true) Integer displayOrder,
            @ApiParam(value = "上次修改时间", required = true) @RequestParam(required = true) Long updateTime
    ) {
        DictionaryBO dictionary = new DictionaryBO();
        dictionary.setId(id);
        dictionary.setName(name);
        dictionary.setKey(key);
        dictionary.setValue(value);
        dictionary.setDisplayOrder(displayOrder);
        dictionary.setUpdateTime(updateTime == null ? null : new Date(updateTime));
        return dictionaryService.saveOrUpdateDictionary(dictionary, false);
    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除数据字典", httpMethod = "POST", produces = "application/json")
    @RequiresPermissions("/system/dictionary/delete")
    public Result deleteDictionary(
            @ApiParam(value = "id", required = true) @RequestParam(required = true) Integer id
    ) {
        return dictionaryService.deleteDictionary(id);
    }

    @PostMapping("/check/name")
    @ApiOperation(value = "检测数据字典名称", httpMethod = "POST", produces = "application/json")
    public Result checkName(
            @ApiParam(value = "id", required = true) @RequestParam(required = true) Integer id,
            @ApiParam(value = "父节点ID", required = true) @RequestParam(required = true) Integer parentId,
            @ApiParam(value = "名称", required = true) @RequestParam(required = true) String name
    ) {
        return dictionaryService.checkName(id, parentId, name);
    }

    @PostMapping("/check/key")
    @ApiOperation(value = "检测数据字典key", httpMethod = "POST", produces = "application/json")
    public Result checkKey(
            @ApiParam(value = "id", required = true) @RequestParam(required = true) Integer id,
            @ApiParam(value = "key", required = true) @RequestParam(required = true) String key
    ) {
        return dictionaryService.checkKey(id, key);
    }

    @PostMapping("/check/value")
    @ApiOperation(value = "检测数据字典value", httpMethod = "POST", produces = "application/json")
    public Result checkValue(
            @ApiParam(value = "id", required = true) @RequestParam(required = true) Integer id,
            @ApiParam(value = "父节点ID", required = true) @RequestParam(required = true) Integer parentId,
            @ApiParam(value = "value", required = true) @RequestParam(required = true) String value
    ) {
        return dictionaryService.checkValue(id, parentId, value);
    }

    @PostMapping("/select")
    @ApiOperation(value = "获取数据字典下拉列表", httpMethod = "POST", produces = "application/json")
    public Result getSelectList(
            @ApiParam(value = "父节点Key", required = true) @RequestParam(required = true) String parentKey
    ) {
        return dictionaryService.getSelectList(parentKey);
    }

}
