package com.ifyou.skypivot.framework.loadbalance;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author GW00305020
 * @ClassName CustomLoadBanlancerConfigProperies
 * @description: TODO
 * @date 2023年11月14日
 * @version: 1.0
 */
@ConfigurationProperties("custom.loadbalancer")
@Data
public class CustomLoadBalancerConfigProperies {

    private String defaultIp;

}
