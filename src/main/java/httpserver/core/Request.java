package httpserver.core;

import httpserver.http.Cookie;
import httpserver.http.HttpHeader;
import httpserver.http.Parameter;
import httpserver.http.Session;

public interface Request {

    HttpHeader getHeader();

    Cookie getCookie();

    Session getSession();

    Parameter getParameter();


}