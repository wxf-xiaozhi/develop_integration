package com.ifyou.skypivot.framework.feign;

import com.ifyou.skypivot.framework.exception.BaseException;
import feign.Client;
import feign.Request;
import feign.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;


/**
 * @author GW00305020
 * @ClassName CustomFeignLoadBalancerClient
 * @description: 支持版本
 *      spring-cloud-commons:3.1.7
 *      springboot 2.5.0-2.7.15
 *      io.github.openfeign:feign-core:11.10
 *       spring-cloud.version 2021.0.8
 *      的自定义feign请求
 * @date 2023年09月20日
 * @version: 1.0
 */
@Slf4j
public class CustomFeignClient extends Client.Default {

    LoadBalancerClient loadBalancerClient;


    /**
     *
     * @param loadBalancerClient loadBalancerClient
     */
    public CustomFeignClient(LoadBalancerClient loadBalancerClient) {
        super(null, null);
        this.loadBalancerClient = loadBalancerClient;
    }




    @Override
    public Response execute(Request request, Request.Options options) throws IOException {
        URI originalUri = URI.create(request.url());
        String serviceId = originalUri.getHost();
        Assert.state(serviceId != null, "Request URI does not contain a valid hostname: " + originalUri);
        // 优先走自己配置的
        List<String> list = (List) request.headers().get(serviceId);
        if(list != null && list.size() > 0){
            String ipAndPort = list.get(0);
            if(!StringUtils.isEmpty(ipAndPort)){
                String[] strs = ipAndPort.split(":");
                if(strs==null || strs.length<=1){
                    throw new BaseException(19999,"header中"+serviceId+"服务对应的配置格式为  ip:port");
                }
                URI uri = UriComponentsBuilder.fromUri(originalUri).scheme(originalUri.getScheme()).host(strs[0]).port(strs[1]).build(true).toUri();
                log.debug("请求至：---> {}",uri.toString());
                Request newRequest = this.buildRequest(request, uri.toString());
                return super.execute(newRequest, options);
            }
        }


        // 没有单独配置,那按负载均衡策略选一个
        ServiceInstance instance = this.loadBalancerClient.choose(serviceId);
        if (instance == null) {
            String message = "Load balancer does not contain an instance for the service " + serviceId;
            if (log.isWarnEnabled()) {
                log.warn(message);
            }
            return Response.builder().request(request).status(HttpStatus.SERVICE_UNAVAILABLE.value()).body(message, StandardCharsets.UTF_8).build();
        } else {
            String reconstructedUrl = this.loadBalancerClient.reconstructURI(instance, originalUri).toString();
            log.info("请求至：---> {}",reconstructedUrl);
            Request newRequest = this.buildRequest(request, reconstructedUrl);
            return super.execute(newRequest, options);
        }

    }

    private Request buildRequest(Request request, String reconstructedUrl) {
        return Request.create(request.httpMethod(), reconstructedUrl, request.headers(), request.body(),
                request.charset(), request.requestTemplate());
    }

}
