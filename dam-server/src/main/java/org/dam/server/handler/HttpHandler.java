package org.dam.server.handler;

import org.dam.server.config.Configuration.*;
import org.dam.http.Request;
import org.dam.http.Response;
import org.dam.http.constant.HttpConstant;
import org.dam.http.statics.HttpMedia;
import org.dam.server.util.HttpHelper;
import org.dam.utils.util.StringUtil;
import org.dam.utils.util.log.Logger;
import org.dam.utils.util.stream.StaticStream;

import java.io.IOException;
import java.util.List;

/**
 * Created by zhujianxin on 2018/3/19.
 */
public class HttpHandler extends AbstractHandler {

    private static final String VERSION = "httpVersion";
    private static final String STATUS = "statusCode";
    public HttpHandler(){
    }

    @Override
    public boolean handle(Request baseRequest, Response baseResponse) {
        baseResponse.setHttpHeader(VERSION,baseRequest.getHeader().getRequestHeader().getUrl().getHttpVersion());
        List<String> accepts = HttpMedia.getAcceptMime();
        List<String> mimes = HttpMedia.getMimeList();
        int isStaticSource = 0;
        for(String accept : accepts){
            if(baseRequest.getHeader().getRequestHeader().getAccept().contains(accept)){
                isStaticSource++;
                break;
            }
        }
        for(String mime : mimes){
            if(baseRequest.getHeader().getRequestHeader().getUrl().getRelativeUrl().trim().endsWith(mime)){
                isStaticSource++;
                break;
            }
        }
        if(isStaticSource == 2){
            handleStaticSource(baseRequest,baseResponse);
            return false;
        }
        return true;
    }

    private void handleStaticSource(Request baseRequest, Response baseResponse) {
        baseResponse.setHttpHeader(STATUS,HttpConstant.HttpStatusCode.OK.getDesc());
        baseResponse.setHttpHeader(HttpConstant.HttpResponseLine.Server.toString(),DefaultConfig.getInstance().getServerName());
        baseResponse.setHttpHeader(HttpConstant.HttpEntity.Content_Type.toString(),HttpHelper
                .sourceCase(baseRequest.getHeader().getRequestHeader().getUrl().getRelativeUrl()).getAccept());
        try {
            String sourcePath = DefaultConfig.getInstance().getAppsDirectory()+baseRequest.getHeader().getRequestHeader()
                    .getUrl().getRelativeUrl();
            baseResponse.setBodyBytes(StaticStream.getBytesByFilePath(sourcePath));
        } catch (IOException e) {
            baseResponse.setHttpHeader(HttpConstant.HttpEntity.Content_Type.toString(),HttpMedia.Accept.TEXT_HTML.getAccept());
            baseResponse.setBodyBytes(HttpHelper.handleError(StringUtil.format("handle static source from path:\"{}\"error:{}",DefaultConfig.getInstance().getAppsDirectory()+baseRequest.getHeader().getRequestHeader()
                            .getUrl().getRelativeUrl(),
                    Logger.printStackTraceToString(e.fillInStackTrace()))));
            Logger.ERROR("handle static source from path:\"{}\"error:{}",DefaultConfig.getInstance().getAppsDirectory()+baseRequest.getHeader().getRequestHeader()
                    .getUrl().getRelativeUrl(),
                    Logger.printStackTraceToString(e.fillInStackTrace()));
        }
    }

}
