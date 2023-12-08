package com.sxnd.develop.framework.feign;

import cn.hutool.core.collection.CollUtil;
import com.sxnd.develop.framework.utils.GlobalTraceIdUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Enumeration;
import java.util.UUID;

/**
 * @author GW00305020
 * @ClassName FeignInterceptor
 * @description: TODO
 * @date 2023年09月13日
 * @version: 1.0
 */
@Slf4j
public class CustomFeignInterceptor implements RequestInterceptor {

    public static final String CONTENT_LENGTH = "content-length";



    @Override
    public void apply(RequestTemplate template) {

        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(servletRequestAttributes != null ){
            HttpServletRequest httpServletRequest = servletRequestAttributes.getRequest();
            if(httpServletRequest != null ) {
                Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
                while (headerNames.hasMoreElements()) {
                    String headerName = headerNames.nextElement();
                    if (CONTENT_LENGTH.equalsIgnoreCase(headerName)) {
                        String value = httpServletRequest.getHeader(headerName);
                        log.info("请求头中的content-length:{}",value);
                        continue;
                    }
                    template.header(headerName, httpServletRequest.getHeader(headerName));
                }
            }
        }

        Collection<String> strings = template.headers().get(GlobalTraceIdUtil.TRACE_ID);
        if(CollUtil.isEmpty(strings)){
            String tranceId = GlobalTraceIdUtil.getTraceId();
            if(StringUtils.isBlank(tranceId)){
                tranceId = UUID.randomUUID().toString().replaceAll("-", "");
                log.info("未获取到tranceID,重新生成tranceId:{}",tranceId);
            }
            template.header(GlobalTraceIdUtil.TRACE_ID,tranceId);
        }
    }
}
