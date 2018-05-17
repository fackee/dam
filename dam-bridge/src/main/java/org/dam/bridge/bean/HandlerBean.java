package org.dam.bridge.bean;

import java.util.Map;

public class HandlerBean {

    private String handlerName;

    private String path;

    private String method;

    private Class[] params;

    private String resultType;

    private Map<String,Object> paramsMap;

    public String getHandlerName() {
        return handlerName;
    }

    public void setHandlerName(String handlerName) {
        this.handlerName = handlerName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public Map<String,Object> getParamsMap() {
        return paramsMap;
    }

    public void setParamsMap(Map<String, Object> paramsMap) {
        this.paramsMap = paramsMap;
    }

    public void setParams(Class[] params) {
        this.params = params;
    }

    public Class[] getParams() {
        return params;
    }
}