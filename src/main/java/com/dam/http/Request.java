package com.dam.http;

public interface Request {

    public HttpHeader getHeader();

    public Cookie getCookie();

    public Session getSession();

    public HttpParameter getParameter();

}