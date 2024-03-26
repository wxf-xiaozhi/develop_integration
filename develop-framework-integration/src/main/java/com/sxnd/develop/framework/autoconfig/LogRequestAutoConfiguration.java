package com.sxnd.develop.framework.autoconfig;


import com.sxnd.develop.framework.annotation.LoginUserConfigurer;
import com.sxnd.develop.framework.properties.RequestParamProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * 描述: web请求参数日志
 *
 * @author ningning.cheng
 * @create 2022/6/17
 */
@Slf4j
@Configuration
@AutoConfigureOrder(2)
@ConditionalOnProperty(prefix = "log.request", name = "enable", havingValue = "true")
public class LogRequestAutoConfiguration {
    @Autowired(required = false)
    private LoginUserConfigurer loginUserConfigurer;

    @Configuration
    @ConditionalOnMissingClass("org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter")
    @EnableConfigurationProperties(RequestParamProperties.class)
    public class LogRequestConfiguration implements WebMvcConfigurer {
        private RequestParamProperties requestParamProperties;

        public LogRequestConfiguration(RequestParamProperties requestParamProperties) {
            this.requestParamProperties = requestParamProperties;
        }

        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(new LogRequestInterceptor())
                .excludePathPatterns(this.requestParamProperties.getNoFilter()).addPathPatterns("/**");
        }
    }

    @Configuration
    @ConditionalOnClass(WebMvcConfigurerAdapter.class)
    @EnableConfigurationProperties(RequestParamProperties.class)
    public class LogRequestConfigurer extends WebMvcConfigurerAdapter {
        private RequestParamProperties requestParamProperties;

        public LogRequestConfigurer(RequestParamProperties requestParamProperties) {
            this.requestParamProperties = requestParamProperties;
        }

        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(new LogRequestInterceptor())
                .excludePathPatterns(this.requestParamProperties.getNoFilter()).addPathPatterns("/**");
        }
    }

    public class LogRequestInterceptor extends HandlerInterceptorAdapter {

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
            try {
                String requestUri = request.getRequestURI();
                log.info("请求-URI：{},queryString:{},currentUser:{}", requestUri, request.getQueryString(),
                    Optional.ofNullable(LogRequestAutoConfiguration.this.loginUserConfigurer)
                        .map(LoginUserConfigurer::getCurrentName).orElse("未知"));
            } catch (Exception e) {
                log.error("打印请求日志异常（不影响业务）e=", e);
            }
            return true;
        }

    }


}
