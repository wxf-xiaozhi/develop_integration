package com.sxnd.develop.framework.entity;

import com.sxnd.develop.framework.enums.BaseErrorMsgEnum;
import lombok.Data;
import org.slf4j.MDC;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 统一返回值
 *
 * @author sunbing
 * @version 1.0
 * @since 2023/1/30 13:48
 */
@Data
public class ResponseResult<T> {
    /**
     * 响应编码。0-正常，小于0-系统级错误，大于0-业务级错误
     */

    private int returncode = 0;
    /**
     * 响应消息。code非0时，message非空
     */
    private String message = "OK";
    /**
     * 响应结果
     */
    private T result;

    private String traceId;

    private String timestamp;

    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static <T> ResponseResult<T> Fail(int code){
        ResponseResult<T> result = new ResponseResult<T>();
        result.setReturncode(code);
        result.setMessage("");
        result.setTimestamp(LocalDateTime.now().format(dateTimeFormatter));
        result.setTraceId(MDC.get("traceId"));
        return result;
    }

    public static <T> ResponseResult<T> Fail(String failMsg){
        ResponseResult<T> result = new ResponseResult<T>();
        result.setMessage(failMsg);
        result.setReturncode(-1);
        result.setTimestamp(LocalDateTime.now().format(dateTimeFormatter));
        result.setTraceId(MDC.get("traceId"));
        return result;
    }


    public static <T> ResponseResult<T> Fail(int code, String failMsg){
        ResponseResult<T> result = new ResponseResult<T>();
        result.setReturncode(code);
        result.setMessage(failMsg);
        result.setTimestamp(LocalDateTime.now().format(dateTimeFormatter));
        result.setTraceId(MDC.get("traceId"));
        return result;
    }

    public static <T> ResponseResult<T> Fail(BaseErrorMsgEnum baseErrorMsgEnum){
        ResponseResult<T> result = new ResponseResult<T>();
        result.setReturncode(baseErrorMsgEnum.getCode());
        result.setMessage(baseErrorMsgEnum.getMsg());
        result.setTimestamp(LocalDateTime.now().format(dateTimeFormatter));
        result.setTraceId(MDC.get("traceId"));
        return result;
    }

    public static <T> ResponseResult<T> Success(T result){
        ResponseResult<T> responseResult = new ResponseResult<T>();
        responseResult.setReturncode(0);
        responseResult.setMessage("成功");
        responseResult.setResult(result);
        responseResult.setTimestamp(LocalDateTime.now().format(dateTimeFormatter));
        responseResult.setTraceId(MDC.get("traceId"));
        return responseResult;
    }

    public static <T> ResponseResult<T> Success() {
        ResponseResult<T> responseResult = new ResponseResult();
        responseResult.setMessage("成功");
        responseResult.setReturncode(0);
        responseResult.setTimestamp(LocalDateTime.now().format(dateTimeFormatter));
        responseResult.setTraceId(MDC.get("traceId"));
        return responseResult;
    }

}
