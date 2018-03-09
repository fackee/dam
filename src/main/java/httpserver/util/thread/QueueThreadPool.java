package httpserver.util.thread;

import httpserver.core.AbstractLifeCycle;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by zhujianxin on 2018/3/9.
 */
public class QueueThreadPool extends AbstractLifeCycle{


    private AtomicLong startedThreadNum;

    private AtomicLong idleThreadNum;

    private ConcurrentHashMap<String,Thread> threadMap;

    private BlockingQueue<Runnable> jobQueue;

    private int idleTimeout;

    private String name;

    private int maxThreadNum;

    private int minThreadNum;

    private boolean deamon;

    private Runnable job;

    public QueueThreadPool(){
        this(100);
    }

    public QueueThreadPool(int maxThreadNum){
        this(maxThreadNum,5);
    }

    public QueueThreadPool(int maxThreadNum,int minThreadNum){
        this(maxThreadNum,minThreadNum,10);
    }

    public QueueThreadPool(int maxThreadNum,int minThreadNum,int idleTimeout){
        this(maxThreadNum,minThreadNum,idleTimeout,true);
    }

    public QueueThreadPool(int maxThreadNum,int minThreadNum,int idleTimeout,boolean deamon){
        this(maxThreadNum,minThreadNum,idleTimeout,true,new LinkedBlockingQueue<Runnable>(1000));
    }

    public QueueThreadPool(int maxThreadNum,int minThreadNum,int idleTimeout,boolean deamon,BlockingQueue<Runnable> jobQueue){
        if((maxThreadNum <=0 || minThreadNum<=0)
                || maxThreadNum<minThreadNum){
            throw new IllegalArgumentException("maxThreadNum must be greater than minThreadNum");
        }
        if(idleTimeout <0){
            throw new IllegalArgumentException("idleTimeOut must be greater than 0");
        }
        this.startedThreadNum = new AtomicLong();
        this.idleThreadNum = new AtomicLong();
        this.threadMap = new ConcurrentHashMap<String,Thread>(16);
        this.name = "qtp" + this.hashCode();
        this.maxThreadNum = maxThreadNum;
        this.minThreadNum = minThreadNum;
        this.idleTimeout = idleTimeout;
        this.deamon = deamon;
        this.jobQueue = jobQueue;
        this.job = ()->{

        };
    }

    @Override
    protected void doStart() throws Exception {
        super.doStart();
    }

    @Override
    protected void doStop() throws Exception {
        super.doStop();
    }
}
