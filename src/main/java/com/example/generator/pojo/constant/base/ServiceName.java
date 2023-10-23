package com.example.generator.pojo.constant.base;

/**
 * FeignClient 使用
 *
 * @author Administrator
 *
 * @FeignClient(name = ServiceName.SHARE_SERVICE,
 * contextId = ServiceName.SHARE_SERVICE + ServiceName.DELIMITER + "IAccountApi")
 */
public class ServiceName {

    /**
     * 统一认证授权服务
     **/
    public static final String SHARE_SERVICE = "jdd-share-service";
    /**
     * 文件服务
     **/
    public static final String FILE_SERVICE = "file-service";
    /**
     * 服务拼接
     */
    public static final String DELIMITER = "-";
}
