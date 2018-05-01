package com.dam.bridge.bean;

import java.util.List;
import java.util.Map;

public class ControllerBean {

    private Class clazz;

    List<HandlerBean> handlerBeans;

    public List<HandlerBean> getHandlerBeans() {
        return handlerBeans;
    }

    public void setHandlerBeans(List<HandlerBean> handlerBeans) {
        this.handlerBeans = handlerBeans;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }
}