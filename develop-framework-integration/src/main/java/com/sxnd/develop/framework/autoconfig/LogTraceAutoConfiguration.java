package com.sxnd.develop.framework.autoconfig;


import com.sxnd.develop.framework.utils.GlobalTraceIdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 描述:
 *
 * @author ningning.cheng
 * @create 2021/3/23
 */
@Slf4j
@Configuration
@AutoConfigureOrder(1)
public class LogTraceAutoConfiguration {

    @Configuration
    @ConditionalOnClass(WebMvcConfigurerAdapter.class)
    public class LogTraceConfigurer extends WebMvcConfigurerAdapter {

        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(new LogTraceInterceptor()).addPathPatterns("/**");
        }

    }

    @Configuration
    @ConditionalOnMissingClass("org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter")
    public class LogTraceConfiguration implements WebMvcConfigurer {

        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(new LogTraceInterceptor()).addPathPatterns("/**");
        }

    }

    public static class LogTraceInterceptor extends HandlerInterceptorAdapter {
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
            // 从请求头获取traceId
            String transferTraceId = request.getHeader(GlobalTraceIdUtil.TRACE_ID);
            GlobalTraceIdUtil.putTraceId(transferTraceId);
            return super.preHandle(request, response, handler);
        }

        @Override
        public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
            Exception ex) {
            // 清空traceId
            GlobalTraceIdUtil.clearTraceId();
        }
    }

    @Bean
    @ConditionalOnMissingBean(LogTraceAdvice.class)
    public LogTraceAdvice logTraceAdvice() {
        return new LogTraceAdvice();
    }
}
