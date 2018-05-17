package org.dam.utils.util.log;

import javafx.scene.input.DataFormat;
import org.dam.utils.util.StringUtil;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by geeche on 2018/5/7.
 */
public class Logger {

    private static final SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");

    public static void ERROR(String msg,Object ...params){
        System.out.println("["+Thread.currentThread().getName()+ "]-" +"错误-"+
                "["+format.format(new Date())+ "]" + ":" +
                StringUtil.format(msg,params));
    }

    public static void INFO(String msg,Object ...params){
        System.out.println("["+Thread.currentThread().getName()+ "]-" +"信息-"+
                "["+format.format(new Date())+ "]" + ":" +
                StringUtil.format(msg,params));
    }

    public static String printStackTraceToString(Throwable throwable){
        StringWriter writer = new StringWriter();
        throwable.printStackTrace(new PrintWriter(writer,true));
        return writer.getBuffer().toString();
    }
}
