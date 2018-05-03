package org.dam.utils.util.thread;


import org.dam.utils.lifecycle.AbstractLifeCycle;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by zhujianxin on 2018/3/9.
 */
public class QueueThreadPool extends AbstractLifeCycle implements ThreadPool,Executor{


    private final AtomicInteger startedThreadNum = new AtomicInteger(0);

    private final AtomicInteger idleThreadNum = new AtomicInteger(0);

    private final AtomicLong lastShrink = new AtomicLong();

    private final ConcurrentLinkedQueue<Thread> threads = new ConcurrentLinkedQueue<Thread>();

    private Object joinLock = new Object();

    private BlockingQueue<Runnable> jobQueue;

    private int idleTimeout;

    private String name;

    private int maxThreadNum;

    private int minThreadNum ;

    private int maxStopTime;

    private int maxQueueSize;

    private boolean deamon ;

    private int priority;

    private Runnable job;

    private final Runnable runnable = ()->{
        boolean shrink = false;
        try{
            Runnable job = jobQueue.poll();
            while (isRunning()) {

                while (job != null && isRunning()) {
                    runJob(job);
                    job = jobQueue.poll();
                }

                try{
                idleThreadNum.incrementAndGet();
                while (isRunning() && job == null) {
                    if (idleTimeout <= 0) {
                        job = jobQueue.take();
                    } else {
                        final int size = startedThreadNum.get();
                        if (size > minThreadNum) {
                            long last = lastShrink.get();
                            long now = System.currentTimeMillis();
                            if (last == 0 || (now - last) > idleTimeout) {
                                shrink = lastShrink.compareAndSet(size, size + 1);
                                startedThreadNum.compareAndSet(size, size - 1);
                                if (shrink) {
                                    return;
                                }
                            }
                        }
                        job = idleJobPoll();
                    }
                }
            }finally {
                   idleThreadNum.decrementAndGet();
                }
            }
        }catch (InterruptedException e){

        }catch (Exception e){

        }finally {
            if(!shrink){
                startedThreadNum.decrementAndGet();
            }
            threads.remove(Thread.currentThread());
        }
    };

    public QueueThreadPool(ThreadBuilder builder){
        name = builder.name;
        maxThreadNum = builder.maxThread;
        minThreadNum = builder.minThread;
        maxQueueSize = builder.maxQueueSize;
        maxStopTime = builder.maxStopTime;
        priority = builder.priority;
        deamon = builder.deamon;
        idleTimeout = builder.idleTimeout;
        if(builder.jobQueue != null){
            jobQueue = builder.jobQueue;
            jobQueue.clear();
        }
    }

    @Override
    protected void doStart() throws Exception {
        super.doStart();
        startedThreadNum.set(0);
        if(jobQueue == null){
            jobQueue = maxQueueSize > 0 ? new ArrayBlockingQueue<Runnable>(maxQueueSize)
                    : new LinkedBlockingQueue<Runnable>(1000);
        }
        int theads = startedThreadNum.get();
        while (isRunning() && theads < minThreadNum){
            startThread(theads);
            theads = startedThreadNum.get();
        }
    }


    @Override
    protected void doStop() throws Exception {
        super.doStop();
        long start = System.currentTimeMillis();

        while (startedThreadNum.get() > 0 && System.currentTimeMillis() - start < (maxStopTime/2)){
            Thread.sleep(1);
        }
        jobQueue.clear();
        Runnable noop = ()->{};
        for(int i = idleThreadNum.get();i>0;i--){
            jobQueue.offer(noop);
        }
        Thread.yield();

        if(startedThreadNum.get() > 0){
            for(Thread thread : threads){
                thread.interrupt();
            }
        }

        while(startedThreadNum.get() > 0 &&((System.currentTimeMillis() - start) > maxStopTime)){
            Thread.sleep(1);
        }
        Thread.yield();

        int size = threads.size();
        if(size > 0){
            if(size == 1){
                for(Thread unstopped : threads){
                    //TODO LOGGER
                }
            }
        }
        synchronized (joinLock){
            joinLock.notifyAll();
        }
    }


    private boolean startThread(int theads) {
        final int next = theads + 1;
        if(!startedThreadNum.compareAndSet(theads,next)){
            return false;
        }
        boolean started = false;
        try {
            Thread thread = newThread(runnable);
            thread.setDaemon(deamon);
            thread.setPriority(priority);
            thread.setName(name+thread.getId());
            threads.add(thread);

            thread.start();
            started = true;
        }finally {
            if (!started) {
                startedThreadNum.decrementAndGet();
            }
        }
        return started;
    }

    private Thread newThread(Runnable runnable) {
        return new Thread(runnable);
    }

    private Runnable idleJobPoll() throws InterruptedException{
        return jobQueue.poll(idleTimeout,TimeUnit.MILLISECONDS);
    }

    private void runJob(Runnable job) {
        job.run();
    }

    @Override
    public String toString() {
        return maxThreadNum+"/"+minThreadNum;
    }

    @Override
    public void join() throws InterruptedException {
        synchronized (joinLock){
            while (isRunning()){
                joinLock.wait();
            }
        }
        while (isStopping()){
            Thread.sleep(1);
        }
    }

    @Override
    public int getThreads() {
        return  startedThreadNum.get();
    }

    @Override
    public int getIdleThreads() {
        return idleThreadNum.get();
    }

    @Override
    public boolean dispatch(Runnable job) {
        if(isRunning()){
            final int jobSize = jobQueue.size();
            final int idleSize = getIdleThreads();
            if(jobQueue.offer(job)){

                if(idleSize == 0 || jobSize > idleSize){
                    int threads = startedThreadNum.get();
                    if(threads < maxThreadNum){
                        startThread(threads);
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public void execute(Runnable command) {
        if(!dispatch(command)){
            throw new RejectedExecutionException();
        }
    }

    public static class ThreadBuilder{

        private final String name;
        private int maxThread = 256;
        private int minThread = 6;
        private int maxStopTime=100;
        private BlockingQueue<Runnable> jobQueue;
        private int maxQueueSize = -1;
        private int priority = Thread.NORM_PRIORITY;
        private int idleTimeout = 60000;
        private boolean deamon = false;


        public ThreadBuilder(){
            name = "qtp"+hashCode();
        }

        public ThreadBuilder maxThread(int maxThread){
            this.maxThread = maxThread;
            return this;
        }
        public ThreadBuilder minThread(int minThread){
            this.minThread = minThread;
            return this;
        }

        public ThreadBuilder maxStopTime(int maxStopTime){
            this.maxStopTime = maxStopTime;
            return this;
        }

        public ThreadBuilder jobQueue(BlockingQueue<Runnable> jobQueue){
            this.jobQueue = jobQueue;
            return this;
        }
        public ThreadBuilder idleTimeOut(int idleTimeOut){
            this.idleTimeout= idleTimeOut;
            return this;
        }
        public ThreadBuilder priority(int priority){
            this.priority = priority;
            return this;
        }
        public ThreadBuilder maxQueueSize(int maxQueueSize){
            this.maxQueueSize = maxQueueSize;
            return this;
        }
        public ThreadBuilder deamon(boolean deamon){
            this.deamon = deamon;
            return this;
        }

        public QueueThreadPool build(){
            return new QueueThreadPool(this);
        }
    }
}
