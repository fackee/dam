package org.dam.http;

public interface Session {

    public Object getAttribute(String key);

    public boolean setAttibute(String key,Object value);

    public boolean addAttribute(String key ,Object value);

}