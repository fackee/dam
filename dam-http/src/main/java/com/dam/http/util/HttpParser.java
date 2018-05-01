package com.dam.http.util;

import com.dam.io.EndPoint;
import com.dam.io.nio.buffer.Buffer;
import com.dam.io.nio.buffer.NioBuffer;
import com.dam.io.nio.buffer.ThreadLocalBuffer;
import com.dam.http.HttpField;
import com.dam.http.HttpHeader;
import com.dam.http.HttpParameter;

import java.io.IOException;

/**
 * 1判断是否读完？读完注册写事件，未读完进行具体parse
 * 2判断
 */
public class HttpParser {

    private static final String METHOD_GET = "GET";
    private static final String METHOD_POST = "POST";
    private static final String METHOD_HEAD = "HEAD";
    private static final String METHOD_PUT = "PUT";
    private static final String METHOD_DELETE = "DELETE";
    private static final String METHOD_OPTIONS = "OPTIONS";

    private static final String HTTP_MESSAGE_REGEX = "\r\n";
    private static final String HTTP_PARAM_RAGEX = "&";

    private final ThreadLocalBuffer currentRequestBuffer = new ThreadLocalBuffer(ThreadLocalBuffer.BufferSize.REQUEST_BUFFER_SIZE.getSize());

    private EndPoint endPoint;
    private String requestContent;
    private HttpField httpField;

    public HttpParser(EndPoint endPoint) {
        this.endPoint = endPoint;
    }

    public HttpParser parse() throws IOException {
        final Buffer tempbuffer = new NioBuffer();
        endPoint.fill(tempbuffer);
        currentRequestBuffer.fillBuffer(tempbuffer);
        requestContent = new String(currentRequestBuffer.getData());
        if (requestContent != null && requestContent.length() > 0) {
            String[] lineContent = requestContent.split(HTTP_MESSAGE_REGEX);
            if (lineContent.length > 0) {
                HttpParameter httpParameter = new HttpParameter();
                HttpHeader.HttpHeaderBuilder headerBuilder = new HttpHeader.HttpHeaderBuilder();
                for (String target : lineContent) {
                    messageParse(target, httpParameter, headerBuilder);
                }
                HttpHeader httpHeader = headerBuilder.builde();
                httpField = new HttpField.HttpBuilder()
                        .header(httpHeader)
                        .parameter(httpParameter)
                        .builde();
            }
        }
        return this;
    }

    public HttpHeader.HttpMethod methodCase(String target) {
        if (target.startsWith(METHOD_GET)) {
            return new HttpHeader.HttpMethod(HttpHeader.Method.GET);
        }
        if (target.startsWith(METHOD_POST)) {
            return new HttpHeader.HttpMethod(HttpHeader.Method.POST);
        }
        if (target.startsWith(METHOD_HEAD)) {
            return new HttpHeader.HttpMethod(HttpHeader.Method.HEAD);
        }
        if (target.startsWith(METHOD_PUT)) {
            return new HttpHeader.HttpMethod(HttpHeader.Method.PUT);
        }
        if (target.startsWith(METHOD_DELETE)) {
            return new HttpHeader.HttpMethod(HttpHeader.Method.DELETE);
        }
        if (target.startsWith(METHOD_OPTIONS)) {
            return new HttpHeader.HttpMethod(HttpHeader.Method.OPTIONS);
        }
        return null;
    }

    public void messageParse(String target, HttpParameter httpParameter, HttpHeader.HttpHeaderBuilder builder) {
        if (target.contains(" HTTP/")) {
            String method = target.substring(0, target.indexOf(" /"));
            builder.httpMethod(methodCase(method));
            builder.httpProtocalVersion(target.substring(target.indexOf("HTTP/"), target.length()));
            if (method.equals(METHOD_GET)) {
                String urlString = target.substring(target.indexOf(" /"), target.indexOf("HTTP/"));
                if (urlString.contains("?")) {
                    builder.url(new HttpHeader.URL(urlString.substring(target.indexOf(" /"), target.indexOf("?"))));
                    String parameterString = target.substring(target.indexOf("?") + 1, target.indexOf("HTTP/"));
                    String[] parametersArr = parameterString.split("&");
                    for (String param : parametersArr) {
                        httpParameter.setParameter(param.substring(0, param.indexOf("=")), param.substring(param.indexOf("=") + 1, param.length()));
                    }
                } else {
                    builder.url(new HttpHeader.URL(target.substring(target.indexOf(" /"), target.indexOf("HTTP/"))));
                }
            }
        }
        if (target.contains("HOST:")) {
            builder.host(target.substring(target.indexOf(":") + 1, target.length()));
            return;
        }
        if (target.contains("Pargma:")) {

        }
        if (target.contains("Cache-Control:")) {
            builder.cacheControl(target.substring(target.indexOf(":") + 1, target.length()));
            return;
        }
        if (target.contains("Upgrade-Insecure-Requests:")) {

        }
        if (target.contains("User-Agent:")) {
            builder.userAgent(target.substring(target.indexOf(":") + 1, target.length()));
            return;
        }
        if (target.contains("Accept:")) {
            builder.accept(target.substring(target.indexOf(":") + 1, target.length()));
            return;
        }
        if (target.contains("Accept-Encoding:")) {
            builder.acceptEncoding(target.substring(target.indexOf(":") + 1, target.length()));
            return;
        }
        if (target.contains("Accept-Language:")) {
            builder.acceptLanguage(target.substring(target.indexOf(":") + 1, target.length()));
            return;
        }
        if (!target.contains(":") && !target.contains("HTTP")) {
            String[] paramArr = target.split(HTTP_PARAM_RAGEX);
            for (String param : paramArr) {
                httpParameter.setParameter(param.substring(0, param.indexOf("=")), param.substring(param.indexOf("=") + 1, param.length()));
            }
        }
    }


    public HttpField getHttpField() {
        return httpField;
    }

    public static void main(String[] args) {
        String httpMessage = "POST / HTTP/1.1\n" +
                "Host: localhost:8888/users/login\n" +
                "Connection: keep-alive\n" +
                "Content-Length: 20\n" +
                "Cache-Control: max-age=0\n" +
                "Upgrade-Insecure-Requests: 1\n" +
                "Origin: null\n" +
                "Content-Type: application/x-www-form-urlencoded\n" +
                "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36\n" +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8\n" +
                "Accept-Encoding: gzip, deflate, br\n" +
                "Accept-Language: zh-CN,zh;q=0.9\n" +
                "\n" +
                "um=qwer&psd=qwer1234";
        String m = "GET /a/b?c=123&d=abcd HTTP/1.1";
        String[] arr = m.substring(m.indexOf("?") + 1, m.indexOf("HTTP/")).split("&");
        for (String s : arr) {
            System.out.println(s.substring(0, s.indexOf("=")) + "      " + s.substring(s.indexOf("=") + 1, s.length()));
        }
    }
}