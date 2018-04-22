package com.dam.http;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhujianxin on 2018/3/14.
 */
public class HttpParameter {

    private static final Map<String,String> parameterMap = new HashMap<String,String>(16);

    public HttpParameter(){}

    public void setParameter(String key,String value){
        if(!parameterMap.containsKey(key)){
            parameterMap.put(key,value);
        }
    }

    public String getParameter(String key){
        return parameterMap.get(key);
    }

}
