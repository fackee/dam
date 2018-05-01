package com.dam.http;

import com.dam.exception.HttpSessionException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HttpSession implements Session{

    private static final Map<String,Object> sessionCache = new ConcurrentHashMap<>(16);

    public HttpSession(){}

    @Override
    public Object getAttribute(String key) {
        if(sessionCache.containsKey(key)){
            return sessionCache.get(key);
        }
        throw new HttpSessionException("cannot fount this session value by key:"+key);
    }

    @Override
    public boolean setAttibute(String key, Object value) {
        if(sessionCache.containsKey(key)){
            sessionCache.remove(key);
            sessionCache.putIfAbsent(key,value);
            return true;
        }
        throw new HttpSessionException("cannot fount this session value by key:"+key);
    }

    @Override
    public boolean addAttribute(String key, Object value) {
        if(!sessionCache.containsKey(key)){
            sessionCache.putIfAbsent(key,value);
            return true;
        }
         throw new HttpSessionException("this session key was contained");
    }
}