package org.dam.utils.util.log;

import org.dam.utils.config.Configuration;
import org.dam.utils.util.StringUtil;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by geeche on 2018/5/7.
 */
public class Logger {

    private static final SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");

    private static final String LOG_LEVEL = Configuration.DefaultConfig.getInstance()
            .getLogLevel();

    private static final String LOG_LEVEL_INFO = "INFO";

    private static final String LOG_LEVEL_ERROR = "ERROR";

    public static void ERROR(String msg,Object ...params){
        String print = "["+Thread.currentThread().getName()+ "]-" +"错误-"+
                "["+format.format(new Date())+ "]" + ":" +
                StringUtil.format(msg,params);
        System.out.println(print);
        if(LOG_LEVEL.equals(LOG_LEVEL_INFO) || LOG_LEVEL.equals(LOG_LEVEL_ERROR)){
            Persistence(print);
        }
    }

    public static void INFO(String msg,Object ...params){
        String print = "["+Thread.currentThread().getName()+ "]-" +"信息-"+
                "["+format.format(new Date())+ "]" + ":" +
                StringUtil.format(msg,params);
        System.out.println(print);
        if(LOG_LEVEL.equals(LOG_LEVEL_INFO)){
            Persistence(print);
        }
    }

    public static String printStackTraceToString(Throwable throwable){
        StringWriter writer = new StringWriter();
        throwable.printStackTrace(new PrintWriter(writer,true));
        return writer.getBuffer().toString();
    }

    public static synchronized void Persistence(String info){
        long current = System.currentTimeMillis();
        long zero = current/(1000*3600*24)*(1000*3600*24) - TimeZone.getDefault().getRawOffset();
        String fileName = Configuration.DefaultConfig.getInstance()
                .getAppsDirectory().replace("www/","logs/")
                +zero + ".txt";
        File file = new File(fileName);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Writer writer = null;
        try {
            writer = new FileWriter(file,true);
            writer.write(info);
            writer.write("\n");
            writer.flush();
        }catch (IOException e){
            Logger.ERROR("Persistence logger error:{}",Logger.printStackTraceToString(e));
        }finally {
            try {
                writer.close();
            } catch (IOException e) {
                Logger.ERROR("close io error:{}",Logger.printStackTraceToString(e));
            }
        }
    }
}
