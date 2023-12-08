package com.sxnd.develop.framework.loadbalance;

import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @author wuxiaofang
 * @date 2022年07月27日 下午3:04
 */
@Configuration
public class CustomLoadBalancerConfig {




    @Bean
//    @ConditionalOnExpression("!(${custom.loadbalancer.defaultIp}.equals(0.0.0.0))")
    @Conditional(CustomLoadBalancerCondition.class)
    public ReactorServiceInstanceLoadBalancer rjLoadBalance(LoadBalancerClientFactory loadBalancerClientFactory, Environment environment) {
        String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
        return new CustomLoadBalancer(loadBalancerClientFactory.getLazyProvider(name, ServiceInstanceListSupplier.class),name);
    }

}
