package org.dam.http;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager implements Session{

    private static final Map<String,Object> SESSION_MAP = new ConcurrentHashMap<>(16);

    public SessionManager(){}


    @Override
    public Object getSession(String key) {
        if(SESSION_MAP.containsKey(key)){
            return SESSION_MAP.get(key);
        }
        return null;
    }

    @Override
    public boolean setSession(String key, Object value) {
        if(SESSION_MAP.containsKey(key)){
            SESSION_MAP.remove(key);
            SESSION_MAP.putIfAbsent(key,value);
            return true;
        }
        addSession(key,value);
        return false;
    }

    @Override
    public boolean addSession(String key, Object value) {
        if(SESSION_MAP.containsKey(key)){
            setSession(key,value);
            return false;
        }
        SESSION_MAP.putIfAbsent(key,value);
        return true;
    }
}
