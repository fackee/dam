package org.dam.utils.util;

/**
 * Created by zhujianxin on 2018/3/7.
 */
public class StringUtil {

    public static boolean httpLineEquals(String server,String client){
        char[] serverArr = server.toCharArray();
        char[] clientArr = client.toCharArray();
        boolean isEquals = true;
        if(server.length() != client.length()){
            return false;
        }
        for(int i=0;i<serverArr.length;i++){
            if(serverArr[i] != '_' && clientArr[i] != '-' && serverArr[i] != clientArr[i]){
                isEquals = false;
            }
        }
        return isEquals;
    }
}
