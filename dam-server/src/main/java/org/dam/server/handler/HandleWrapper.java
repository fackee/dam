package org.dam.server.handler;

import org.dam.exception.AppLoadException;
import org.dam.exception.HttpProtocalException;
import org.dam.exception.PropertiesNotFoundException;
import org.dam.http.Request;
import org.dam.http.Response;
import org.dam.http.constant.HttpConstant;
import org.dam.server.Server;
import org.dam.utils.util.log.Logger;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by geeche on 2018/1/27.
 */
public class HandleWrapper extends AbstractHandler {

    private final List<Handler> handlerChain;

    public HandleWrapper(LinkedList<Handler> handlers){
        handlerChain = Collections.synchronizedList(handlers);
    }

    public HandleWrapper addDefaultHandler(){
        HttpHandler httpHandler = new HttpHandler();
        httpHandler.setServer(getServer());
        handlerChain.add(httpHandler);
        SessionHandler sessionHandler = new SessionHandler();
        sessionHandler.setServer(getServer());
        handlerChain.add(sessionHandler);
        AppLoadHandler appLoadHandler = new AppLoadHandler();
        appLoadHandler.setServer(getServer());
        handlerChain.add(appLoadHandler);
        return this;
    }

    @Override
    public boolean handle(Request request, Response response) {
        for(Handler handler : handlerChain){
            try {
                if(!handler.handle(request,response)){
                    Logger.INFO("handle-[{}] back:",handler.getClass().getName());
                    break;
                }
            }catch (AppLoadException e){
                Logger.ERROR("load app '{}' error:{}",request.getHeader().getRequestHeader()
                        .getUrl().getRelativeUrl(),
                        Logger.printStackTraceToString(e.fillInStackTrace()));
                break;
            }catch (HttpProtocalException e){
                break;
            }
        }
        if(response.getBodyBytes() != null && response.getBodyBytes().length > 0){
            response.setHttpHeader(HttpConstant.HttpEntity.Content_Length.toString(),String.valueOf(response.getBodyBytes().length));
        }
        response.generateHeaderBytes();
        return true;
    }

    @Override
    public void setServer(Server server) {
        super.setServer(server);
    }
}
