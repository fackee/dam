package org.dam.server.handler;

import org.dam.bridge.bean.ControllerBean;
import org.dam.exception.ClientException;
import org.dam.exception.ServerException;
import org.dam.http.*;
import org.dam.http.constant.HttpConstant;
import org.dam.server.cache.ControllerCache;
import org.dam.bridge.bean.HandlerBean;
import org.dam.server.config.Configuration;
import org.dam.utils.util.StringUtil;
import org.dam.utils.util.log.Logger;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.dam.constant.Constants.*;

/**
 * Created by geeche on 2018/1/27.
 */
public class AppLoadHandler extends AbstractHandler {

    private List<ControllerBean> controllerBeanList;
    private static final String URL_SPLITE = "/";
    public static final String EXTEND = "welcome";

    public AppLoadHandler() {
    }

    @Override
    public boolean handle(Request baseRequest, Response baseResponse) {
        String urlPath = baseRequest.getHeader().getRequestHeader().getUrl().getRelativeUrl();
        final HandleWrapper.HandleResult handleResult = getHandleResult();
        if (urlPath == null || "".equals(urlPath) || "/ ".equals(urlPath)) {
            handleResult.setResultStatus(HttpConstant.HttpStatusCode.OK.getDesc());
            handleResult.setResultType(RESULT_TYPE_STATIC);
            handleResult.setExtend(EXTEND);
            return true;
        }
        try {
            String appName = "";
            if (urlPath.indexOf('/') != urlPath.lastIndexOf('/')) {
                appName = urlPath.substring(urlPath.indexOf('/') + 1
                        , urlPath.indexOf('/', urlPath.indexOf('/') + 1));
            } else {
                appName = urlPath.substring(urlPath.indexOf('/') + 1);
            }
            Logger.INFO("ApploadHandle execute with app:{}", appName);
            String path = urlPath.replace("/" + appName, "");
            if (path.indexOf('?') != -1) {
                path = path.substring(0, path.indexOf('?'));
            }
            try {
                controllerBeanList = ControllerCache.getControllerByAppName(appName);
            } catch (IllegalArgumentException e) {
                Logger.ERROR("cannot found this app:{},caused by:{}", appName, Logger
                        .printStackTraceToString(e.fillInStackTrace()));
                throw  new ClientException(StringUtil.format("cannot found this app:{},caused by:{}", appName, Logger
                        .printStackTraceToString(e.fillInStackTrace())));
            }
            final String redirectPrefix = appName;
            boolean contain = false;
            for (ControllerBean controllerBean : controllerBeanList) {
                for (HandlerBean handlerBean : controllerBean.getHandlerBeans()) {
                    if(handlerBean.getPath().trim().equalsIgnoreCase(path.trim())){
                        Map<String, Object> invokerMap = new HashMap<>(16);
                        if (matchParams(controllerBean, handlerBean, invokerMap, baseRequest, baseResponse)) {
                            Class clazz = controllerBean.getClazz();
                            try {
                                Method method = null;
                                if (handlerBean.getParams() != null && handlerBean.getParams().length > 0) {
                                    method = clazz.getMethod(handlerBean.getHandlerName(), handlerBean.getParams());
                                } else {
                                    method = clazz.getMethod(handlerBean.getHandlerName());
                                }
                                Object result = invoke(clazz, method, invokerMap);
                                Logger.INFO("invoker app return result:{}", result);
                                handleResult(handleResult, result, baseRequest, baseResponse, method, redirectPrefix);
                            } catch (Exception e) {
                                Logger.ERROR("execute app controller error:{}", Logger
                                        .printStackTraceToString(e.fillInStackTrace()));
                                throw new ServerException(StringUtil.format("execute app controller error:{}", Logger
                                        .printStackTraceToString(e.fillInStackTrace())));
                            }
                            contain = true;
                            break;
                        } else {
                            throw new ClientException("matching parameters error");
                        }
                    }
                }
            }
            if (!contain) {
                throw new ClientException("con not found this path");
            }
        } catch (Exception e) {
            throw new ClientException("matching this url path error");
        }
        return true;
    }

    private boolean matchParams(ControllerBean controllerBean, HandlerBean handlerBean, Map<String, Object> invokerParam,
                                Request request, Response response) {
        String httpMethod = request.getHeader().getRequestHeader().getUrl().getHttpMethod().getMethod().toString();
        if (!handlerBean.getMethod().equalsIgnoreCase(httpMethod)) {
            return false;
        }
        Map<String, Object> paramsMap = handlerBean.getParamsMap();
        Iterator<String> iterator = paramsMap.keySet().iterator();
        while (iterator.hasNext()) {
            String paramsKey = iterator.next();
            if (!(paramsMap.get(paramsKey) instanceof Map)) {
                String obj = (String) paramsMap.get(paramsKey);
                if ("Request".equals(obj)) {
                    invokerParam.put(paramsKey, request);
                }
                if ("Response".equals(obj)) {
                    invokerParam.put(paramsKey, response);
                }
                if("Multipart".equals(obj)){
                    invokerParam.put(paramsKey,request.getMultipart());
                }
                continue;
            }
            Map<String, String> defaultAnnotationKeyValueMap = (Map<String, String>) paramsMap.get(paramsKey);
            String annotationKey = (String) defaultAnnotationKeyValueMap.keySet().toArray()[0];
            if (request.getHttpParameter().getParameter(annotationKey) != null) {
                invokerParam.put(paramsKey, request.getHttpParameter().getParameter(annotationKey));
                continue;
            } else {
                invokerParam.put(paramsKey, defaultAnnotationKeyValueMap.get(annotationKey));
            }
        }
        return true;
    }

    private Object invoke(Class clazz, Method method, Map<String, Object> map) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Object params[] = new Object[method.getParameterCount()];
        int index = 0;
        Parameter[] parameters = method.getParameters();
        for (Parameter parameter : parameters) {
            params[index++] = map.get(parameter.getName());
        }
        if (params.length > 0) {
            return method.invoke(clazz.newInstance(), params);
        }
        return method.invoke(clazz.newInstance());
    }

    private void handleResult(HandleWrapper.HandleResult handleResult,Object result,
                              Request baseRequest, Response baseResponse,
                              Method method, String appName) {
        String resultType = null;
        Annotation[] annotations = method.getDeclaredAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation instanceof org.dam.annotation.Request) {
                org.dam.annotation.Request request = (org.dam.annotation.Request) annotation;
                if (request != null && request.resultType() != null) {
                    resultType = request.resultType();
                }
            }
        }
        if (RESULT_TYPE_PAGE.equalsIgnoreCase(resultType)) {
            String staticFilePath = Configuration.DefaultConfig.getInstance().getAppsDirectory()
                    + appName + Configuration.DefaultConfig.getInstance().getAppSource()
                    + result;
            handleResult.setResultType(RESULT_TYPE_STATIC);
            handleResult.setExtend(staticFilePath);
            return;
        } else if (RESULT_TYPE_DYNAMIC.equalsIgnoreCase(resultType)) {
            String dynamicFilePath = Configuration.DefaultConfig.getInstance().getAppsDirectory()
                    + appName + Configuration.DefaultConfig.getInstance().getAppSource()
                    + result;
            handleResult.setResultType(RESULT_TYPE_DYNAMIC);
            handleResult.setExtend(dynamicFilePath);
            return;
        } else if (RESULT_TYPE_JSON.equalsIgnoreCase(resultType)) {
            handleResult.setResultType(RESULT_TYPE_JSON);
            handleResult.setExtend(result);
            return;
        } else if (RESULT_TYPE_REDIRECT.equalsIgnoreCase(resultType)) {
            handleResult.setResultType(RESULT_TYPE_REDIRECT);
            String redirectLocation = URL_SPLITE + appName + result;
            handleResult.setExtend(redirectLocation);
            return;
        } else if(RESULT_TYPE_FILE.equalsIgnoreCase(resultType)){
            handleResult.setResultType(RESULT_TYPE_FILE);
            handleResult.setExtend(result);
            return;
        } else {
            throw new ServerException(StringUtil.format("app result type error:{}",resultType));
        }
    }
}
