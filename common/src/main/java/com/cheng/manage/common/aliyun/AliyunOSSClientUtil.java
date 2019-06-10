package com.cheng.manage.common.aliyun;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.CopyObjectRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.Map;

/**
 * @Description : 阿里云OSS工具类
 * @Author : cheng fei
 * @Date : 2018/12/24 09:43
 */

@Component
public class AliyunOSSClientUtil {

    /**
     * log日志
     */
    private static Logger logger = LoggerFactory.getLogger(AliyunOSSClientUtil.class);

    /**
     * 获取地址为永久有效，默认为100年 100 * 365 * 24 * 3600 * 1000 = 3153600000000
     */
    private static final Long PERPETUAL = 3153600000000L;

    /**
     * 阿里云API的内网域名
     */
    @Value("${oss.intranet.endpoint}")
    private String intranetEndpoint;

    /**
     * 阿里云API的外网域名
     */
    @Value("${oss.outer.net.endpoint}")
    private String outerNetEndpoint;
    /**
     * 阿里云API的密钥Access Key ID
     */
    @Value("${oss.accessKeyId}")
    private String accessKeyID;
    /**
     * 阿里云API的密钥Access Key Secret
     */
    @Value("${oss.accessKeySecret}")
    private String accessKeySecret;
    /**
     * 阿里云API的bucket名称
     */
    @Value("${oss.bucket}")
    private String bucketName;

    /**
     * url有效时间
     */
    @Value("${oss.expiration}")
    private long expiration = 86400000L;

    /**
     * 当前环境是否能连接OSS内网
     */
    @Value("${oss.client.intranet.endpoint}")
    private boolean clientIntranetEndpoint = false;

    /**
     * 是否使用nginx反向代理
     */
    @Value("${oss.use.nginx}")
    private boolean useNginx = true;

    /**
     * nginx反向代理地址
     */
    @Value("${oss.nginx.url}")
    private String nginxUrl;

    /**
     * 阿里云外网连接
     */
    private OSSClient ossClientByOuterNet;

    /**
     * 阿里云内网连接
     */
    private OSSClient ossClientByIntranet;


    /**
     * 作者: cheng fei
     * 创建日期: 2018/12/24 17:18
     * 描述 : 获取阿里云OSS客户端连接对象
     *
     * @return
     */
    public OSSClient createOSSClient() {
        if (clientIntranetEndpoint) {
            if (this.ossClientByIntranet == null) {
                this.ossClientByIntranet = new OSSClient(intranetEndpoint, accessKeyID, accessKeySecret);
            }
            return this.ossClientByIntranet;
        } else {
            if (this.ossClientByOuterNet == null) {
                this.ossClientByOuterNet = new OSSClient(outerNetEndpoint, accessKeyID, accessKeySecret);
            }
            return this.ossClientByOuterNet;
        }
    }


    /**
     * 作者: cheng fei
     * 创建日期: 2019/1/10 15:46
     * 描述 : 获取阿里云OSS客户端外网连接对象
     *
     * @return
     */
    public OSSClient createOSSClientByOuterNet() {
        if (this.ossClientByOuterNet == null) {
            this.ossClientByOuterNet = new OSSClient(outerNetEndpoint, accessKeyID, accessKeySecret);
        }
        return this.ossClientByOuterNet;
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/1/10 15:46
     * 描述 : 获取阿里云OSS内网连接对象
     *
     * @return
     */
    public OSSClient createOSSClientByIntranet() {
        if (this.ossClientByIntranet == null) {
            this.ossClientByIntranet = new OSSClient(intranetEndpoint, accessKeyID, accessKeySecret);
        }
        return this.ossClientByIntranet;
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2018/12/24 17:43
     * 描述 : 关闭OSS连接
     */
    public void closeOSSClient() {

        if (this.ossClientByOuterNet != null) {
            this.ossClientByOuterNet.shutdown();
            this.ossClientByOuterNet = null;
        }

        if (this.ossClientByIntranet != null) {
            this.ossClientByIntranet.shutdown();
            this.ossClientByIntranet = null;
        }


    }


    /**
     * 作者: cheng fei
     * 创建日期: 2018/12/24 23:37
     * 描述 : 删除文件
     *
     * @param fileName 文件名
     */
    public void deleteFile(String fileName) {
        createOSSClient().deleteObject(bucketName, fileName);
        logger.info("删除" + bucketName + "下的文件" + fileName + "成功");
        closeOSSClient();
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2018/12/25 1:01
     * 描述 : 下载文件
     *
     * @param fileFullPath
     * @return
     */
    public InputStream downLoadFile(String fileFullPath) {
        //判断当前OSS连接网络环境
        if (clientIntranetEndpoint) {
            OSSObject ossObject = createOSSClient().getObject(bucketName, fileFullPath);
            return ossObject.getObjectContent();
        } else {
            OSSObject ossObject = createOSSClientByOuterNet().getObject(bucketName, fileFullPath);
            return ossObject.getObjectContent();
        }

    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/3/14 21:25
     * 描述 : 获取文件url
     *
     * @param fileName 文件名称
     * @return
     */
    public String getUrl(String fileName) {
        return getUrl(fileName,true);
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/3/14 21:25
     * 描述 : 获取文件url
     *
     * @param fileName 文件名称
     * @param isCloseClient 是否关闭OSS连接
     * @return
     */
    public String getUrl(String fileName, boolean isCloseClient) {
        return getUrl(fileName, isCloseClient, false);
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2018/12/24 21:30
     * 描述 : 获得url链接
     *
     * @param isPerpetual url地址是否用久生效
     * @param fileName    文件名称
     * @return
     */
    public String getUrl(boolean isPerpetual, String fileName) {
        return getUrl(fileName, true, false);
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2018/12/24 21:30
     * 描述 : 获得url链接
     *
     * @param fileName    文件名称
     * @param isCloseClient    是否关闭OSS连接
     * @param isPerpetual url地址是否用久生效
     * @return
     */
    public String getUrl(String fileName , boolean isCloseClient,  boolean isPerpetual) {
        // 设置URL过期时间
        Date expirationDate = new Date(System.currentTimeMillis() + (isPerpetual ? PERPETUAL : expiration));
        // 生成URL
        URL url = createOSSClientByIntranet().generatePresignedUrl(bucketName, fileName, expirationDate);
        if (isCloseClient) {
            closeOSSClient();
        }
        if (url != null) {
            String path = url.toString();
            if (useNginx) {
                path = nginxUrl + path.substring(path.indexOf("/", 8), path.length());
            }
            return path;
        }
        return "获网址路径出错";
    }


    /**
     * 作者: cheng fei
     * 创建日期: 2019/6/1 18:43
     * 描述 : 获取文件用户自定义元信息
     * @return
     */
    public  Map<String, String>  getMetaData(String fileName){
        OSSClient ossClient = createOSSClient();
        ObjectMetadata metadata = ossClient.getObjectMetadata(bucketName, fileName);
        Map<String, String> userMetadata = metadata.getUserMetadata();
        closeOSSClient();
        return userMetadata;
    }

    /**
     * 作者: cheng fei
     * 创建日期: 2019/6/1 18:48
     * 描述 : 修改文件用户自定义元信息
     * @param userMeta
     * @param fileName
     */
    public void  SetMetaData(Map<String, String> userMeta, String fileName){
        OSSClient ossClient = createOSSClient();
        ObjectMetadata meta = new ObjectMetadata();
        meta.setUserMetadata(userMeta);

        // 设置源文件与目标文件相同，调用ossClient.copyObject方法修改文件元信息。
        CopyObjectRequest request = new CopyObjectRequest(bucketName, fileName, bucketName, fileName);
        request.setNewObjectMetadata(meta);

        //修改元信息。
        ossClient.copyObject(request);

        closeOSSClient();
    }


    /**
     * 作者: cheng fei
     * 创建日期: 2019/6/1 18:43
     * 描述 : 获取文件用户自定义元信息,然后删除
     * @return
     */
    public  Map<String, String>  getMetaDataAndDelete(String fileName){
        OSSClient ossClient = createOSSClient();
        ObjectMetadata metadata = ossClient.getObjectMetadata(bucketName, fileName);
        Map<String, String> userMetadata = metadata.getUserMetadata();

        ObjectMetadata meta = new ObjectMetadata();
        meta.setUserMetadata(null);

        // 设置源文件与目标文件相同，调用ossClient.copyObject方法修改文件元信息。
        CopyObjectRequest request = new CopyObjectRequest(bucketName, fileName, bucketName, fileName);
        request.setNewObjectMetadata(meta);

        //修改元信息。
        ossClient.copyObject(request);

        closeOSSClient();
        return userMetadata;
    }

}

