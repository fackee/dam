package com.dam.http;

/**
 * Created by geeche on 2018/1/27.
 */
public class HttpField {

    private HttpHeader httpHeader;

    private HttpParameter httpParameter;

    private Cookie cookie;

    private Session session;

    public HttpField(HttpBuilder builder){
        if(builder.httpHeader != null){
            httpHeader = builder.httpHeader;
        }
        if(builder.httpParameter != null){
            httpParameter = builder.httpParameter;
        }
        if(builder.cookie != null){
            cookie = builder.cookie;
        }
        if(builder.session != null){
            session = builder.session;
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

    public Cookie getCookie() {
        return cookie;
    }

    public void setCookie(Cookie cookie) {
        this.cookie = cookie;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public static class HttpBuilder{

        private HttpHeader httpHeader;

        private HttpParameter httpParameter;

        private Cookie cookie;

        private Session session;

        public HttpBuilder header(HttpHeader httpHeader){
            this.httpHeader = httpHeader;
            return this;
        }

        public HttpBuilder parameter(HttpParameter httpParameter){
            this.httpParameter = httpParameter;
            return this;
        }

        public HttpBuilder cookie(Cookie cookie){
            this.cookie = cookie;
            return this;
        }

        public HttpBuilder session(Session session){
            this.session = session;
            return this;
        }

        public HttpField builde(){
            return new HttpField(this);
        }

    }
}
