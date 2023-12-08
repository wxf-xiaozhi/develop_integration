package com.sxnd.develop.framework.exception;

import lombok.Data;

/**
 * @Author: nieyy
 * @Date: 2019/5/14 23:08
 * @Version 1.0
 * @Description: 全局异常类
 */
@Data
public class AuthException extends RuntimeException {

    public AuthException() {
        super();
    }

    public AuthException(String message) {
        super(message);
    }

    public AuthException(int code, String message) {
        super(code + " : " + message);
        this.code = code;
        this.message = message;
    }

    private int code;
    private String message;


}
