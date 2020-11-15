package com.wangmeng.mall.oss;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @ClassName: CloudStorageConfig
 * @Description:  云存储配置信息
 * @author: <a href="edeis@163.com">edeis</a>
 * @version: V1.0.0
 * @date: 2017-8-1
 */
@Component
@ConfigurationProperties(prefix = "oss")
public class CloudStorageProperties implements Serializable {
    private static final long serialVersionUID = 1L;

    //类型 1：七牛  2：阿里云  3：腾讯云
    private Integer type;

    //绑定的域名
    private String domain;
    //路径前缀
    private String prefix;
    //ACCESS_KEY
    private String accessKey;
    //SECRET_KEY
    private String secretKey;
    //存储空间名
    private String bucketName;

    //阿里云EndPoint
    private String endPoint;

    //腾讯云AppId
    private Integer appId;
    //腾讯云COS所属地区
    private String region;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
