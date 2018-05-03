package org.dam.start.load;

import org.dam.bridge.bean.ControllerBean;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LoadWebappsCache {

    public static final Map<String,List<ControllerBean>> webAppMap = new ConcurrentHashMap<>();

    public LoadWebappsCache cache(){
        return this;
    }
}