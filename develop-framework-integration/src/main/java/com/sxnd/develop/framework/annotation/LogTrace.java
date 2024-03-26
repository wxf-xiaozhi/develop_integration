package com.sxnd.develop.framework.annotation;

import java.lang.annotation.*;

/**
 * 手动添加logTrace
 * 
 * @author ningning.cheng
 * @since 2022/10/25
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogTrace {

}
