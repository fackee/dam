package org.dam.utils.util.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class StaticSourceMap {

    private final Map<String,byte[]> cache = new HashMap<>(16);
    private final Lock lock = new ReentrantLock();
    private final ScheduledExecutorService gcService;

    private final Runnable gc = new Runnable() {
        @Override
        public void run() {
            gcHandle();
        }
    };

    public StaticSourceMap(){
        gcService = Executors.newScheduledThreadPool(1, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread();
                thread.setName("staticSource gc thread");
                thread.setDaemon(true);
                return thread;
            }
        });
        gcService.schedule(gc,5,TimeUnit.MINUTES);
    }

    public void put(String key, byte[] data){
        try {
            lock.lock();
            if(cache.containsKey(key)){
                cache.remove(key);
                cache.putIfAbsent(key,data);
            }else{
                cache.putIfAbsent(key,data);
            }
        }finally {
            lock.unlock();
        }
    }

    public void remove(String key, byte[] data){
        try {
            lock.lock();
            if(cache.containsKey(key)){
                cache.remove(key);
            }
        }finally {
            lock.unlock();
        }
    }

    public byte[] get(String key){
        try {
            lock.lock();
            if(cache.containsKey(key)){
                return cache.get(key);
            }else{
                return null;
            }
        }finally {
            lock.unlock();
        }
    }

    private void gcHandle() {
        try {
            lock.lock();
            cache.clear();
        }finally {
            lock.unlock();
        }
    }
}