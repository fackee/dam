package org.dam.server.handler;

import org.dam.http.HttpHeader;
import org.dam.http.HttpResponse;
import org.dam.http.Request;
import org.dam.http.Response;
import org.dam.http.constant.HttpConstant;
import org.dam.http.statics.HttpMedia;
import org.dam.server.Server;
import org.dam.utils.util.cache.StaticSourceMap;
import org.dam.utils.util.stream.StaticStream;

import java.util.List;

/**
 * Created by zhujianxin on 2018/3/19.
 */
public class HttpHandler extends AbstractHandler {

    private static final String SERVER_NAME = "Server";
    private static final String VERSION = "httpVersion";

    public HttpHandler(){
    }

    @Override
    public boolean handle(Request baseRequest, Response baseResponse) {
        baseResponse.setHttpHeader(VERSION,baseRequest.getHeader().getRequestHeader().getUrl().getHttpVersion());
//       baseResponse.setHttpHeader("statusCode", HttpConstant.HttpStatusCode.Internal_Server_Error.getDesc());
        baseResponse.setHttpHeader(SERVER_NAME,getServer().getConfiguration().getProperties(SERVER_NAME.toLowerCase()));
//        baseResponse.setHttpHeader("Content_Length","5000");
//        baseResponse.setBodyBytes("<html><head></head><body><h1>Hello Dam</h1></body></html>".getBytes());
        return true;
//        List<String> accepts = HttpMedia.getAcceptMime();
//        List<String> mimes = HttpMedia.getMimeList();
//        int isStaticSource = 0;
//        for(String accept : accepts){
//            if(baseRequest.getHeader().getRequestHeader().getAccept().contains(accept)){
//                isStaticSource++;
//                break;
//            }
//        }
//        for(String mime : mimes){
//            if(baseRequest.getHeader().getRequestHeader().getUrl().getRelativeUrl().trim().endsWith(mime)){
//                isStaticSource++;
//                break;
//            }
//        }
//        if(isStaticSource == 2){
//            handleStaticSource(baseRequest,baseResponse);
//            return false;
//        }
//        return true;
    }

    private void handleStaticSource(Request baseRequest, Response baseResponse) {


    }

}
