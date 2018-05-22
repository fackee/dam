package org.dam.utils.config;

import org.dam.exception.PropertiesNotFoundException;
import org.dam.utils.util.StringUtil;

import java.io.*;
import java.util.*;

public class Configuration {

    private static final String confilePath = StringUtil.getConfigPath();

    private static final Map<String,String> propertiesMap = new HashMap<>(16);

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

    public static class DefaultConfig{

        private static DefaultConfig config ;

        public static DefaultConfig getInstance(){
            if(config == null){
                config = new DefaultConfig();
            }
            return config;
        }

        public String getServerName(){
            if(propertiesMap.containsKey("server")){
                return propertiesMap.get("server");
            }
            return "Dam_v_1.0.0";
        }

        public String getAppsDirectory(){
            if(propertiesMap.containsKey("www")){
                return propertiesMap.get("www");
            }
            return "www";
        }
        public String getAppSource(){
            if(propertiesMap.containsKey("appSource")){
                return propertiesMap.get("appSource");
            }
            return "appSource/";
        }

        public String getWelcome(){
            if(propertiesMap.containsKey("welCome")){
                return propertiesMap.get("welCome");
            }
            return "E:/Dam_v_1.0.0/www/index.html";
        }

        public String getLogLevel(){
            if(propertiesMap.containsKey("logLevel")){
                return propertiesMap.get("logLevel");
            }
            return "ERROR";
        }
    }
}