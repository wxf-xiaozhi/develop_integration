package com.sxnd.develop.framework.loadbalance;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @author wuxiaofang
 * @date 2022年07月27日 下午3:06
 */
@Slf4j
public class CustomLoadBalancerCondition implements Condition {


    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Environment environment = context.getEnvironment();
        String defaultIp = environment.getProperty("custom.loadbalancer.defaultIp");
        // 就算没有设置customLoadBalance.isOpen，那么isOpen就等于null，Boolean.valueOf对于null照样会返回false的
        return StringUtils.isNotBlank(defaultIp) && !"0.0.0.0".equals(defaultIp);
    }
}
