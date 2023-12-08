package com.sxnd.develop.framework.feign;

import feign.Logger;
import lombok.extern.slf4j.Slf4j;

/**
 * (一句话描述该类的功能)
 *
 * @version v1.0
 * @Author: xiaozhi
 * @Date: 2023/12/7 22:34
 */
@Slf4j
public class CustomFeignLogger extends Logger {

    private FeignLoggerLevelEnum feignLoggerLevel;

    public CustomFeignLogger(FeignLoggerLevelEnum feignLoggerLevel) {
        this.feignLoggerLevel = feignLoggerLevel;
    }


    @Override
    protected void log(String configKey, String format, Object... args) {
        if(feignLoggerLevel.equals(FeignLoggerLevelEnum.INFO)){
            log.info(String.format(methodTag(configKey) + format, args));
        }else{
            log.debug(String.format(methodTag(configKey) + format, args));
        }

    }
    /**
     * feign日志级别
     *
     * @version v1.0
     * @Author: xiaozhi
     * @Date: 2023/12/7 23:04
     */
    public enum FeignLoggerLevelEnum {
        INFO,
        DEBUG;
    }
}
