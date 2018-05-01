package com.dam.http;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HttpResponse implements Response {

    private static final Map<String,Object> attribute = new ConcurrentHashMap<>();
    private HttpHeader httpHeader;
    private byte[] content;
    public HttpResponse(){}
    @Override
    public void setHeader(String key, String value) {

    }

    @Override
    public void addAttribute(String key, String value) {

    }


    @Override
    public byte[] getContent() {
        return content;
    }

    @Override
    public void setContent(byte[] content) {
        this.content = content;
    }

    public HttpHeader getHttpHeader() {
        return httpHeader;
    }

    public void setHttpHeader(HttpHeader httpHeader) {
        this.httpHeader = httpHeader;
    }
}