package com.ifyou.skypivot.framework.enums;

/**
 * @Author: nieyy
 * @Date: 2019/5/7 23:06
 * @Version 1.0
 * @Description: 全局模块错误枚举类
 */
public enum BaseErrorMsgEnum {

    USER_NAME_ERROR(200001,"用户名错误"),
    JSON_CHANGE_ERROR(200050,"json转换String出错"),
    AUTHORIZATION_NOT_FOUND(401,"你没有访问权限"),
    UnknowErrorMsg(99999,"系统繁忙，请稍后再试");


    private int code;
    private String msg;

    private BaseErrorMsgEnum(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 根据错误代码获取错误信息
     * @param code
     * @return
     */
    public static String getMsgByCode(int code){
        for(BaseErrorMsgEnum item : BaseErrorMsgEnum.values()){
            if(code==item.getCode()){
                return item.msg;
            }
        }
        return "";
    }


}
