package org.dam.http;

import org.dam.exception.HttpParserException;
import org.dam.http.bean.*;
import org.dam.http.constant.HttpConstant;
import org.dam.http.constant.HttpMedia;
import org.dam.io.EndPoint;
import org.dam.io.nio.buffer.Buffer;
import org.dam.io.nio.buffer.NioBuffer;
import org.dam.io.nio.buffer.ThreadLocalBuffer;
import org.dam.utils.util.StringUtil;
import org.dam.utils.util.log.Logger;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 1判断是否读完？读完注册写事件，未读完进行具体parse
 * 2判断
 */
public class HttpParser {

    private static final String HTTP_MESSAGE_REGEX = "\r\n";
    private static final String HTTP_PARAM_RAGEX = "&";
    private static final String PROTOCAL_NAME = "HTTP/";

    private final ThreadLocalBuffer currentRequestBuffer = new ThreadLocalBuffer(ThreadLocalBuffer.BufferSize.REQUEST_BUFFER_SIZE.getSize());
    private static final String ISFILE = "filename=";
    private EndPoint endPoint;
    private String requestContent;
    private HttpField httpField;

    public HttpParser(EndPoint endPoint) {
        this.endPoint = endPoint;
    }

    public HttpParser parse() throws IOException{
        Logger.INFO("==========parse http message=========");
        final Buffer tempbuffer = new NioBuffer();
        endPoint.fill(tempbuffer);
        currentRequestBuffer.fillBuffer(tempbuffer);
        requestContent = new String(currentRequestBuffer.getData());
        Multipart multipart = new Multipart();
        if (requestContent != null && requestContent.length() > 0) {
            String[] lineContent = requestContent.split(HTTP_MESSAGE_REGEX);
            if (lineContent.length > 0) {
                HttpParameter httpParameter = new HttpParameter();
                HttpHeader.HttpRequestHeaderBuilder requestHeaderBuilder = new HttpHeader.HttpRequestHeaderBuilder();
                boolean isMultipart = false;
                int multipartIndex = -1;
                for (int i=0;i<lineContent.length;i++) {
                    try {
                        messageParse(lineContent,i, httpParameter, requestHeaderBuilder,multipart);
                        if(lineContent[i].contains(HttpMedia.Accept.MULTIPART_FORM_DATA.getAccept())){
                            isMultipart = true;
                        }
                        if((i+1) <= lineContent.length -1){
                            if( (lineContent[i+1] == null || lineContent[i+1].length() == 0) &&
                                    lineContent[i].contains(HttpConstant.HttpEntity.Content_Type.getEntity())){
                                multipartIndex = i + 2 <= lineContent.length-1 ? i+2 : -1;
                            }
                        }
                    }catch (HttpParserException e){

                    }
                }
                if(isMultipart){
                    if(multipartIndex != -1){
                        StringBuilder multipartData = new StringBuilder();
                        for(int j = multipartIndex;j<lineContent.length;j++){
                            multipartData.append(lineContent[j]);
                        }
                        multipart.setBytes(multipartData.toString().getBytes());
                    }
                }
                HttpHeader httpHeader = new HttpHeader(requestHeaderBuilder);
                httpField = new HttpField.HttpBuilder()
                        .header(httpHeader)
                        .parameter(httpParameter)
                        .multipart(multipart)
                        .builde();
            }
        }
        return this;
    }

    public void messageParse(String[] lineContent,int i, HttpParameter httpParameter, HttpHeader.HttpRequestHeaderBuilder builder,Multipart multipart) {
        String target = lineContent[i];
        if (target.contains(PROTOCAL_NAME)) {
            builder.setUrl(parseUrl(target));
            if (target.contains(HttpHeader.Method.GET.toString()) && target.contains("?")) {
                String[] paramArr= target.substring(target.indexOf("?")+1,target.indexOf("HTTP/")).trim().split(HTTP_PARAM_RAGEX);
                parseHttpParameters(paramArr,httpParameter);
            }
            return;
        }
        if(target.contains(":")){
            String targetKey = target.substring(0,target.indexOf(":"));
            String targetValue = target.substring(target.indexOf(":")+1,target.length());
            if(targetKey.equalsIgnoreCase(HttpConstant.HttpEntity.Content_Disposition.getEntity())){
                targetValue = targetValue.substring(targetValue.indexOf(';')+1,targetValue.length());
                if(targetValue.contains(ISFILE)){
                    String[] values = targetValue.split(";");
                    multipart.setName(values[0].split("=")[1].replace("\"",""));
                    multipart.setFileName(values[1].split("=")[1].replace("\"",""));
                }else{
                    multipart.addContentDisposition(targetValue.split("=")[1].replace("\"",""),
                            lineContent[i+2].replace("\"",""));
                }
                return;
            }
            if(targetKey.equals("cookie:")){
                String[] cookies = targetValue.split(";");
                List<Cookie> cookieList = new ArrayList<>();
                for(String cookie : cookies){
                    Cookie ck = new Cookie();
                    String name = cookie.split("=")[0];
                    String value = cookie.split("=")[1];
                    ck.setName(name);
                    ck.setValue(value);
                    cookieList.add(ck);
                }
                httpField.setCookie(cookieList);
                return;
            }
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
    }

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
}