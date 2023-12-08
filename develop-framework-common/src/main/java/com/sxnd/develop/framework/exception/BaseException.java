package com.sxnd.develop.framework.exception;

import com.sxnd.develop.framework.enums.BaseErrorMsgEnum;
import lombok.Data;

/**
 * @Author: nieyy
 * @Date: 2019/5/14 23:08
 * @Version 1.0
 * @Description: 全局异常类
 */
@Data
public class BaseException extends RuntimeException {

    public BaseException() {
        super();
    }


    public BaseException(String message) {
        super(message);
    }

    public BaseException(BaseErrorMsgEnum baseErrorMsgEnum) {
        super(baseErrorMsgEnum.getMsg());
        this.code = baseErrorMsgEnum.getCode();
        this.message = baseErrorMsgEnum.getMsg();
    }


    public BaseException(int code, String message) {
        super(code + " : " + message);
        this.code = code;
        this.message = message;
    }

    private int code;
    private String message;


}
