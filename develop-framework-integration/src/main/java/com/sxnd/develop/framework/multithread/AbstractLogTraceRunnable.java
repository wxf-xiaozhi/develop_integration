package com.sxnd.develop.framework.multithread;

import org.slf4j.MDC;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 * 增加traceId
 * 
 * @author ningning.cheng
 * @since 2022/11/12
 **/
public abstract class AbstractLogTraceRunnable implements Runnable {
    private final Map<String, String> copyOfContextMap = MDC.getCopyOfContextMap();

    @Override
    public void run() {
        if (!CollectionUtils.isEmpty(this.copyOfContextMap)) {
            MDC.setContextMap(this.copyOfContextMap);
        }

        try {
            this.logTraceRun();
        } finally {
            MDC.clear();
        }

    }

    /**
     * 要实现的线程run方法
     */
    public abstract void logTraceRun();
}
