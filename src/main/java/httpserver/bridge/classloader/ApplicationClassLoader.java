package httpserver.bridge.classloader;

import java.io.*;
import java.nio.charset.Charset;

/**
 * Created by rocky on 2018/3/6.
 */
public class ApplicationClassLoader extends ClassLoader{

    private byte[] clazzBytes;

    private static final String CLASS_SUFFIX = ".class";

    public ApplicationClassLoader(byte[] clazzBytes){
        this.clazzBytes = clazzBytes;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String className = getClassName(name);
        return super.defineClass(name,clazzBytes,0,clazzBytes.length);
    }

    private String getClassName(String name){
        int index = name.lastIndexOf('.');
        if(index == -1){
            return name + CLASS_SUFFIX;
        }
        return name;
    }
}
