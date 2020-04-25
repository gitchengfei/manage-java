package com.cheng.manage.service.file.impl;

import com.cheng.manage.common.exception.MyException;
import com.cheng.manage.common.result.Result;
import com.cheng.manage.common.result.ResultEnum;
import com.cheng.manage.constant.app.file.FileConstant;
import com.cheng.manage.dao.system.dictionary.DictionaryMapper;
import com.cheng.manage.entity.file.FileBO;
import com.cheng.manage.entity.file.FileDTO;
import com.cheng.manage.entity.file.UseFileBO;
import com.cheng.manage.service.base.BaseService;
import com.cheng.manage.service.file.FileService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: cheng fei
 * @Date: 2019/6/1 18:54
 * @Description:
 */
@Service
public class FileServiceImpl extends BaseService implements FileService {

    /**
     * 数据库操作日志类型
     */
    private static final String DB_LOG_TYPE = "SYSTEM_DB_LOG_TYPE_FILE";

    @Autowired
    private DictionaryMapper dictionaryMapper;




    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result saveFile(String uploadFileName) {

        logger.debug("保存文件:uploadFileName=【{}】", uploadFileName);

        Integer loginAccountId = getLoginAccountId();
        Date date = new Date();

        Map<String, String> metaData = aliyunOSSClientUtil.getMetaData(uploadFileName);

        //添加到文件表
        FileBO fileBO = new FileBO();
        try {
            String fileName = metaData.get(FileConstant.FILE_NAME);
            if (StringUtils.isNotBlank(fileName)) {
                fileName = URLDecoder.decode(fileName);
            }
            fileBO.setFileName(fileName);
        } catch (Exception e) {
            throw new MyException(ResultEnum.FILE_URL_DECODER_ERROR);
        }
        fileBO.setPath(metaData.get(FileConstant.PATH));
        fileBO.setFileExtension(metaData.get(FileConstant.FILE_EXTENSION));
        fileBO.setFileType(metaData.get(FileConstant.FILE_TYPE));
        fileBO.setType(metaData.get(FileConstant.type));
        fileBO.setFilePurpose(metaData.get(FileConstant.FILE_PURPOSE));
        fileBO.setSize(metaData.get(FileConstant.SIZE));
        fileBO.setTime(metaData.get(FileConstant.TIME));
        fileBO.setDelete(false);
        fileBO.setCreateId(loginAccountId);
        fileBO.setCreateTime(date);

        int i = fileMapper.insertSelective(fileBO);
        checkDbInsert(i);

        //添加到未使用文件表
        addUnusedFile(fileBO.getId(), date);

        saveDBLog(loginAccountId, getDBLog(fileBO), DB_LOG_TYPE);

        fileBO.setPath(getFilePath(fileBO.getPath()));

        return Result.succee(fileBO);
    }

    @Override
    public Result getFileList(String fileName, String type, String filePurpose, String startTime, String endTime, Integer page, Integer pageSize) {

        logger.debug("查询文件列表:fileName=【{}】，dictionaryCode=【{}】, startTime=【{}】, endTime=【{}】, page=【{}】, pageSize=【{}】");

        type = StringUtils.isBlank(type) ? null : type;

        filePurpose = StringUtils.isBlank(filePurpose) ? null : filePurpose;

        fileName = checkSearchString(fileName);

        startTime = checkCompareDate(startTime);

        endTime = checkCompareDate(endTime);

        PageHelper.startPage(page, pageSize);

        List<FileBO> list = fileMapper.getFileList(fileName, type, filePurpose, startTime, endTime);

        PageInfo<FileBO> info = new PageInfo<>(list);

        List<FileDTO> rows = new ArrayList<>();

        for (FileBO fileBO : info.getList()) {
            FileDTO fileDTO = new FileDTO(fileBO);
            if (FileConstant.FILE_TYPE_IMAGE.equals(fileBO.getType())) {
                fileDTO.setPath(getFilePath(fileBO.getPath(), false));
            }
            fileDTO.setUsed(checkFileUse(fileBO.getId()));
            fileDTO.setTypeName(dictionaryMapper.getNameByCode(fileBO.getType()));
            fileDTO.setFilePurpose(dictionaryMapper.getNameByCode(fileBO.getFilePurpose()));
            fileDTO.setCreateName(fileBO.getCreateId() == null ? "" : accountMapper.getNameById(fileBO.getCreateId()));
            fileDTO.setUpdateName(fileBO.getUpdateId() == null ? "" : accountMapper.getNameById(fileBO.getUpdateId()));

            rows.add(fileDTO);
        }
        aliyunOSSClientUtil.closeOSSClient();

        return getResult(rows, info.getTotal());
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/6/4 18:18
     * 描述 : 检测图片是否已使用
     * @param id
     * @return
     */
    private Boolean checkFileUse(Integer id) {
        int count = unusedFileMapper.countByFileId(id);
        return count <= 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result updateFileName(FileBO fileBO) {

        logger.debug("修改文件： file=【 {} 】", fileBO);

        //检测参数
        if (fileBO.getId() == null || StringUtils.isBlank(fileBO.getFileName())) {
            return Result.build(ResultEnum.PARAM_ERROR);
        }
        FileBO oldFile = fileMapper.selectByPrimaryKey(fileBO.getId());
        checkUpdateTime(fileBO.getUpdateTime(), oldFile.getUpdateTime());

        Integer loginAccountId = getLoginAccountId();

        fileBO.setUpdateTime(new Date());
        fileBO.setUpdateId(loginAccountId);

        int i = fileMapper.updateByPrimaryKeySelective(fileBO);
        checkDbUpdate(i);

        StringBuffer log = new StringBuffer();

        log.append("修改文件【 ").append(oldFile.getFileName()).append(" 】的名称为【 ").append(fileBO.getFileName()).append(" 】");

        saveDBLog(loginAccountId, log.toString(), DB_LOG_TYPE);

        return Result.succee();
    }

    @Override
    public FileBO downloadFile(Integer fileId) {
        return fileMapper.selectByPrimaryKey(fileId);
    }

    @Override
    public Result deleteFile(String fileIds) {

        String[] idArrayStr = fileIds.split(",");
        for (String idStr : idArrayStr) {
            if (!StringUtils.isNumeric(idStr)) {
                throw new MyException(ResultEnum.PARAM_ERROR);
            }
            int id = Integer.parseInt(idStr);
            //修改文件为删除状态
            int i = fileMapper.updateDeleteById(id);
            checkDbUpdate(i);

            //删除已使用文件表
            useFileMapper.deleteByFileId(id);

            // 将文件加入未使用记录,等待系统自动删除,避免删除发生异常,事务回滚后,文件已删除
            addUnusedFile(id, null);
        }
        return Result.succee();
    }

    @Override
    public Result getFileUseInfo(Integer fileId) {
        logger.debug("获取文件使用详情：fileId=【 {} 】", fileId);

        List<UseFileBO> list = useFileMapper.getUseFileByFileId(fileId);
        return Result.succee(list);
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/6/1 20:57
     * 描述 : 获取数据库操作日志
     * @param fileBO 文件
     * @return
     */
    private String getDBLog(FileBO fileBO){
        StringBuffer log = new StringBuffer();
        log.append("上传了文件").append("【 ").append(fileBO.getFileName()).append(" 】，")
                .append("文件ID为【 ").append(fileBO.getId()).append(" 】");
        return log.toString();
    }
}
