package org.dam.server.handler;

import org.dam.bridge.bean.ControllerBean;
import org.dam.http.HttpHeader;
import org.dam.http.HttpRequest;
import org.dam.http.constant.HttpConstant;
import org.dam.server.cache.ControllerCache;
import org.dam.bridge.bean.HandlerBean;
import org.dam.exception.AppLoadException;
import org.dam.http.Request;
import org.dam.http.Response;
import org.dam.utils.util.cache.StaticSourceMap;
import org.dam.utils.util.stream.StaticStream;
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
//    private Request request;
    public AppLoadHandler(){
//        request = new HttpRequest();
//        HttpHeader httpHeader = new HttpHeader(
//                new HttpHeader.HttpRequestHeaderBuilder()
//                        .setConnection("Keep-alive")
//                        .setUrl(new HttpHeader.URL("GET / HTTP/1.1"))
//                        .setAccept(" text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
//                        .setAccept_Encoding(" gzip, deflate, br")
//                        .setAccept_Language(" zh-CN,zh;q=0.9")
//                        .setHost(" localhost:8080")
//                        .setUser_Agent(" Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.140 Safari/537.36")
//                        );
    }

    @Override
    public boolean handle(Request baseRequest, Response baseResponse) {
        String urlPath = baseRequest.getHeader().getRequestHeader().getUrl().getRelativeUrl();
        if(urlPath == null || "".equals(urlPath) || "/ ".equals(urlPath)){
            baseResponse.setHttpHeader("statusCode", HttpConstant.HttpStatusCode.OK.getDesc());
            //TODO WELCOME RESPONSE
            throw  new AppLoadException("con not found this webapp");
        }
        try {
            String appName = "";
            if(urlPath.indexOf('/') != urlPath.lastIndexOf('/')){
                appName = urlPath.substring(urlPath.indexOf('/')+1
                        ,urlPath.indexOf('/',urlPath.indexOf('/')+1));
            }else{
                appName = urlPath.substring(urlPath.indexOf('/')+1);
            }
            String path = urlPath.replace("/"+appName,"");
            controllerBeanList = ControllerCache.getControllerByAppName(appName);
            final String redirectPrefix = appName;
            final boolean[] contain = {true};
            controllerBeanList.forEach( controllerBean -> {
                controllerBean.getHandlerBeans().forEach(handlerBean -> {
                    if(handlerBean.getPath().trim().equals(path.trim())){
                        contain[0] = false;
                        Map<String,Object> invokerMap = new HashMap<>(16);
                        if(matchParams(controllerBean,handlerBean,invokerMap,baseRequest,baseResponse)){
                            Class clazz = controllerBean.getClazz();
                            try {
                                Method method = clazz.getMethod(handlerBean.getHandlerName());
                                String result = invoke(clazz,method,invokerMap);
                                handleResult(result,baseRequest,baseResponse,method,redirectPrefix);
                            } catch (Exception e) {
                                baseResponse.setBodyBytes("<h1>invoker controller error</h1>".getBytes());
                            }
                        }else{
                            baseResponse.setBodyBytes("<h1>matching parameters error</h1>".getBytes());
                            throw new AppLoadException("matching parameters error");
                        }
                    }
                });
            });
            if(contain[0]){
                baseResponse.setBodyBytes("<h1>con not found this url path</h1>".getBytes());
                throw new AppLoadException("con not found this path");
            }
        }catch (Exception e){
            baseResponse.setBodyBytes("<h1>matching this url path error</h1>".getBytes());
            throw new AppLoadException("matching this url path error");
        }
        return true;
    }

    private boolean matchParams(ControllerBean controllerBean, HandlerBean handlerBean,Map<String,Object> invokerParam,
                                Request request,Response response){
        String httpMethod = request.getHeader().getRequestHeader().getUrl().getHttpMethod().getMethod().toString();
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

    private String invoke(Class clazz,Method method,Map<String,Object> map)  {
        Object params[] = new Object[method.getParameterCount()];
        int index = 0;
        Parameter[] parameters = method.getParameters();
        for(Parameter parameter : parameters){
            try {
                if(parameter.getType().newInstance() instanceof Request){
                    params[index++] = map.get(parameter.getName());
                    continue;
                }
                if(parameter.getType().newInstance() instanceof Response){
                    params[index++] = map.get(parameter.getName());
                    continue;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
            params[index++] = map.get(parameter.getName());
        }
        String result = null;
        try {
            result =  (String)method.invoke(clazz.newInstance());
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void handleResult(String result, Request baseRequest, Response baseResponse,Method method,String appName) {
        String resultType = "page";
        Annotation[] annotations = method.getDeclaredAnnotations();
        for(Annotation annotation : annotations){
            if(annotation instanceof org.dam.annotation.Request){
                org.dam.annotation.Request request = (org.dam.annotation.Request)annotation;
                if(request != null && request.resultType()!= null){
                    resultType = request.resultType();
                }
            }
        }

        if("page".equalsIgnoreCase(resultType)){
            String staticFilePath = "E:/Dam_v_1.0.0/www/example_0/webApp/html/Index.html";
            byte[] body = StaticStream.getBytesByFilePath(staticFilePath);
            if(body != null && body.length > 0){
                baseResponse.setBodyBytes(body);
                baseResponse.setHttpHeader(HttpConstant.HttpEntity.Content_Type.toString(),"text/html;charset=utf-8");
                baseResponse.setHttpHeader(HttpConstant.HttpEntity.Content_Encoding.toString(),"gzip");
                baseResponse.setHttpHeader("statusCode",HttpConstant.HttpStatusCode.OK.getDesc());
            }
            return;
        }else if("dynamic".equalsIgnoreCase(resultType)){

        }else if("data".equalsIgnoreCase(resultType)){

        }else if("redirect".equalsIgnoreCase(resultType)){
            baseResponse.setHttpHeader("statusCode",HttpConstant.HttpStatusCode.Moved_Temporarily.getDesc());
            baseResponse.setHttpHeader(HttpConstant.HttpResponseLine.Location.getResponseLine(),"/"+appName+result);
            return;
        }

    }
}
