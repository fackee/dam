package org.dam.bridge;


import org.dam.bridge.classloader.ApplicationClassLoader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class LoadAppJar {

    private static final String FILE_SPERATE = "/";
    private static final String PACKAGE_LINK = ".";
    private static final String CLASS_SUFFIX = ".class";
    private static final String JAR_SUFFIX = ".jar";

    public LoadAppJar(){}
    public Map<String, List<Class>> loadApps(String appsPath) throws IOException, ClassNotFoundException {
        Map<String, List<Class>> clazzMap = new HashMap<>(16);
        File file = new File(appsPath);
        File[] files = file.listFiles();
        for (File f : files) {
            if (f.isDirectory()) {
                String everyAppName = f.getName();
                String everyAppPath = f.getAbsolutePath();
                clazzMap.putIfAbsent(everyAppName, readJar(loadJar(everyAppPath)));
            }
        }
        return clazzMap;
    }

    private JarFile loadJar(String jarFilePath) throws IOException {
        File file = new File(jarFilePath);
        if (file == null) {
            return null;
        } else if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                if (f.isFile() && f.getName().endsWith(JAR_SUFFIX)) {
                    return new JarFile(f);
                } else if (f.isDirectory()) {
                    return loadJar(f.getAbsolutePath());
                }
            }
        }
        return null;
    }

    private List<Class> readJar(JarFile jarFile) throws IOException, ClassNotFoundException {
        List<Class> result = new ArrayList<>();
        Enumeration<JarEntry> enumeration = jarFile.entries();
        while (enumeration.hasMoreElements()) {
            JarEntry entry = enumeration.nextElement();
            String className = entry.getName();
            if (className.endsWith(CLASS_SUFFIX)) {
                ApplicationClassLoader classLoader = new ApplicationClassLoader();
                result.add(classLoader.loadClass(className));
            }
        }
        return result;
    }
}