package com.sxnd.develop.framework.feign;

import feign.Logger;
import feign.slf4j.Slf4jLogger;
import org.slf4j.LoggerFactory;

/**
 * (一句话描述该类的功能)
 *
 * @version v1.0
 * @Author: xiaozhi
 * @Date: 2023/12/7 22:34
 */
public class CustomFeignLogger extends Slf4jLogger {
    private final org.slf4j.Logger logger;
    private FeignLoggerLevelEnum feignLoggerLevel;

    public CustomFeignLogger(FeignLoggerLevelEnum feignLoggerLevel) {
        this(Logger.class);
        this.feignLoggerLevel = feignLoggerLevel;
    }

    public CustomFeignLogger(Class<?> clazz) {
        this(LoggerFactory.getLogger(clazz));
    }
    public CustomFeignLogger(String name) {
        this(LoggerFactory.getLogger(name));
    }

    CustomFeignLogger(org.slf4j.Logger logger) {
        this.logger = logger;
    }
    @Override
    protected void log(String configKey, String format, Object... args) {
        if(feignLoggerLevel.equals(FeignLoggerLevelEnum.INFO)){
            this.logger.info(String.format(methodTag(configKey) + format, args));
        }else{
            this.logger.debug(String.format(methodTag(configKey) + format, args));
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
