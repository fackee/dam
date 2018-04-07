package httpserver.core;

import httpserver.http.Cookie;
import httpserver.http.HttpHeader;
import httpserver.http.HttpParameter;
import httpserver.http.Session;

public interface Request {

    public HttpHeader getHeader();

    public Cookie getCookie();

    public Session getSession();

    public HttpParameter getParameter();

}