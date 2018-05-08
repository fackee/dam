package org.dam.bridge;

import org.dam.bridge.classloader.ApplicationClassLoader;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.JarFile;

/**
 * Created by zhujianxin on 2018/3/8.
 */
public class LoadAppClasses {

    private static final String FILE_SPERATE = "\\\\";
    private static final String PACKAGE_LINK = ".";
    private static final String CLASS_SUFFIX = ".class";
    private static final String PACKAGE_START = "classes";

    public LoadAppClasses() {
    }

    public Map<String, List<Class>> loadApps(String wwwPath) throws ClassNotFoundException, IOException {
        Map<String, List<Class>> clazzMap = new HashMap<>(16);
        File path = new File(wwwPath);
        if (!path.isDirectory()) {
            return clazzMap;
        }
        File[] apps = path.listFiles();
        for (File file : apps) {
            if (file.isDirectory()) {
                String appName = file.getName();
                List<Class> classes = loadClasses(file);
                if (classes != null && classes.size() > 0) {
                    clazzMap.putIfAbsent(appName, loadClasses(file));
                }
            }
        }
        return clazzMap;
    }

    private List<Class> loadClasses(File file) throws ClassNotFoundException, IOException {
        List<Class> appClasses = new ArrayList<>();
        if (file == null) {
            return appClasses;
        } else if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                if (f.isFile() && f.getName().endsWith(CLASS_SUFFIX)) {
                    String className = f.getPath();
                    className = className.substring(className.indexOf(PACKAGE_START) + 8, className.length());
                    if (className.contains("\\")) {
                        className = className.replaceAll(FILE_SPERATE, PACKAGE_LINK);
                    }
                    className = className.replaceAll(FILE_SPERATE, PACKAGE_LINK);
                    ApplicationClassLoader classLoader = new ApplicationClassLoader();
                    Class clazz = classLoader.loadClass(className);
                    appClasses.add(clazz);
                } else if (f.isDirectory()) {
                    return loadClasses(f);
                }
            }
        }
        return appClasses;
    }
}
