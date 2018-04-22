package com.dam.connector;


import com.dam.lifecycle.ContainerLifeCycle;
import com.dam.util.thread.ThreadPool;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by geeche on 2018/2/3.
 */
public abstract class AbstractConnector extends ContainerLifeCycle implements Connector {

    private String name;

    private Server server;
    private ThreadPool threadPool;
    private String host;
    private int port = 8080;
    private int acceptorQueueSize = 0;
    private int acceptors = 1;
    private boolean reuseAddress;


    private Thread[] acceptorThread;

    private final AtomicLong statsStartedAt = new AtomicLong(-1L);

    public AbstractConnector(Server server){
        this.server = server;
    }



    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setServer(Server server) {
        this.server = server;
    }

    @Override
    public Server getServer() {
        return server;
    }

    @Override
    public String getHost() {
        return host;
    }

    @Override
    public void setHost(String hostName) {
        this.host = hostName;
    }

    @Override
    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public Object getConnection() {
        return null;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAcceptorQueueSize() {
        return acceptorQueueSize;
    }

    public void setAcceptorQueueSize(int acceptorQueueSize) {
        this.acceptorQueueSize = acceptorQueueSize;
    }

    public int getAcceptors() {
        return acceptors;
    }

    public void setAcceptors(int acceptors) {
        if(acceptors > 2*Runtime.getRuntime().availableProcessors()){
            return;
        }
        this.acceptors = acceptors;
    }

    public boolean getReuseAddress() {
        return reuseAddress;
    }

    public void setReuseAddress(boolean reuseAddress) {
        this.reuseAddress = reuseAddress;
    }

    public ThreadPool getThreadPool() {
        return threadPool;
    }

    public void setThreadPool(ThreadPool threadPool) {
        removeBean(this.threadPool);
        this.threadPool = threadPool;
        addBean( this.threadPool);
    }

    protected void configure(Socket socket) {

    }

    protected void connectionOpened(){
        if(statsStartedAt.get() == -1){
            return;
        }
        statsStartedAt.getAndIncrement();
    }

    protected void connectionUpgrade(){
    }

    @Override
    public abstract void open() throws IOException ;

    @Override
    public abstract void close() throws IOException ;

    protected abstract void accept(int acceptId) throws IOException,InterruptedException;

    @Override
    protected void doStart() throws Exception {
        if(server == null){
            throw new IllegalStateException("No server");
        }
        open();

        if(threadPool == null){
            threadPool = server.getThreadPool();
            addBean(threadPool,true);
        }

        super.doStart();

        synchronized (this){
            acceptorThread = new Thread[getAcceptors()];
            for(int i=0;i<acceptorThread.length;i++){
                if(!threadPool.dispatch(new Acceptor(i))){
                    throw new IllegalStateException("");
                }

            }
        }
    }

    @Override
    protected void doStop() throws Exception {
        try {
            close();
        }catch (IOException e){

        }
        super.doStop();

        Thread[] acceptors;
        synchronized (this){
            acceptors = acceptorThread;
            acceptorThread = null;
        }
        if(acceptors != null){
            for(Thread thread : acceptors){
                if(thread != null){
                    thread.interrupt();
                }
            }
        }
    }

    private class Acceptor implements Runnable{

        int acceptorId = 0;

        Acceptor(int acceptorId){
            this.acceptorId = acceptorId;
        }

        @Override
        public void run() {
            while (true){
                try {
                    accept(acceptorId);
                } catch (IOException e) {

                } catch (InterruptedException e) {

                }
            }
        }
    }
}
