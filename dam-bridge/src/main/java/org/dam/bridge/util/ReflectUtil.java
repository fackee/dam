package org.dam.bridge.util;

import org.dam.bridge.bean.ControllerBean;
import org.dam.bridge.bean.HandlerBean;
import org.dam.annotation.Parameter;
import org.dam.annotation.Request;
import org.dam.annotation.Toylet;
import org.dam.http.Response;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReflectUtil {


    public static ControllerBean parseControllerBean(Class clazz){
        final ControllerBean controllerBean = new ControllerBean();
        controllerBean.setClazz(clazz);
        Annotation[] annotations = clazz.getDeclaredAnnotations();
        for(Annotation annotation : annotations){
            if(annotation instanceof Toylet){
                List<HandlerBean> handlerBeanList = new ArrayList<>();
                Method[] methods = clazz.getDeclaredMethods();
                for(Method method : methods){
                    handlerBeanList.add(parseHandlerBean(method));
                }
            }
        }
        return controllerBean;
    }

    public static final HandlerBean parseHandlerBean(Method method){
        Map<String,List<String>> result = new HashMap<>(1,1);
        final HandlerBean handlerBean = new HandlerBean();
        handlerBean.setHandlerName(method.getName());
        Annotation[] annotations = method.getDeclaredAnnotations();
        for(Annotation annotation : annotations){
            if(annotation instanceof Request){
                Request request = (Request) annotation;
                handlerBean.setPath(request.url());
                handlerBean.setMethod(request.method());
                handlerBean.setResultType(request.resultType());
            }
        }
        handlerBean.setParamsMap(getMethodParamsAnnotationValue(method));
        return handlerBean;
    }

    private static final  Map<String,Map<String,String>> getMethodParamsAnnotationValue(Method method){
        Map<String,Map<String,String>> result = new HashMap<>(16);
        java.lang.reflect.Parameter[] parameters = method.getParameters();
        for(java.lang.reflect.Parameter parameter : parameters){
            Annotation[] annotations = parameter.getDeclaredAnnotations();
            for(Annotation annotation : annotations){
                if(annotation instanceof Parameter){
                    Map<String,String> annParamMap = new HashMap<>(1,1);
                    Parameter param = (Parameter)annotation;
                    annParamMap.put(param.paramName(),param.defaultValue());
                    result.put(parameter.getName(),annParamMap);
                }
            }
            try {
                if(parameter.getType().newInstance() instanceof org.dam.http.Request){
                    result.put("Request",new HashMap<>(0));
                }
                if(parameter.getType().newInstance() instanceof Response){
                    result.put("Response",new HashMap<>(0));
                }
            } catch (InstantiationException e) {

            } catch (IllegalAccessException e) {

            }
        }
        return result;
    }
}