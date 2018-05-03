package org.dam.http.util;

import org.dam.exception.HttpParserException;
import org.dam.io.EndPoint;
import org.dam.io.nio.buffer.Buffer;
import org.dam.io.nio.buffer.NioBuffer;
import org.dam.io.nio.buffer.ThreadLocalBuffer;
import org.dam.http.HttpField;
import org.dam.http.HttpHeader;
import org.dam.http.HttpParameter;
import org.dam.utils.util.StringUtil;

import java.io.IOException;
import java.lang.reflect.Field;

/**
 * 1判断是否读完？读完注册写事件，未读完进行具体parse
 * 2判断
 */
public class HttpParser {

    private static final String HTTP_MESSAGE_REGEX = "\r\n";
    private static final String HTTP_PARAM_RAGEX = "&";
    private static final String PROTOCAL_NAME = "HTTP/";

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
                HttpHeader.HttpRequestHeaderBuilder requestHeaderBuilder = new HttpHeader.HttpRequestHeaderBuilder();
                for (String target : lineContent) {
                    try {
                        messageParse(target, httpParameter, requestHeaderBuilder);
                    }catch (HttpParserException e){

                    }
                }
                HttpHeader httpHeader = new HttpHeader(requestHeaderBuilder);
                httpField = new HttpField.HttpBuilder()
                        .header(httpHeader)
                        .parameter(httpParameter)
                        .builde();
            }
        }
        return this;
    }

    public void messageParse(String target, HttpParameter httpParameter, HttpHeader.HttpRequestHeaderBuilder builder) {
        if (target.contains(PROTOCAL_NAME)) {
            builder.setUrl(parseUrl(target));
            if (target.contains(HttpHeader.Method.GET.toString()) && target.contains("?")) {
                String[] paramArr= target.substring(target.indexOf("?")+1,target.indexOf("HTTP/")).trim().split(HTTP_PARAM_RAGEX);
                parseHttpParameters(paramArr,httpParameter);
            }
            return;
        }
        String targetKey = target.substring(0,target.indexOf(":"));
        String targetValue = target.substring(target.indexOf(":")+1,target.length());
        Field[] requestHeaderFields = builder.getClass().getDeclaredFields();
        for(Field field : requestHeaderFields){
            if(StringUtil.httpLineEquals(field.getName(),targetKey)){
                try {
                    field.setAccessible(true);
                    field.set(builder,targetValue);
                } catch (IllegalAccessException e) {
                    throw new HttpParserException("parser http request error");
                }
            }
        }
    }
//    GET /itsenlin/article/details/53107304 HTTP/1.1
//    Host: blog.csdn.net
//    Connection: keep-alive
//    Pragma: no-cache
//    Cache-Control: no-cache
//    Upgrade-Insecure- Requests: 1
//    User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.139 Safari/537.36
//    Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8
//    Referer: https://www.baidu.com/link?url=rkDSgVXHKU_MjbLqMq0gmI0DtbGvjM86ysyvhNx0u0MRlrgBP8eg76DyT5UciE03GjopZI_5TcKKpRdWZ12aKnKm5zgyawka_YgihfBMzcG&wd=&eqid=cec6325900001879000000045aea696e
//    Accept-Encoding: gzip, deflate, br
//    Accept-Language: zh-CN,zh;q=0.9

    private HttpHeader.URL parseUrl(String targetLine){
        return new HttpHeader.URL(targetLine);
    }
    private void parseHttpParameters(String[] params,HttpParameter httpParameter){
        if(params == null || params.length == 0){
            return;
        }
        for(String param : params){
            String key = param.substring(0,param.indexOf("="));
            String value = param.substring(param.indexOf("=")+1,param.length());
            httpParameter.setParameter(key,value);
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