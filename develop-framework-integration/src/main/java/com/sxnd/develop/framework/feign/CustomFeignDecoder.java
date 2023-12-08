package com.sxnd.develop.framework.feign;

import cn.hutool.json.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.sxnd.develop.framework.utils.GlobalTraceIdUtil;
import feign.FeignException;
import feign.Response;
import feign.Util;
import feign.codec.Decoder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

/**
 * @author GW00305020
 * @ClassName 解开最外层的公共包装类
 * @description: TODO
 * @date 2023年09月02日
 * @version: 1.0
 */
public class CustomFeignDecoder implements Decoder {

    private final Decoder delegate;
    private ObjectMapper objectMapper;

    public CustomFeignDecoder(Decoder decoder, ObjectMapper objectMapper) {
        this.delegate = decoder;
        this.objectMapper = objectMapper;
    }

    @Override
    public Object decode(Response response, Type type) throws IOException,  FeignException {
        Response.Body body = response.body();
        if (body == null) {
            return null;
        }
        // 如果调用别人的feign接口返回中带有tranceId,则直接用别人的tranceId,为了将tranceId在多层feign调用的时候串起来
        JSONObject jsonObject = objectMapper.readValue(body.asReader(Util.UTF_8), JSONObject.class);
        String traceId = jsonObject.getStr(GlobalTraceIdUtil.TRACE_ID);
        if(StringUtils.isNotBlank(traceId)){
            MDC.put(GlobalTraceIdUtil.TRACE_ID,traceId);
        }
        Response response2 = response.toBuilder().body(new Gson().toJson(jsonObject).getBytes(StandardCharsets.UTF_8)).build();
        return delegate.decode(response2, type);


//        ResponseResult result = objectMapper.readValue(body.asReader(Util.UTF_8),ResponseResult.class);
//        //ResponseResult result = new Gson().fromJson(Util.toString(body.asReader(Util.UTF_8)), ResponseResult.class);
//        if (!result.isSuccess()) {
//            throw new ThingsboardException(result.getMessage(), ThingsboardErrorCode.valueOf(result.getCode()));
//        }
//        Object result2 = result.getResult();
//        Response response2 = response.toBuilder().body(new Gson().toJson(result2).getBytes(StandardCharsets.UTF_8)).build();
//        return delegate.decode(response2, type);
    }
}
