package httpserver.core;

import httpserver.connector.nio.Connector;
import httpserver.handler.HandleWrapper;
import sun.nio.ch.ThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by geeche on 2018/1/25.
 */
public class Server {


    private Connector connector;
    private ExecutorService acceptService;
    private ExecutorService workerService;
    private HandleWrapper handleWrapper;
    private ThreadPool threadPool;
    public Server(){
        acceptService = Executors.newCachedThreadPool();
        workerService = Executors.newCachedThreadPool();
        handleWrapper = new HandleWrapper();
        //connector =
    }
    public void serve() {

    }

    public ThreadPool getThreadPool() {
        return threadPool;
    }
}
