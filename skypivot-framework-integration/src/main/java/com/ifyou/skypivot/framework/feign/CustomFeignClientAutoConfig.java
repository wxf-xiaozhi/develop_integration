package com.ifyou.skypivot.framework.feign;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Client;
import feign.Logger;
import feign.codec.Decoder;
import feign.optionals.OptionalDecoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wuxiaofang
 * @date 2022年07月27日 下午3:04
 */
@Configuration
@EnableConfigurationProperties(CustomFeignConfigProperies.class)
@ConditionalOnExpression("${custom.feignconfig.isOpen:false}")
public class CustomFeignClientAutoConfig {

    @Autowired
    private ObjectFactory<HttpMessageConverters> messageConverters;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    CustomFeignConfigProperies configProperies;


    @Bean
    @ConditionalOnExpression("${custom.feignconfig.tranceId:false}")
    public FeignInterceptor basicAuthRequestInterceptor() {
        return new FeignInterceptor();
    }

    @Bean
    @ConditionalOnExpression("${custom.feignconfig.tranceId:false}")
    public Decoder feignDecoder(){
        return new FeignDecoder(new OptionalDecoder(new ResponseEntityDecoder(new SpringDecoder(this.messageConverters))),objectMapper);
    }


    @Bean
    Logger.Level feignLoggerLevel() {
        if("NONE".equals(configProperies.getLoggerLevel())){
            return Logger.Level.NONE;
        }else if("BASIC".equals(configProperies.getLoggerLevel())){
            return Logger.Level.BASIC;
        }else if("HEADERS".equals(configProperies.getLoggerLevel())){
            return Logger.Level.HEADERS;
        }else if("FULL".equals(configProperies.getLoggerLevel())){
            return Logger.Level.FULL;
        }else{
            return Logger.Level.NONE;
        }

    }

    @Bean
    @ConditionalOnExpression("${custom.feignconfig.customfeign:false}")
    public Client feignIPClient(LoadBalancerClient loadBalancerClient) {
        return new CustomFeignClient(loadBalancerClient);
    }
}
