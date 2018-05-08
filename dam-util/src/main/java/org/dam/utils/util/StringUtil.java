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

    public static String format(String msg,Object ...args){
        int index = 0;
        while (msg.contains("{}")){
            if(index < args.length){
                Object arg = args[index++];
                if(arg instanceof Integer){
                    arg = String.valueOf(arg);
                }
                int startIndex = msg.indexOf("{}");
                String before = msg.substring(0,startIndex+2);
                String end = msg.substring(startIndex+2,msg.length());
                msg = before.replace("{}",arg.toString()) + end;
            }else{
                break;
            }
        }
        return msg;
    }
}
