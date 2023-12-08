package com.sxnd.develop.framework.mq;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author GW00305020
 * @ClassName GenericMqMessage
 * @description: MQ 消息通用类，所有MQ消息继承此类
 * @date 2023年11月15日
 * @version: 1.0
 */
@Data
public class GenericMqMessage implements Serializable {

    private String mqUniqId;

    public GenericMqMessage(String mqUniqId) {
        this.mqUniqId = mqUniqId;
    }

    public GenericMqMessage() {
        this.mqUniqId = UUID.randomUUID().toString();
    }
}
