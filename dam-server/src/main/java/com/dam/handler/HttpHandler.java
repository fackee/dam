package com.dam.handler;

import com.dam.exception.HttpProtocalException;
import com.dam.http.HttpHeader;
import com.dam.http.HttpResponse;
import com.dam.http.Request;
import com.dam.http.Response;
import com.dam.http.statics.HttpMedia;
import com.dam.util.cache.StaticSourceMap;
import com.dam.util.stream.StaticStream;

import java.util.List;

/**
 * Created by zhujianxin on 2018/3/19.
 */
public class HttpHandler extends AbstractHandler {

    public HttpHandler(){
    }

    @Override
    public boolean handle(Request baseRequest, Response baseResponse) {
        List<String> accepts = HttpMedia.getAcceptMime();
        List<String> mimes = HttpMedia.getMimeList();
        int isStaticSource = 0;
        for(String accept : accepts){
            if(baseRequest.getHeader().getAccept().contains(accept)){
                isStaticSource++;
                break;
            }
        }
        for(String mime : mimes){
            if(baseRequest.getHeader().getUrl().getAbsoluteURL().trim().endsWith(mime)){
                isStaticSource++;
                break;
            }
        }
        if(isStaticSource == 2){
            byte[] bytes = StaticStream.getBytesByFilePath("F:/HBuilderProject/H5/Main.html");
            int contentLength = bytes.length;
            String responseHeader = "HTTP/1.1 200 OK\r\n" +
                    "Content-Length: "+contentLength+"\r\n" +
                    "Content-Type: text/html\r\n" +
                    "\r\n" +
                    "\n";
            String response = "HTTP/1.1 200 OK\r\n" +
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
            int allLength = bytes.length + responseHeader.getBytes().length;
            byte[] result = new byte[allLength];
            int index = 0;
            int j = 0;
            while (index<allLength) {
                if (index < responseHeader.getBytes().length) {
                    result[index] = responseHeader.getBytes()[index];
                } else {
                    result[index] = bytes[j++];
                }
                index++;
            }
            baseResponse.setContent(result);
            return false;
        }
        return true;
    }

    private void buildSimpleHttpReponseMessage(HttpResponse baseResponse){
        HttpHeader httpHeader;
    }
}
