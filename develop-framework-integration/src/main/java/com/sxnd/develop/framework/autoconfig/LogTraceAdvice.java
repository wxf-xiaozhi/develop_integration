package com.sxnd.develop.framework.autoconfig;

;
import com.sxnd.develop.framework.utils.GlobalTraceIdUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author ningning.cheng
 * @since 2022/10/26
 **/
@Aspect
public class LogTraceAdvice {

    @Pointcut("@annotation(com.sxnd.develop.framework.annotation.LogTrace)")
    private void pointCut() {}


    @Before("pointCut()")
    public static void LogTraceBefore(JoinPoint joinPoint) {
        GlobalTraceIdUtil.putTraceId(null);
    }

    @After("pointCut()")
    public static void LogTraceAfter() {
        GlobalTraceIdUtil.clearTraceId();
    }

}
