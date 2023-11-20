package com.ifyou.skypivot.framework.loadbalance;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.DefaultResponse;
import org.springframework.cloud.client.loadbalancer.EmptyResponse;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.loadbalancer.core.NoopServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.SelectedInstanceCallback;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author wuxiaofang
 * @date 2022年07月27日 下午3:06
 */
@Slf4j
public class CustomLoadBalancer implements ReactorServiceInstanceLoadBalancer {

    //转发服务的名称
    private final String serviceId;
    //轮询随机数
    private final AtomicInteger position;

    @Autowired
    private InetUtils inetUtils;

    @Autowired
    private CustomLoadBalancerConfigProperies configProperies;




    private ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider;

    public CustomLoadBalancer(ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider, String serviceId) {
        this(serviceInstanceListSupplierProvider, serviceId,  new Random().nextInt(1000));
    }

    public CustomLoadBalancer(ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider, String serviceId, int seedPosition) {
        this.serviceInstanceListSupplierProvider = serviceInstanceListSupplierProvider;
        this.serviceId = serviceId;
        this.position = new AtomicInteger(seedPosition);
    }

    @Override
    public Mono<Response<ServiceInstance>> choose(Request request) {
//        log.info("当前请求：{}",JSONUtil.toJsonStr(request));
        ServiceInstanceListSupplier supplier = serviceInstanceListSupplierProvider
                .getIfAvailable(NoopServiceInstanceListSupplier::new);
        return supplier.get(request).next()
                .map(serviceInstances -> processInstanceResponse(supplier,request, serviceInstances));
    }


    @Override
    public Mono<Response<ServiceInstance>> choose() {
        return null;
    }

    private Response<ServiceInstance> processInstanceResponse(ServiceInstanceListSupplier supplier, Request request, List<ServiceInstance> serviceInstances) {
        Response<ServiceInstance> serviceInstanceResponse = this.getInstanceResponse(serviceInstances,request);
        if (supplier instanceof SelectedInstanceCallback && serviceInstanceResponse.hasServer()) {
            ((SelectedInstanceCallback) supplier).selectedServiceInstance(serviceInstanceResponse.getServer());
        }
        return serviceInstanceResponse;
    }

    private Response<ServiceInstance> getInstanceResponse(List<ServiceInstance> instances, Request request) {
        Response<ServiceInstance> serviceInstanceResponse = null;
        if (instances.isEmpty()) {
            if (log.isWarnEnabled()) {
                log.warn("Gateway No servers available:" + serviceId);
            }
            return new EmptyResponse();
        } else {
//            localIp = inetUtils.findFirstNonLoopbackHostInfo().getIpAddress();
            // 规则: 默认调用本机服务
            List<ServiceInstance> localServers = new ArrayList<>();
            List<ServiceInstance> otherServers = new ArrayList<>();
            List<String> intanceIpList = instances.stream().map(ServiceInstance::getHost).collect(Collectors.toList());
            for (ServiceInstance instance : instances) {
                String host = instance.getHost();
                if(!"0.0.0.0".equals(configProperies.getDefaultIp()) && host.equals(configProperies.getDefaultIp())){
                    localServers.add(instance);
                }else{
                    otherServers.add(instance);
                }
            }
            // 如果默认服务实例, 则优先默认服务实例
            if(CollectionUtil.isNotEmpty(localServers)){
                serviceInstanceResponse = roundRobin(position, localServers);
            }else{
                // 如果本机服务不存在, 则调用其他
                serviceInstanceResponse = roundRobin(position, otherServers);
            }
            log.debug("service:[{}],当前注册中心包含实例：{},默认服务ip:{},路由uri:{},",serviceId,JSONUtil.toJsonStr(intanceIpList),configProperies.getDefaultIp(),serviceInstanceResponse.getServer().getUri());
            return serviceInstanceResponse;


        }
    }


    private Response<ServiceInstance> roundRobin(AtomicInteger position, List<ServiceInstance> instances) {
        int pos = Math.abs(position.incrementAndGet());
        return new DefaultResponse(instances.get(pos % instances.size()));
    }



}
