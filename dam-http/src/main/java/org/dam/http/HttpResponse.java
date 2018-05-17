package org.dam.http;

import org.dam.http.bean.Cookie;
import org.dam.http.bean.HttpHeader;
import org.dam.utils.util.log.Logger;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.dam.http.constant.HttpConstant.HTTP_MESSAGE_LINEFEED;

public class HttpResponse implements Response {

    private static final Map<String,Object> attributeMap = new ConcurrentHashMap<>();
    private final CookieManager cookieManager = new CookieManager();
    private final SessionManager sessionManager = new SessionManager();
    private HttpHeader httpHeader = new HttpHeader(new HttpHeader.HttpResponseHeaderBuilder());
    private byte[] header;
    private byte[] body;

    public HttpResponse(){

    }

    @Override
    public void generateHeaderBytes(){
        StringBuffer buffer = new StringBuffer();
        HttpHeader.HttpResponseHeader responseHeader = httpHeader.getResponseHeader();
        Field[] headers = responseHeader.getClass().getDeclaredFields();
        for(Field field : headers){
            field.setAccessible(true);
            try {
                String value = (String)field.get(responseHeader);
                if(value != null && !"".equals(value) && value.length() > 0){
                    if("statusCode".equalsIgnoreCase(field.getName())){
                        String headerLine = responseHeader.getHttpVersion() + " "+ (String) field.get(responseHeader) + HTTP_MESSAGE_LINEFEED;
                        buffer.append(headerLine);
                        continue;
                    }
                    if("httpVersion".equalsIgnoreCase(field.getName())){
                        continue;
                    }
                    String headerLine = field.getName().replaceAll("_","-") + ": " + ((String)field.get(responseHeader)) +HTTP_MESSAGE_LINEFEED;
                    buffer.append(headerLine);
                }
            } catch (IllegalAccessException e) {
                Logger.INFO("generateHeader error:{}",Logger.printStackTraceToString(e.fillInStackTrace()));
            }
        }
        Iterator<Cookie> cookieIterator = cookieManager.getCookies();
        while (cookieIterator.hasNext()){
            Cookie cookie = cookieIterator.next();
            buffer.append(cookie.toString() + HTTP_MESSAGE_LINEFEED);
        }
        buffer.append(HTTP_MESSAGE_LINEFEED);
        header = buffer.toString().getBytes();
    }

    @Override
    public byte[] getHeaderBytes() {
        return header;
    }

    @Override
    public void setBodyBytes(byte[] bodyBytes) {
        this.body = bodyBytes;
    }

    @Override
    public byte[] getBodyBytes() {
        return body;
    }

    @Override
    public void setHttpHeader(String key, String value) {
        try {
            Field field = httpHeader.getResponseHeader().getClass().getDeclaredField(key);
            field.setAccessible(true);
            field.set(httpHeader.getResponseHeader(),value);
        } catch (NoSuchFieldException e) {

        }catch (IllegalAccessException e) {

        }
    }

    @Override
    public HttpHeader getHttpHeader() {
        return httpHeader;
    }

    @Override
    public void addAttribute(String key, Object value) {
        attributeMap.put(key,value);
    }

    @Override
    public Map<String, Object> getAttribute() {
        return attributeMap;
    }

    @Override
    public Object getAttribute(String key) {
        return attributeMap.get(key);
    }

    public CookieManager getCookieManager() {
        return cookieManager;
    }

    public SessionManager getSessionManager() {
        return sessionManager;
    }
}