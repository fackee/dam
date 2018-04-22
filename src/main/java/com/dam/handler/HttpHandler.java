package com.dam.handler;

import com.dam.exception.HttpProtocalException;
import com.dam.http.Request;
import com.dam.http.Response;

/**
 * Created by zhujianxin on 2018/3/19.
 */
public class HttpHandler extends AbstractHandler {

    public HttpHandler(){
    }

    @Override
    public void handle(Request baseRequest, Response baseResponse) {
        String httpResponse = "HTTP/1.1 200 OK\r\n" +
                "Content-Length: 38\r\n" +
                "Content-Type: text/html\r\n" +
                "\r\n" +
                "\n" +
                "<html>"+
                "<header></header>"+
                "<body>" +
                "<h1>hello</h1><h2>dam</h2>"+
                "</body>"+
                "</html>";
        baseResponse.setContent(httpResponse);
        throw new HttpProtocalException();
    }
}
