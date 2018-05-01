package com.dam.handler;

import com.dam.bridge.bean.ControllerBean;
import com.dam.bridge.bean.HandlerBean;
import com.dam.bridge.cache.ControllerCache;
import com.dam.exception.AppLoadException;
import com.dam.http.Request;
import com.dam.http.Response;
import com.dam.util.stream.StaticStream;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by geeche on 2018/1/27.
 */
public class AppLoadHandler extends AbstractHandler {

    private List<ControllerBean> controllerBeanList;

    public AppLoadHandler(){
    }

    @Override
    public boolean handle(Request baseRequest, Response baseResponse) {
        String urlPath = baseRequest.getHeader().getUrl().getRelativeURL();
        if(urlPath == null || urlPath.equals("")){
            baseResponse.setContent("<h1>welcome to toylet server</h1>".getBytes());
            throw  new AppLoadException("con not found this webapp");
        }
        try {
            String appName = urlPath.substring(0,urlPath.indexOf('/'));
            String path = urlPath.substring(urlPath.indexOf('/'),urlPath.length());
            controllerBeanList = ControllerCache.getControllerByAppName(appName);
            final boolean[] contain = {true};
            controllerBeanList.forEach( controllerBean -> {
                controllerBean.getHandlerBeans().forEach(handlerBean -> {
                    if(handlerBean.getPath().equals(path)){
                        contain[0] = false;
                        Map<String,Object> invokerMap = new HashMap<>(16);
                        if(matchParams(controllerBean,handlerBean,invokerMap,baseRequest,baseResponse)){
                            Class clazz = controllerBean.getClazz();
                            try {
                                Method method = clazz.getMethod(handlerBean.getHandlerName());
                                String result = invoke(clazz,method,invokerMap);
                                handleResult(result,baseRequest,baseResponse,method);
                            } catch (Exception e) {
                                baseResponse.setContent("<h1>invoker controller error</h1>".getBytes());
                            }
                        }else{
                            baseResponse.setContent("<h1>matching parameters error</h1>".getBytes());
                            throw new AppLoadException("matching parameters error");
                        }
                    }
                });
            });
            if(contain[0]){
                baseResponse.setContent("<h1>con not found this url path</h1>".getBytes());
                throw new AppLoadException("con not found this path");
            }
        }catch (Exception e){
            baseResponse.setContent("<h1>matching this url path error</h1>".getBytes());
            throw new AppLoadException("matching this url path error");
        }
        return true;
    }

    private boolean matchParams(ControllerBean controllerBean, HandlerBean handlerBean,Map<String,Object> invokerParam,
                                Request request,Response response){
        String httpMethod = request.getHeader().getHttpMethod().getMethod().toString();
        if(!handlerBean.getMethod().equalsIgnoreCase(httpMethod)){
            return false;
        }
        Map<String,Map<String,String>> paramsMap = handlerBean.getParamsMap();
        Iterator<String> iterator = paramsMap.keySet().iterator();
        while (iterator.hasNext()){
            String paramsKey = iterator.next();
            if(paramsKey.equalsIgnoreCase("Request")){
                invokerParam.put(paramsKey,request);
                continue;
            }
            if(paramsKey.equalsIgnoreCase("Response")){
                invokerParam.put(paramsKey,response);
                continue;
            }
            Map<String,String> defaultAnnotationKeyValueMap = paramsMap.get(paramsKey);
            String annotationKey = (String) defaultAnnotationKeyValueMap.keySet().toArray()[0];
            if(request.getHttpParameter().getParameter(annotationKey) != null){
                invokerParam.put(paramsKey,request.getHttpParameter().getParameter(paramsKey));
                continue;
            }else{
                invokerParam.put(paramsKey,defaultAnnotationKeyValueMap.get(annotationKey));
            }
        }
        return true;
    }

    private String invoke(Class clazz,Method method,Map<String,Object> map) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Object params[] = new Object[method.getParameterCount()];
        int index = 0;
        Parameter[] parameters = method.getParameters();
        for(Parameter parameter : parameters){
            if(parameter.getType().newInstance() instanceof Request){
                params[index++] = map.get(parameter.getName());
                continue;
            }
            if(parameter.getType().newInstance() instanceof Response){
                params[index++] = map.get(parameter.getName());
                continue;
            }
            params[index++] = map.get(parameter.getName());
        }
        String result = (String)method.invoke(clazz.newInstance(),params);
        return result;
    }

    private void handleResult(String result, Request baseRequest, Response baseResponse,Method method) {
        String resultType = "page";
        Annotation[] annotations = method.getDeclaredAnnotations();
        for(Annotation annotation : annotations){
            if(annotation instanceof com.toylet.annotation.Request){
                com.toylet.annotation.Request request = (com.toylet.annotation.Request)annotation;
                if(request != null && request.resultType()!= null){
                    resultType = request.resultType();
                }
            }
        }
        if(resultType.equalsIgnoreCase("staticPage")){
            baseResponse.setContent(StaticStream.getBytesByFilePath(result));
        }else if(result.equalsIgnoreCase("dynamicPage")){

        }else if(result.equalsIgnoreCase("")){

        }

    }

}
