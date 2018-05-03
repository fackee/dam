package org.dam.utils.util.thread;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by zhujianxin on 2018/3/7.
 */
public class ThreadFactory implements java.util.concurrent.ThreadFactory{

    private final AtomicLong threadNum = new AtomicLong(0L);

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r,"select-work-event"+threadNum.getAndIncrement());
        thread.setDaemon(true);
        thread.setPriority(Thread.NORM_PRIORITY);
        return thread;
    }
}
