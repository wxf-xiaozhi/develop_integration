package com.sxnd.develop.framework.multithread;

import org.slf4j.MDC;
import org.springframework.util.CollectionUtils;

import java.util.Map;
import java.util.concurrent.Callable;

/**
 * 增加traceId
 * 
 * @author ningning.cheng
 * @since 2022/11/12
 **/
public abstract class AbstractLogTraceCallable<T> implements Callable<T> {

    private final Map<String, String> copyOfContextMap = MDC.getCopyOfContextMap();

    @Override
    public T call() throws Exception {
        if (!CollectionUtils.isEmpty(this.copyOfContextMap)) {
            MDC.setContextMap(this.copyOfContextMap);
        }
        try {
            return this.logTraceCall();
        } finally {
            MDC.clear();
        }
    }

    /**
     * 要实现的线程call方法
     */
    public abstract T logTraceCall();
}
