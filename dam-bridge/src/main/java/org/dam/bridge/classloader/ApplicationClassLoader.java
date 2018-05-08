package org.dam.bridge.classloader;

import sun.dc.pr.PRError;

import java.io.*;

/**
 * Created by rocky on 2018/3/6.
 */
public class ApplicationClassLoader extends ClassLoader{

    private static final String CLASS_SUFFIX = ".class";
    private static final String CLASS_LINK = ".";
    private static final String PACKAGE_LINK = "/";

    public ApplicationClassLoader(){

    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String path = "E:/Dam_v_1.0.0/www/example_0/classes/";
        if(name.endsWith(CLASS_SUFFIX)){
            String subPath = name.substring(0,name.lastIndexOf(CLASS_SUFFIX));
            subPath = subPath.replace(CLASS_LINK,PACKAGE_LINK);
            String className = name.substring(name.lastIndexOf(CLASS_SUFFIX),name.length());
            path += subPath + className;
        }else{
            path += name.replace(CLASS_LINK,PACKAGE_LINK) + CLASS_SUFFIX;
        }
        byte[] clazzBytes = getClazzBytes(path);
        String className = getClassName(name);
        className = className.replace(".class","");
        return super.defineClass(className,clazzBytes,0,clazzBytes.length);
    }

    private String getClassName(String name){
        int index = name.lastIndexOf('.');
        if(index == -1){
            return name + CLASS_SUFFIX;
        }
        return name;
    }

    private byte[] getClazzBytes(String path){
        byte[] buffer = null;
        File file = new File(path);
        if(!file.isFile()){
            return buffer;
        }
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = new FileInputStream(file);
            outputStream = new ByteArrayOutputStream();
            buffer = new byte[inputStream.available()];
            int read = 0;
            while ((read = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return buffer;
    }
}
