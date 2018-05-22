package org.dam.server.cache;

import org.dam.bridge.LoadAppClasses;
import org.dam.bridge.LoadAppJar;
import org.dam.bridge.bean.ControllerBean;
import org.dam.bridge.util.ReflectUtil;
import org.dam.utils.config.Configuration;
import org.dam.utils.util.log.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WebappsCache {

    public static final Map<String, List<ControllerBean>> webAppMap = new ConcurrentHashMap<>();

    private static final String WWW = "www";
    private final String WWW_PATH;

    public WebappsCache(Configuration config){
        WWW_PATH = config.getProperties(WWW);
    }
    public WebappsCache cacheJarApp() {
        try {
            Map<String,List<Class>> clazzMap = new LoadAppJar().loadApps(WWW_PATH);
            clazzMap.keySet().forEach(action->{
                List<Class> parsedClasses = clazzMap.get(action);
                List<ControllerBean> controllerBeans = new ArrayList<>();
                parsedClasses.forEach(subAction->{
                    ControllerBean bean = ReflectUtil.parseControllerBean(subAction);
                    if(bean != null){
                        controllerBeans.add(bean);
                    }
                });
                webAppMap.putIfAbsent(action,controllerBeans);
            });
        } catch (IOException e) {
            Logger.ERROR("load App jar error:{}",Logger.printStackTraceToString(
                    e.fillInStackTrace()
            ));
            System.exit(0);
        } catch (ClassNotFoundException e) {
            Logger.ERROR("load App jar error:{}",Logger.printStackTraceToString(
                    e.fillInStackTrace()
            ));
            System.exit(0);
        }
        return this;
    }
    public WebappsCache cacheClassesApp() {
        try {
            Map<String,List<Class>> clazzMap = new LoadAppClasses().loadApps(WWW_PATH);
            clazzMap.keySet().forEach(action->{
                List<Class> parsedClasses = clazzMap.get(action);
                List<ControllerBean> controllerBeans = new ArrayList<>();
                parsedClasses.forEach(subAction->{
                    ControllerBean controllerBean = ReflectUtil.parseControllerBean(subAction);
                    if(controllerBean != null){
                        controllerBeans.add(controllerBean);
                    }
                });
                webAppMap.putIfAbsent(action,controllerBeans);
            });
        } catch (IOException e) {
            Logger.ERROR("load App classes error:{}",Logger.printStackTraceToString(
                    e.fillInStackTrace()
            ));
            System.exit(0);
        } catch (ClassNotFoundException e) {
            Logger.ERROR("load App classes error:{}",Logger.printStackTraceToString(
                    e.fillInStackTrace()
            ));
            System.exit(0);
        }
        return this;
    }
}