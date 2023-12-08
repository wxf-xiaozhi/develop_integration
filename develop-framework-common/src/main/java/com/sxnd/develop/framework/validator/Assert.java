package com.sxnd.develop.framework.validator;

import com.sxnd.develop.framework.exception.BaseException;
import org.apache.commons.lang3.StringUtils;

/**
 * 数据校验
 *
 * @author hq
 */
public abstract class Assert {

    public static void isBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            throw new BaseException(message);
        }
    }

    public static void isNull(Object object, String message) {
        if (object == null) {
            throw new BaseException(message);
        }
    }
}
