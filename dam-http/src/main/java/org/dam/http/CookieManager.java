package org.dam.http;

import org.dam.http.bean.Cookie;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CookieManager {

    private List<Cookie> COOKIES = new CopyOnWriteArrayList<>();

    public CookieManager(){

    }

    public synchronized Cookie setCookie(String name, String value){
        for(Cookie cookie : COOKIES){
            if(cookie.getName().equals(name)){
                cookie.setValue(value);
                return cookie;
            }
        }
        Cookie cookie = new Cookie();
        cookie.setName(name);
        cookie.setValue(value);
        COOKIES.add(cookie);
        return cookie;
    }

    public Cookie getCookie(String name){
        for(Cookie cookie : COOKIES){
            if(cookie.getName().equals(name)){
                return cookie;
            }
        }
        return null;
    }

    public Iterator<Cookie> getCookies(){
        return COOKIES.iterator();
    }

}
