package com.dam.bridge.classloader;

/**
 * Created by rocky on 2018/3/6.
 */
public class TsperClassLoader extends ClassLoader{

    private static final String TSPSUFFIX = ".tsp";

    private String tspPath;

    public TsperClassLoader(String tspPath){
        this.tspPath = tspPath;
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {

        return super.loadClass(name);
    }
}
