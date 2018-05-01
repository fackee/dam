package com.dam.bridge;


import com.dam.bridge.classloader.ApplicationClassLoader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class LoadAppJar {

    private static final String FILE_SPERATE = "/";
    private static final String PACKAGE_LINK = ".";

    private static final String CLASS_SUFFIX = ".class";

    private static final String JAR_SUFFIX = ".jar";
    /**
    * key->appName;value->controllers
    */
    private static final Map<String,List<Class>> clazzMap = new ConcurrentHashMap<>(16);

    public static void loadApps(String appsPath)throws IOException{
        File file = new File(appsPath);
        File[] files = file.listFiles();
        for(File f : files){
            if(f.isDirectory()){
                String everyAppName = f.getName();
                String everyAppPath = f.getAbsolutePath();
                clazzMap.putIfAbsent(everyAppName,readJar(loadJar(everyAppPath)));
            }
        }
    }
    public static JarFile loadJar(String jarFilePath) throws IOException {
        File file = new File(jarFilePath);
        if (file == null) {
            return null;
        } else if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                if(f.isFile() && f.getName().endsWith(JAR_SUFFIX)){
                    return new JarFile(f);
                }else if(f.isDirectory()){
                    return loadJar(f.getAbsolutePath());
                }
            }
        }
        return null;
    }

    public static List<Class> readJar(JarFile jarFile) {
        List<Class> result = new ArrayList<>();
        Enumeration<JarEntry> enumeration = jarFile.entries();
        while (enumeration.hasMoreElements()) {
            JarEntry entry = enumeration.nextElement();
            String className = entry.getName();
            if (className.endsWith(CLASS_SUFFIX)) {
                InputStream inputStream = null;
                ByteArrayOutputStream outputStream = null;
                try {
                    className = className.replaceAll(FILE_SPERATE,PACKAGE_LINK);
                    inputStream = jarFile.getInputStream(entry);
                    outputStream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[inputStream.available()];
                    int read = 0;
                    while ((read = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, read);
                    }
                    ApplicationClassLoader classLoader = new ApplicationClassLoader(buffer);
                    result.add(classLoader.loadClass(className));
                } catch (IOException e) {

                } catch (ClassNotFoundException e) {

                }finally {
                    try {
                        inputStream.close();
                        outputStream.close();
                    } catch (IOException e) {

                    }
                }
            }
        }
        return result;
    }

    public static Map<String, List<Class>> getClazzMap() {
        return clazzMap;
    }
}