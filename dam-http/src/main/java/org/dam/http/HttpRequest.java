package org.dam.http;

import org.dam.http.bean.Cookie;
import org.dam.http.bean.HttpHeader;
import org.dam.http.bean.HttpParameter;
import org.dam.http.bean.Multipart;

import java.util.List;

public class HttpRequest implements Request {

    private HttpHeader httpHeader;
    private HttpParameter httpParameter;
    private List<Cookie> cookies;
    private Multipart multipart;
    private volatile boolean initCookie = true;
    public HttpRequest(){}
    public HttpRequest(HttpHeader httpHeader){
        this(httpHeader,new HttpParameter());
    }
    public HttpRequest(HttpHeader httpHeader, HttpParameter httpParameter){
        this.httpHeader = httpHeader;
        this.httpParameter = httpParameter;
    }

    @Override
    public HttpHeader getHeader() {
        return httpHeader;
    }

    @Override
    public HttpParameter getHttpParameter() {
        return httpParameter;
    }

    @Override
    public void setHttpHeader(HttpHeader httpHeader) {
        this.httpHeader = httpHeader;
    }

    @Override
    public void setHttpParameter(HttpParameter httpParameter) {
        this.httpParameter = httpParameter;
    }

    @Override
    public void setCookies(List<Cookie> cookies) {
        if(initCookie){
            this.cookies = cookies;
        }
        initCookie = false;
    }

    @Override
    public Cookie getCookie(String name) {
        for(Cookie cookie : cookies){
            if(cookie.getName().equals(name)){
                return cookie;
            }
        }
        return null;
    }

    @Override
    public void setMultipart(Multipart multipart) {
        this.multipart = multipart;
    }

    @Override
    public Multipart getMultipart() {
        return multipart;
    }
}