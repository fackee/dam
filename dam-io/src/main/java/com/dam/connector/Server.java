package com.dam.connector;

import com.dam.handler.AbstractHandler;
import com.dam.handler.HandleWrapper;
import com.dam.handler.Handler;
import com.dam.http.Request;
import com.dam.http.Response;
import com.dam.lifecycle.AbstractLifeCycle;
import com.dam.util.thread.ExecutorThreadPool;
import com.dam.util.thread.QueueThreadPool;
import com.dam.util.thread.ThreadPool;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by geeche on 2018/1/25.
 */
public class Server extends AbstractHandler {


    private Connector connector;

    private ExecutorThreadPool acceptService;
    private ExecutorThreadPool workerService;
    private HandleWrapper handleWrapper;
    private final List<Handler> handlerChain = new LinkedList<>();
    private ThreadPool threadPool;

    public Server(){
        this(5);
    }
    public Server(int acceptThreadNum){
        this(acceptThreadNum,10000);
    }
    public Server(int acceptThreadNum, int workThreadNum){
        this(acceptThreadNum,workThreadNum,
                new QueueThreadPool.ThreadBuilder()
                        .maxThread(workThreadNum)
                        .minThread(100)
                        .jobQueue(new ArrayBlockingQueue<Runnable>(workThreadNum))
                        .maxQueueSize(workThreadNum)
                        .build());
    }
    public Server(int acceptThreadNum, int workThreadNum, ThreadPool threadPool){
        this.threadPool = threadPool;
        acceptService = new ExecutorThreadPool(acceptThreadNum);
        workerService = new ExecutorThreadPool(workThreadNum);
        handleWrapper = new HandleWrapper(new LinkedList<Handler>());
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

    public void setHandler(Handler handler){
        synchronized (handlerChain){
            handlerChain.add(handler);
        }
    }
    public ExecutorThreadPool getAcceptService() {
        return acceptService;
    }

    public ExecutorThreadPool getWorkerService() {
        return workerService;
    }

    @Override
    protected void doStart() throws Exception {
        connector.start();;
    }

    @Override
    public void handle(Request baseRequest, Response baseResponse) {
        handleWrapper.handle(baseRequest,baseResponse);
    }

    public void serve() {
        try {
            this.doStart();
        } catch (Exception e) {

        }
    }
}
