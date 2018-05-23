package org.dam.server.log;

import org.dam.utils.config.Configuration;
import org.dam.utils.util.thread.BasicThreadFactory;

import java.io.File;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class LoggerSchedule {

    private static final ScheduledExecutorService loggerSchedule =
            new ScheduledThreadPoolExecutor(1,new BasicThreadFactory.Builder()
                    .deamon(true).namePattern("logger-schedule-pool-%d").builde());

    private static final Runnable collection = ()->{

        long timestamp = System.currentTimeMillis();
        String path = Configuration.DefaultConfig.getInstance().getAppsDirectory()
                .replace("www/","logs/");
        File[] files = new File(path).listFiles();
        if(files.length > 0){
            for(File file : files){
                String fileName = file.getName();
                fileName = fileName.replace(".txt","");
                long fileTimestamp = Long.parseLong(fileName);
                if((timestamp - fileTimestamp)/(3600*24) >= 7){
                    file.delete();
                }
            }
        }
    };
    public static final void schedule(){
        loggerSchedule.scheduleAtFixedRate(collection,
                0,
                Configuration.DefaultConfig.getInstance().getLogPeriod()
                ,TimeUnit.HOURS);
    }
}
