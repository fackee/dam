package com.dam.util.thread;

import com.dam.connector.nio.buffer.ThreadLocalBuffer;

/**
 * Created by zhujianxin on 2018/3/7.
 */
public class ReadableTask implements Task {

    private final ThreadLocalBuffer threadLocalBuffer;


    public ReadableTask(final ThreadLocalBuffer localBuffer){
        this.threadLocalBuffer = localBuffer;
    }

    @Override
    public void decode() {

    }

    @Override
    public void encode(){

    }


    @Override
    public void run() {

    }
}
