package org.dam.server.config;

import org.dam.exception.PropertiesNotFoundException;

import java.io.*;
import java.util.*;

public class Configuration {

    private static final String confilePath = "C:/Users/geeche/IdeaProjects/dam/config/config.properties";

    private final Map<String,String> propertiesMap = new HashMap<>(16);

    public Configuration(){}

    public Configuration config() throws FileNotFoundException {
        File configFile = new File(confilePath);
        if(!configFile.isFile()){
            throw new FileNotFoundException();
        }
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(configFile);
            Properties properties = new Properties();
            properties.load(inputStream);
            Enumeration enumeration = properties.propertyNames();
            while (enumeration.hasMoreElements()){
                String key = (String) enumeration.nextElement();
                String value = properties.getProperty(key);
                propertiesMap.putIfAbsent(key,value);
            }
        } catch (IOException e){

        }finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return this;
    }
    public String getProperties(String key){
        if(!propertiesMap.containsKey(key)){
            throw new PropertiesNotFoundException("cannot found properties by key:" + key);
        }
        synchronized (propertiesMap){
            return propertiesMap.get(key);
        }
    }

    public Iterator getPropertiesKeyIterator(){
        return propertiesMap.keySet().iterator();
    }
}