package com.toylet.annotation;

import java.lang.annotation.*;

/**
 * Created by geeche on 2018/1/21.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface URL {
}
