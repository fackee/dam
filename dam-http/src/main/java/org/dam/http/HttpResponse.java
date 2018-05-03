package org.dam.http;

import org.dam.utils.util.serialize.Serialize;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HttpResponse implements Response {

    private static final Map<String,Object> attributeMap = new ConcurrentHashMap<>();
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
                if("statusCode".equalsIgnoreCase(field.getName())){
                    String headerLine = responseHeader.getHttpVersion() + " "+ (String) field.get(responseHeader) + "\r\n";
                    buffer.append(headerLine);
                    continue;
                }
                if("httpVersion".equalsIgnoreCase(field.getName())){
                    continue;
                }
                String headerLine = field.getName().replaceAll("_","-") + ": " + ((String)field.get(responseHeader)) +"\r\n";
                buffer.append(headerLine);
            } catch (IllegalAccessException e) {

            }
        }
        buffer.append("\r\n");
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

    }

    @Override
    public Object addAttribute(String key) {
        return null;
    }
}