package com.sxnd.develop.framework.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * web请求参数
 * 
 * @author ningning.cheng
 * @date 2022/6/17
 **/
@ConfigurationProperties(prefix = "log.request")
public class RequestParamProperties {
    /**
     * 开启
     */
    private boolean enable = true;
    /**
     * 不拦截路径
     */
    private String[] noFilter = {"/swagger-resources", "/swagger-resources/**", "/v2/api-docs", "/v2/api-docs-ext",
        "/error", "/doc.html", "/webjars/**", "/ping", "/api/prometheus"};

    public boolean isEnable() {
        return this.enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String[] getNoFilter() {
        return this.noFilter;
    }

    public void setNoFilter(String[] noFilter) {
        this.noFilter = noFilter;
    }
}
