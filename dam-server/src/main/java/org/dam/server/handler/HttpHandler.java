package org.dam.server.handler;

import org.dam.server.config.Configuration.*;
import org.dam.http.Request;
import org.dam.http.Response;
import org.dam.http.constant.HttpMedia;
import org.dam.utils.util.log.Logger;

import java.util.List;

import static org.dam.constant.Constants.*;

/**
 * Created by zhujianxin on 2018/3/19.
 */
public class HttpHandler extends AbstractHandler {

    public HttpHandler(){
    }

    @Override
    public boolean handle(Request baseRequest, Response baseResponse) {
        Logger.INFO("request target url:{}",baseRequest.getHeader().getRequestHeader()
                .getUrl().getRelativeUrl());
        List<String> accepts = HttpMedia.getAcceptMime();
        List<String> mimes = HttpMedia.getMimeList();
        int isStaticSource = 0;
        for(String accept : accepts){
            if(baseRequest.getHeader().getRequestHeader().getAccept().contains(accept)){
                isStaticSource++;
                break;
            }
        }
        if(ANY_ACCEPT.equals(baseRequest.getHeader().getRequestHeader().getAccept().trim())){
            isStaticSource++;
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
        Logger.INFO("==================HttpHandle execute with staticSource=================");
        final HandleWrapper.HandleResult handleResult = getHandleResult();
        handleResult.setResultType(RESULT_TYPE_STATIC);
        handleResult.setExtend(DefaultConfig.getInstance().getAppsDirectory()+baseRequest.getHeader().getRequestHeader().getUrl().getRelativeUrl());
    }

}
