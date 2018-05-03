package org.dam.server.handler;

import org.dam.http.Request;
import org.dam.http.Response;

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
        addDefaultHandler();
    }

    private void addDefaultHandler(){
        handlerChain.add(new HttpHandler());
        handlerChain.add(new SessionHandler());
        handlerChain.add(new AppLoadHandler());
    }

    @Override
    public boolean handle(Request request, Response response) {
        for(Handler handler : handlerChain){
            try {
                if(!handler.handle(request,response)){
                    break;
                }
            }catch (Exception e){
                break;
            }
        }
        response.setHttpHeader("Content_Length",setContentLength(response));
        response.generateHeaderBytes();
        return true;
    }

    private String setContentLength(Response response) {
        int length = 0;
        Field[] declaredFields = response.getHttpHeader().getResponseHeader().getClass().getDeclaredFields();
        for(Field field : declaredFields){
            field.setAccessible(true);
            try {
                String value = (String) field.get(response.getHttpHeader().getResponseHeader());
                if(value != null){
                    length += value.length();
                }
            } catch (IllegalAccessException e) {

            }
        }
        length += response.getBodyBytes().length;
        return String.valueOf(length + "Content-Length : 10000".length() + 4);
    }
}
