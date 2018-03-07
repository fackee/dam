package httpserver.util.thread;

import httpserver.core.AbstractLifeCycle;
import httpserver.core.LifeCycle;

import java.util.concurrent.*;


/**
 * Created by zhujianxin on 2018/3/7.
 */
public class ExecutorThreadPool extends AbstractLifeCycle implements ThreadPool,LifeCycle{

    private ExecutorService service;

    private BlockingQueue queue = new LinkedBlockingDeque();

    public ExecutorThreadPool(ExecutorService service){
        this.service = service;
    }

    public ExecutorThreadPool(){
        this(new ThreadPoolExecutor(256,256, 60L,TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>()));
    }

    public ExecutorThreadPool(int queueSize){
        this(queueSize < 0 ? new ThreadPoolExecutor(256,256,60l,TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>())
        : queueSize == 0 ? new ThreadPoolExecutor(32,256,60L,TimeUnit.SECONDS,new SynchronousQueue<Runnable>())
        : new ThreadPoolExecutor(32,256,60L,TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(queueSize)));
    }

    public ExecutorThreadPool(int corePoolSize,int maximumPoolSize){
        this(corePoolSize,maximumPoolSize,60);
    }

    public ExecutorThreadPool(int corePoolSize,int maximumPoolSize,int keepAliveTime){
        this(corePoolSize, maximumPoolSize,keepAliveTime,TimeUnit.SECONDS);
    }

    public ExecutorThreadPool(int corePoolSize,int maximumPoolSize,int keepAliveTime,TimeUnit timeUnit){
        this(corePoolSize, maximumPoolSize, keepAliveTime,timeUnit,new LinkedBlockingQueue<Runnable>());
    }

    public ExecutorThreadPool(int corePoolSize,int maximumPoolSize,int keepAliveTime,TimeUnit timeUnit,BlockingQueue queue){
        this(new ThreadPoolExecutor(corePoolSize,maximumPoolSize,keepAliveTime,timeUnit,queue));
    }


    @Override
    public boolean dispatch(Runnable runnable) {
        try{
            service.execute(runnable);
            return true;
        }catch (RejectedExecutionException e){
            //TODO Thread rejected
            return false;
        }
    }

    @Override
    public void join() throws InterruptedException {
        service.awaitTermination(Long.MAX_VALUE,TimeUnit.MILLISECONDS);
    }

    @Override
    public void stop() {
        service.shutdown();
    }

    @Override
    public int getThreads() {
        if(service instanceof ThreadPoolExecutor){
            return ((ThreadPoolExecutor)service).getPoolSize();
        }
        return -1;
    }

    @Override
    public int getIdleThreads() {
        if(service instanceof ThreadPoolExecutor){
            return ((ThreadPoolExecutor)service).getPoolSize()-((ThreadPoolExecutor)service).getActiveCount();
        }
        return -1;
    }
}
