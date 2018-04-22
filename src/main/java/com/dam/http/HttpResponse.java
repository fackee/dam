package com.dam.http;

public class HttpResponse implements Response {
    private String content;
    public HttpResponse(){}
    @Override
    public void setHeader(String key, String value) {

    }

    @Override
    public void addAttribute(String key, String value) {

    }
    @Override
    public void setContent(String content) {
        this.content = content;
    }
    @Override
    public String getContent() {
        return content;
    }
}