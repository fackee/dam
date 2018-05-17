package org.dam.http;

import org.dam.http.bean.HttpHeader;

import java.util.Map;

public interface Response {

    public void generateHeaderBytes();
    public byte[] getHeaderBytes();

    public byte[] getBodyBytes();
    public void setBodyBytes(byte[] bodyBytes);

    public void setHttpHeader(String key, String value);

    public HttpHeader getHttpHeader();


    public void addAttribute(String key, Object value);
    public Map<String,Object> getAttribute();
    public Object getAttribute(String key);
}