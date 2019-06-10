package com.cheng.manage.service.system.dictionary.impl;

import com.cheng.manage.common.exception.MyException;
import com.cheng.manage.common.result.Result;
import com.cheng.manage.common.result.ResultEnum;
import com.cheng.manage.constant.app.ApplicationConstant;
import com.cheng.manage.dao.account.account.AccountMapper;
import com.cheng.manage.dao.system.dictionary.DictionaryMapper;
import com.cheng.manage.entity.account.account.AccountBO;
import com.cheng.manage.entity.system.dictionary.DictionaryBO;
import com.cheng.manage.entity.system.dictionary.DictionaryDTO;
import com.cheng.manage.service.system.base.SystemBaseService;
import com.cheng.manage.service.system.dictionary.DictionaryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Description : 数据字典接口实现类
 * @Author : cheng fei
 * @Date : 2019/4/11 00:10
 */
@Service
public class DictionaryServiceImpl extends SystemBaseService implements DictionaryService {

    /**
     * 数据库操作日志类型
     */
    private static final String DB_LOG_TYPE = "SYSTEM_DB_LOG_TYPE_DICTIONARY";

    /**
     * 日志
     */
    Logger logger = LoggerFactory.getLogger(DictionaryServiceImpl.class);

    @Autowired
    private DictionaryMapper dictionaryMapper;

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public Result saveOrUpdateDictionary(DictionaryBO dictionary, boolean isUpdateStatus) throws MyException {
        Integer loginAccountId = getLoginAccountId();
        logger.debug("添加/修数据字典:【 {} 】", dictionary);
        DictionaryBO oldDictionary = null;
        if (dictionary.getId() != null) {
            oldDictionary = dictionaryMapper.selectByPrimaryKey(dictionary.getId());
        }

        //检测参数
        checkDictionary(dictionary, oldDictionary, isUpdateStatus);

        if (dictionary.getId() == null) {
            dictionary.setStatus(true);
            dictionary.setCreateId(loginAccountId);
            dictionary.setCreateTime(new Date());

            int i = dictionaryMapper.insertSelective(dictionary);
            checkDbInsert(i);
            //数据操作日志
            saveDBLog(loginAccountId, getDictionaryLog(dictionary), DB_LOG_TYPE);
        } else {
            dictionary.setUpdateId(loginAccountId);
            dictionary.setUpdateTime(new Date());

            int i = dictionaryMapper.updateByPrimaryKeySelective(dictionary);
            checkDbInsert(i);

            saveDBLog(loginAccountId, getDictionaryLog(dictionary, oldDictionary), DB_LOG_TYPE);
        }

        return Result.succee();
    }

    @Override
    public Result getDictionaryList(Integer parentId, Integer page, Integer pageSize) {

        logger.debug("查询数据字典列表:【 {} 】", parentId);
        PageHelper.startPage(page, pageSize);
        List<DictionaryBO> dictionaries = dictionaryMapper.getDictionaryListByParentId(parentId);

        PageInfo<DictionaryBO> info = new PageInfo<>(dictionaries);

        List<DictionaryBO> list = info.getList();

        List<DictionaryDTO> rows = new ArrayList<>();
        for (DictionaryBO dictionary : list) {
            DictionaryDTO dictionaryDTO = new DictionaryDTO(dictionary);
            dictionaryDTO.setCreateName(accountMapper.getNameById(dictionary.getCreateId()));
            dictionaryDTO.setUpdateName(accountMapper.getNameById(dictionary.getUpdateId()));
            rows.add(dictionaryDTO);
        }

        return getResult(rows, info.getTotal());
    }

    @Override
    public Result deleteDictionary(Integer id) {

        logger.debug("删除数据字典, id=【 {} 】", id);

        //判断是否存在子数据
        int count = dictionaryMapper.countByParentId(id);
        if (count > 0) {
            throw new MyException(ResultEnum.DICTIONARY_DELETE_EXIST_CHILDREN);
        }

        String name = dictionaryMapper.getNameByID(id);
        int i = dictionaryMapper.deleteByPrimaryKey(id);
       checkDbDelete(i);

        saveDBLog(getLoginAccountId(), "删除了数据字典【 " + name + " 】", DB_LOG_TYPE);

        return Result.succee();
    }

    @Override
    public Result checkName(Integer id, Integer parentId, String name) {
        return Result.succee(checkName(name, parentId, id));
    }

    @Override
    public Result checkKey(Integer id, String key) {
        return Result.succee(checkKey(key, id));
    }

    @Override
    public Result checkValue(Integer id, Integer parentId, String value) {
        return Result.succee(checkValue(value, parentId, id));
    }

    @Override
    public Result getSelectList(String parentKey) {

        //检测参数
        if (StringUtils.isBlank(parentKey)){
            return Result.build(ResultEnum.PARAM_ERROR);
        }

        if (!checkParentStatus(dictionaryMapper.getDictionaryByKey(parentKey))){
            return Result.succee();
        }

        return Result.succee( dictionaryMapper.getSelectList(dictionaryMapper.getIDByCode(parentKey)));
    }


    /**
     * 作者: cheng fei
     * 创建日期: 2019/5/24 16:38
     * 描述 : 检测所有父节点状态
     * @param parentDictionary 父节点
     * @return
     */
    private boolean checkParentStatus(DictionaryBO parentDictionary) {
        if (!parentDictionary.getStatus()){
            return false;
        }
        if (parentDictionary.getParentId() == 0 || parentDictionary.getParentId() == null) {
            return true;
        }
        return checkParentStatus(dictionaryMapper.getDictionaryById(parentDictionary.getParentId()));
    }


    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/11 1:15
     * 描述 : 获取数据字典日志
     *
     * @param dictionary
     * @param oldDictionary
     * @return
     */
    private String getDictionaryLog(DictionaryBO dictionary, DictionaryBO oldDictionary) {

        StringBuffer log = new StringBuffer();

        //名称
        if (!equalsString(dictionary.getName(), oldDictionary.getName())) {
            log.append("名称：").append(oldDictionary.getName()).append("==>").append(dictionary.getName()).append("， ");
        }

        //key
        if (!equalsString(dictionary.getKey(), oldDictionary.getKey())) {
            log.append("Key：").append(oldDictionary.getKey()).append("==>").append(dictionary.getKey()).append("， ");
        }

        //value
        if (!equalsString(dictionary.getValue(), oldDictionary.getValue())) {
            log.append("value：").append(oldDictionary.getValue()).append("==>").append(dictionary.getValue()).append("， ");
        }

        //排序码
        if (!equalsInteger(dictionary.getDisplayOrder(), oldDictionary.getDisplayOrder())) {
            log.append("排序码：").append(dictionary.getDisplayOrder()).append("==>").append(oldDictionary.getDisplayOrder()).append("， ");
        }

        //状态
        if (dictionary.getStatus() != null && !equalsBoolean(dictionary.getStatus(), oldDictionary.getStatus())) {
            log.append("状态：").append(dictionary.getStatus() ? "启用" : "禁用").append("==>").append(oldDictionary.getStatus() ? "启用" : "禁用").append("， ");
        }

        String logStr = log.toString();
        if (StringUtils.isNotBlank(logStr)) {
            StringBuffer data = new StringBuffer();
            data.append("修改了数据字典【 ").append(oldDictionary.getName()).append(" 】==>【 ").append(logStr.substring(0, logStr.lastIndexOf("，"))).append(" 】");
            return data.toString();
        }
        return null;
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/11 1:05
     * 描述 : 获取数据字典日志
     *
     * @param dictionary
     * @return
     */
    private String getDictionaryLog(DictionaryBO dictionary) {
        StringBuffer log = new StringBuffer();
        log.append("添加了数据字典【 名称=").append(dictionary.getName()).append("，")
                .append("key=").append(dictionary.getKey()).append("，")
                .append("value=").append(dictionary.getValue() == null ? "" : dictionary.getValue()).append("，")
                .append("排序码=").append(dictionary.getDisplayOrder()).append("，")
                .append("父节点=").append(dictionary.getParentId() == 0 ? "顶级节点" : dictionaryMapper.getNameByID(dictionary.getParentId())).append(" 】");
        return log.toString();
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/11 0:22
     * 描述 : 检测数据字典参数
     *
     * @param dictionary
     * @param oldDictionary
     * @param isUpdateStatus 是否是修改状态
     */
    private void checkDictionary(DictionaryBO dictionary, DictionaryBO oldDictionary, boolean isUpdateStatus) {

        //id
        if (oldDictionary != null && dictionary.getId() == null){
            throw new MyException(ResultEnum.DICTIONARY_ID_IS_NULL);
        }

        //名称
        if (StringUtils.isBlank(dictionary.getName()) && ! isUpdateStatus) {
            throw new MyException(ResultEnum.DICTIONARY_NAME_IS_NULL);
        } else {
            if (!checkName(dictionary.getName(), oldDictionary == null ? dictionary.getParentId() : oldDictionary.getParentId(), oldDictionary == null ? null : oldDictionary.getId())) {
                throw new MyException(ResultEnum.DICTIONARY_NAME_IS_EXIST);
            }
        }

        //key
        if (StringUtils.isBlank(dictionary.getKey()) && ! isUpdateStatus) {
            throw new MyException(ResultEnum.DICTIONARY_KEY_IS_NULL);
        } else {
            if (!checkKey(dictionary.getKey(), oldDictionary == null ? null : oldDictionary.getId())) {
                throw new MyException(ResultEnum.DICTIONARY_KEY_IS_EXIST);
            }
        }

        //value
        if (StringUtils.isNotBlank(dictionary.getValue()) && ! isUpdateStatus) {
            if (!checkValue(dictionary.getValue(), dictionary.getParentId(), oldDictionary == null ? null : oldDictionary.getId())) {
                throw new MyException(ResultEnum.DICTIONARY_VALUE_IS_EXIST);
            }
        }

        //排序码
        if (dictionary.getDisplayOrder() == null && ! isUpdateStatus) {
            throw new MyException(ResultEnum.DICTIONARY_DISPLAY_ORDER_IS_NULL);
        }

        //父节点ID
        if (dictionary.getParentId() == null && oldDictionary == null && !isUpdateStatus) {
            throw new MyException(ResultEnum.DICTIONARY_PARENT_ID_IS_NULL);
        }

        //更新时间
        if (dictionary.getId() != null && oldDictionary != null) {
            checkUpdateTime(dictionary.getUpdateTime(), oldDictionary.getUpdateTime());
        }

    }


    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/11 0:41
     * 描述 : 检测数据字典名称
     *
     * @param name     名称
     * @param parentId  父节点Id
     * @param excludeId 需要排除的Id
     * @return
     */
    private boolean checkName(String name, Integer parentId, Integer excludeId) {
        int count = dictionaryMapper.countByName(name, parentId, excludeId);
        return count == 0;
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/11 0:41
     * 描述 : 检测数据字典Value
     *
     * @param value     value
     * @param parentId  父节点Id
     * @param excludeId 需要排除的Id
     * @return
     */
    private boolean checkValue(String value, Integer parentId, Integer excludeId) {
        int count = dictionaryMapper.countByValue(value, parentId, excludeId);
        return count == 0;
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/11 0:35
     * 描述 : 检测数据字典key是否可用
     *
     * @param key       key
     * @param excludeId 需要排除的Id
     * @return
     */
    private boolean checkKey(String key, Integer excludeId) {
        int count = dictionaryMapper.countByKey(key, excludeId);
        return count == 0;
    }

}
