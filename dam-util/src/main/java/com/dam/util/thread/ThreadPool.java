package com.dam.util.thread;

/**
 * Created by zhujianxin on 2018/3/7.
 */
public interface ThreadPool{

    public boolean dispatch(Runnable job);

    public void join() throws InterruptedException;

    public int getThreads();

    public int getIdleThreads();

    public void stop();


}
