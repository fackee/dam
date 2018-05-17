package org.dam.http.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by geeche on 2018/1/27.
 */
public class HttpField {

    private HttpHeader httpHeader;

    private HttpParameter httpParameter;

    private List<Cookie> cookies = new ArrayList<>();

    private Multipart multipart;

    private volatile boolean initCookies = true;

    public HttpField(HttpBuilder builder){
        if(builder.httpHeader != null){
            httpHeader = builder.httpHeader;
        }
        if(builder.httpParameter != null){
            httpParameter = builder.httpParameter;
        }
        if(builder.multipart != null){
            multipart = builder.multipart;
        }
    }

    public HttpHeader getHttpHeader() {
        return httpHeader;
    }

    public void setHttpHeader(HttpHeader httpHeader) {
        this.httpHeader = httpHeader;
    }

    public HttpParameter getHttpParameter() {
        return httpParameter;
    }

    public void setHttpParameter(HttpParameter httpParameter) {
        this.httpParameter = httpParameter;
    }


    public void setCookie(List<Cookie> cookieList) {
        if(initCookies){
            cookies.addAll(cookieList);
        }
        initCookies = false;
    }

    public List<Cookie> getCookies() {
        return cookies;
    }

    public static class HttpBuilder{

        private HttpHeader httpHeader;

        private HttpParameter httpParameter;

        private Multipart multipart;

        public HttpBuilder header(HttpHeader httpHeader){
            this.httpHeader = httpHeader;
            return this;
        }

        public HttpBuilder parameter(HttpParameter httpParameter){
            this.httpParameter = httpParameter;
            return this;
        }
        public HttpBuilder multipart(Multipart multipart){
            this.multipart = multipart;
            return this;
        }
        public HttpField builde(){
            return new HttpField(this);
        }

    }

    public void setMultipart(Multipart multipart) {
        this.multipart = multipart;
    }

    public Multipart getMultipart() {
        return multipart;
    }
}
