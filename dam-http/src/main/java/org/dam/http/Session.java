package org.dam.http;

public interface Session {

    public Object getSession(String key);

    public boolean setSession(String key,Object value);

    public boolean addSession(String key ,Object value);

}