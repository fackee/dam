package httpserver.core;

import httpserver.connector.Connector;
import httpserver.handler.HandleWrapper;
import httpserver.util.thread.ExecutorThreadPool;
import httpserver.util.thread.QueueThreadPool;
import httpserver.util.thread.ThreadPool;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by geeche on 2018/1/25.
 */
public class Server {


    private Connector connector;

    private ExecutorThreadPool acceptService;
    private ExecutorThreadPool workerService;
    private HandleWrapper handleWrapper;

    private ThreadPool threadPool;

    public Server(){
        this(5);
    }
    public Server(int acceptThreadNum){
        this(acceptThreadNum,10000);
    }
    public Server(int acceptThreadNum,int workThreadNum){
        this(acceptThreadNum,workThreadNum,
                new QueueThreadPool.ThreadBuilder()
                        .maxThread(workThreadNum)
                        .minThread(100)
                        .jobQueue(new ArrayBlockingQueue<Runnable>(workThreadNum))
                        .maxQueueSize(workThreadNum)
                        .build());
    }
    public Server(int acceptThreadNum,int workThreadNum,ThreadPool threadPool){
        this.threadPool = threadPool;
        acceptService = new ExecutorThreadPool(acceptThreadNum);
        workerService = new ExecutorThreadPool(workThreadNum);
    }


    public ThreadPool getThreadPool() {
        return threadPool;
    }

    public void setConnector(Connector connector) {
        this.connector = connector;
    }

    public Connector getConnector() {
        return connector;
    }

    public void setHandleWrapper(HandleWrapper handleWrapper) {
        this.handleWrapper = handleWrapper;
    }

    public HandleWrapper getHandleWrapper() {
        return handleWrapper;
    }


    public ExecutorThreadPool getAcceptService() {
        return acceptService;
    }

    public ExecutorThreadPool getWorkerService() {
        return workerService;
    }

    public void serve(){
        connector.start();
    }

    public void handle(String target,Request request,Response response) {
        handleWrapper.handle();
    }
}
