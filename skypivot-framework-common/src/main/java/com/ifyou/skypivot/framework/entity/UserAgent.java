package com.ifyou.skypivot.framework.entity;

import com.ifyou.skypivot.framework.constants.Constants;
import com.ifyou.skypivot.framework.enums.BaseErrorMsgEnum;
import com.ifyou.skypivot.framework.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@Slf4j
public class UserAgent {
    /**
     * 获取系统用户id
     * @return 系统用户id
     */
    public static String getUserId(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return request.getHeader(Constants.USER_ID);
    }

    /**
     * 获取系统用户名称
     * @return 系统用户名称
     */
    public static String getAccount(){
        HttpServletRequest request = getRequest();
        return request.getHeader(Constants.ACCOUNT);
    }

    /**
     * 获取系统用户名称
     * @return 系统用户名称
     */
    public static String getUsername() {
        HttpServletRequest request =getRequest();
        String userName = "";
        try {
            userName = URLDecoder.decode(request.getHeader(Constants.FIRST_NAME),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error("获取用户名出错！" ,e);
            throw new BaseException(BaseErrorMsgEnum.USER_NAME_ERROR);
        }
        return userName;
    }

    public static String getLastName()  {
        HttpServletRequest request = getRequest();
        String lastName = "";
        try {
            lastName = URLDecoder.decode(request.getHeader(Constants.LAST_NAME),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error("获取lastName出错！" ,e);
            throw new BaseException(BaseErrorMsgEnum.USER_NAME_ERROR);
        }

        return lastName;
    }

    public static boolean getEnable() {
        HttpServletRequest request = getRequest();
        String enable = request.getHeader(Constants.ENABLED);
        if(!StringUtils.hasText(enable)){
            enable = "false";
        }
        return Boolean.valueOf(enable);
    }

    public static String getAuthority() {
        HttpServletRequest request = getRequest();
        return request.getHeader(Constants.AUTHORITY);
    }

    public static String getTenantId() {
        HttpServletRequest request = getRequest();
        return request.getHeader(Constants.TENANT_ID);
    }

    public static String getIsPublic() {
        HttpServletRequest request = getRequest();
        return request.getHeader(Constants.IS_PUBLIC);
    }

    public static String getCustomerId() {
        HttpServletRequest request = getRequest();
        return request.getHeader(Constants.CUSTOMER_ID);
    }

    public static String getDeviceImei() {
        HttpServletRequest request = getRequest();
        return request.getHeader(Constants.DEVICE_IMEI);
    }

    public static String getDeviceId() {
        HttpServletRequest request = getRequest();
        return request.getHeader(Constants.DEVICE_ID);
    }

    public static HttpServletRequest getRequest(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return request;
    }


}
