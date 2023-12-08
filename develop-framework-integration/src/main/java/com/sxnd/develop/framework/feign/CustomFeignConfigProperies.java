package com.sxnd.develop.framework.feign;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author GW00305020
 * @ClassName CustomLoadBanlancerConfigProperies
 * @description: TODO
 * @date 2023年11月14日
 * @version: 1.0
 */
@ConfigurationProperties("custom.feignconfig")
@Data
public class CustomFeignConfigProperies {


    private String isOpen;
    /**
     * NONE, 不记录（默认）。
     *
     * BASIC，仅记录请求方法和URL以及响应状态码和执行时间。
     *
     * HEADERS，记录基本信息以及请求和响应标头。
     *
     * FULL，记录请求和响应的标头、正文和元数据。
     * @return
     */
    private String loggerFormat;

    /**
     * feign日志级别
     */
    private String loggerLevel;


}
