package org.dam.annotation;


import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Request {

    public String url() default "/index.html";

    public String method() default "GET";

    public String resultType() default "page";
}