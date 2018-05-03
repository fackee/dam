package org.dam.utils.util.thread;

/**
 * Created by zhujianxin on 2018/3/7.
 */
public interface Task extends Runnable{

    void decode();

    void encode();
}
