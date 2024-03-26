package com.sxnd.develop.framework.annotation;


import org.apache.commons.lang3.StringUtils;

/**
 * 登录用户的实现
 * 
 * @author ningning.cheng
 * @since 2022/11/23
 **/
public interface LoginUserConfigurer {
    /**
     * 获得当前登录用户（中文）
     * 
     * @return
     */
    String getCurrentName();

    /**
     * 获得当前登录用户（英文）
     *
     * @return
     */
    default String getCurrentUserName() {
        return StringUtils.EMPTY;
    }
}
