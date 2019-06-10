package com.cheng.manage.dao.system.dictionary;

import com.cheng.manage.common.mapper.BaseMapper;
import com.cheng.manage.entity.system.dictionary.DictionaryBO;
import com.cheng.manage.entity.system.dictionary.Select;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
@Repository
public interface DictionaryMapper extends BaseMapper<DictionaryBO> {
    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/11 0:31
     * 描述 : 根据Key查询总数
     *
     * @param key       key
     * @param excludeId 需要排除的I的
     * @return
     */
    int countByKey(@Param("key")String key, @Param("excludeId")Integer excludeId);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/11 0:44
     * 描述 : 根据value查询总数
     *
     * @param value     value
     * @param parentId  父节点ID
     * @param excludeId 需要排除的ID
     * @return
     */
    int countByValue(@Param("value")String value, @Param("parentId")Integer parentId, @Param("excludeId")Integer excludeId);


    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/12 0:39
     * 描述 : 根据名称查询总数
     *
     * @param name      名称
     * @param parentId  父节点ID
     * @param excludeId 需要排除的ID
     * @return
     */
    int countByName(@Param("name")String name, @Param("parentId")Integer parentId, @Param("excludeId")Integer excludeId);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/16 1:06
     * 描述 : 根据父节点查询总数
     *
     * @param parentId
     * @return
     */
    int countByParentId(@Param("parentId")Integer parentId);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/11 1:12
     * 描述 : 根据ID获取名称
     *
     * @param id id
     * @return
     */
    String getNameByID(@Param("id")Integer id);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/11 22:21
     * 描述 : 通过数据字典父节点ID查询数据字典列表
     * @param parentId
     * @return
     */
    List<DictionaryBO> getDictionaryListByParentId(Integer parentId);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/16 23:31
     * 描述 : 通过数据字典固定编码查询value
     *
     * @param code
     * @return
     */
    String getValueByCode(@Param("code")String code);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/17 0:08
     * 描述 : 通过固定编码查询Id
     *
     * @param code
     * @return
     */
    Integer getIDByCode(@Param("code")String code);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/17 0:04
     * 描述 : 查询数据字典下拉列表
     * @param parentId 父节点ID
     * @return
     */
    List<Select> getSelectList(Integer parentId);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/4/23 1:22
     * 描述 : 通过id查询修改时间
     * @param id
     * @return
     */
    Date getUpdateTimeById(@Param("id") Integer id);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/5/24 16:39
     * 描述 : 根据数据字典固定编码查询数据字典
     * @param code 固定编码
     * @return
     */
    DictionaryBO getDictionaryByKey(@Param("code") String code);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/5/24 16:45
     * 描述 : 根据数据字典id查询数据字典
     * @param id id
     * @return
     */
    DictionaryBO getDictionaryById(@Param("id") Integer id);

    /**
     * 作者: cheng fei
     * 创建日期: 2019/6/4 0:14
     * 描述 : 根据固定编码查询名称
     * @param code
     * @return
     */
    String getNameByCode(@Param("code") String code);
}
