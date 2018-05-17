package org.dam.http;

import org.dam.http.bean.Cookie;
import org.dam.http.bean.HttpHeader;
import org.dam.http.bean.HttpParameter;
import org.dam.http.bean.Multipart;

import java.util.List;

public interface Request {

    public HttpHeader getHeader();

    public HttpParameter getHttpParameter();

    public void setHttpHeader(HttpHeader httpHeader);

    public void setHttpParameter(HttpParameter httpParameter);

    public void setCookies(List<Cookie> cookies);

    public Cookie getCookie(String name);

    public void setMultipart(Multipart multipart);

    public Multipart getMultipart();
}