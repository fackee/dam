package org.dam.http;

public interface Response {

    public void generateHeaderBytes();
    public byte[] getHeaderBytes();

    public byte[] getBodyBytes();
    public void setBodyBytes(byte[] bodyBytes);

    public void setHttpHeader(String key, String value);

    public HttpHeader getHttpHeader();

    public void addAttribute(String key, Object value);
    public Object addAttribute(String key);
}