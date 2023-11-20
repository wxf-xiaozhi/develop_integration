package com.ifyou.skypivot.framework.utils;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import java.util.UUID;

/**
 * @Author xueyu
 * @Date 2022/7/21 17:15
 */
@Slf4j
public class GlobalTraceIdUtil {
    public static final String TRACE_ID = "traceId";


    public static String generate() {
        String traceId = UUID.randomUUID().toString().replaceAll("-", "");
        // log.info("put tranceID:{}",traceId);
        MDC.put(TRACE_ID, traceId);
        return traceId;
    }

    public static String getTraceId() {
        return MDC.get(TRACE_ID);
    }

    public static void putTraceId(String traceId) {
        // log.info("put tranceID:{}",traceId);
        MDC.put(TRACE_ID, traceId);
    }

    /**
     * 清理traceId，防止内存溢出
     */
    public static void clearTraceId() {
        // log.info("remove tranceID:{}",MDC.get(TRACE_ID));
        MDC.remove(TRACE_ID);

    }
}
