package org.dam.http;

public interface Request {

    public HttpHeader getHeader();

    public Cookie getCookie();

    public Session getSession();

    public HttpParameter getHttpParameter();

    public void setHttpHeader(HttpHeader httpHeader);

    public void setHttpParameter(HttpParameter httpParameter);

    public void setCookie(Cookie cookie);

    public void setSession(Session session);

}