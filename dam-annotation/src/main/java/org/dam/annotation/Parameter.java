package org.dam.annotation;

import java.lang.annotation.*;

/**
 * Created by zhujianxin on 2018/3/8.
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Parameter {

    String paramName();

    String defaultValue() default "";

}
