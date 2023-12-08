package com.sxnd.develop.framework.loadbalance;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.context.annotation.Configuration;

/**
 * @author wuxiaofang
 * @date 2022年07月27日 下午3:03
 */
@Configuration
@EnableConfigurationProperties(CustomLoadBalancerConfigProperies.class)
@LoadBalancerClients(defaultConfiguration = CustomLoadBalancerConfig.class)
public class CustomLoadBalancerAutoConfig {



}
