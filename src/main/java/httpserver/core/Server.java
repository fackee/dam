package httpserver.core;

import httpserver.connector.Connector;
import httpserver.handler.HandleWrapper;
import httpserver.util.thread.ExecutorThreadPool;
import sun.nio.ch.ThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
        acceptService = new ExecutorThreadPool(acceptThreadNum);
        workerService = new ExecutorThreadPool(workThreadNum);
    }


    public ThreadPool getThreadPool() {
        return threadPool;
    }

    public ExecutorThreadPool getAcceptService() {
        return acceptService;
    }

    public ExecutorThreadPool getWorkerService() {
        return workerService;
    }
}
