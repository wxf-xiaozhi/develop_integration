package com.sxnd.develop.framework.constants;

/**
 * @Author: nieyy
 * @Date: 2022/5/19 22:26
 * @Version 1.0
 * @Description:
 */
public class Constants {

    //获取TOKEN的KEY
    public static final String HEADER_STRING = "X-Authorization";

    //签名
    public static final String HEADER_SIGN = "sign";

    //时间戳
    public static final String HEADER_TIMESTAMP = "timestamp";

    //salt
    public static final String HEADER_SALT = "ifyouAuthentication";

    //5分钟
    public static final int EFFECTIVE_TIME = 60 * 2;

    //登录uri
    public static final String LOGIN_URI = "/api/auth/login";
    //刷新token  uri
    public static final String REFRESH_TOKEN_URI = "/api/auth/token";

    //车端调用接口
    public static final String DEVICE_URI = "/api/device";

    //登录用户ID
    public final static String USER_ID = "userId";
    //登录用户账号
    public final static String ACCOUNT = "account";

    public static final String TENANT_ID = "tenantId";

    public static final String AUTHORITY = "authority";

    public static final String FIRST_NAME = "firstName";

    public static final String LAST_NAME = "lastName";

    public static final String ENABLED = "enabled";

    public static final String IS_PUBLIC = "isPublic";

    public static final String CUSTOMER_ID = "customerId";

    //登录用户账号
    public final static String EMAIL = "email";
    //登录用户名称
    public final static String USER_NAME = "userName";

    public static final String DEVICE_IMEI = "deviceImei";

    public static final String DEVICE_ID = "deviceId";

    //服务名
    public static final String BASE = "base";

    public static final String THINGSBOARD = "thingsboard";

    public static final String CONTROLLER = "controller";


}
