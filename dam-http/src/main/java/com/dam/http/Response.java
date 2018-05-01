package com.dam.http;

public interface Response {

    public void setContent(byte[] content);
    public byte[] getContent();
    public void setHeader(String key, String value);
    public void addAttribute(String key, String value);

}