package com.cheng.manage.service.system.dictionary;

import com.cheng.manage.common.exception.MyException;
import com.cheng.manage.common.result.Result;
import com.cheng.manage.entity.account.account.AccountBO;
import com.cheng.manage.entity.system.dictionary.DictionaryBO;

/**
 * @Description : 数据字典接口
 * @Author : cheng fei
 * @Date : 2019/4/11 00:10
 */
public interface DictionaryService {

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/11 0:19
     * 创建日期: 2019/4/11 0:19
     * 描述 : 添加或者修改数据字典
     * @param dictionary 数据字典
     * @param isUpdateStatus 是否是修改状态
     * @return
     * @throws MyException
     */
    Result saveOrUpdateDictionary(DictionaryBO dictionary, boolean isUpdateStatus) throws MyException;

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/11 22:20
     * 描述 : 查询数据字典列表
     * @param parentId 父节点ID
     * @param page y页码
     * @param pageSize 每页加载条数
     * @return
     */
    Result getDictionaryList(Integer parentId, Integer page, Integer pageSize);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/11 22:41
     * 描述 : 删除数据字典

     * @param id
     * @return
     */
    Result deleteDictionary(Integer id);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/12 0:36
     * 描述 : 检测数据字典名称
     * @param id
     * @param parentId
     * @param name
     * @return
     */
    Result checkName(Integer id, Integer parentId, String name);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/12 0:45
     * 描述 : 检测数据字典key
     * @param id
     * @param key
     * @return
     */
    Result checkKey(Integer id, String key);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/12 0:48
     * 描述 : 检测数据字典value

     * @param id
     * @param parentId
     * @param value
     * @return
     */
    Result checkValue(Integer id, Integer parentId, String value);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/17 0:02
     * 描述 : 获取数据字典下拉列表
     * @param parentKey 父节点key
     * @return
     */
    Result getSelectList(String parentKey);
}
