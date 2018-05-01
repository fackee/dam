package com.dam.nio;

import com.dam.AbstractConnector;
import com.dam.io.connection.Connection;
import com.dam.HttpConnection;
import com.dam.Server;
import com.dam.io.EndPoint;
import com.dam.io.nio.SelectChannelEndPoint;
import com.dam.io.nio.SelectorManager;
import com.dam.util.thread.ThreadPool;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Created by geeche on 2018/2/3.
 */
public class NioConnector extends AbstractConnector {

    private ServerSocketChannel acceptorChannel;
    private int localPort=-1;

    private final SelectorManager selectorManager = new NioSelectorManager();

    public NioConnector(Server server){
        super(server);
        addBean(selectorManager,true);
        setAcceptors(Runtime.getRuntime().availableProcessors()-2 <= 0 ? 1 : Runtime.getRuntime().availableProcessors()-2);
    }

    @Override
    public void setThreadPool(ThreadPool threadPool) {
        super.setThreadPool(threadPool);
        removeBean(selectorManager);
        addBean(selectorManager,true);
    }

    @Override
    public void doStart() throws Exception {
        selectorManager.setWorkLength(getAcceptors());
        super.doStart();
    }

    @Override
    public void open() throws IOException {
        synchronized (this){
            if(acceptorChannel == null){
                acceptorChannel = ServerSocketChannel.open();
                acceptorChannel.configureBlocking(true);
                acceptorChannel.socket().setReuseAddress(getReuseAddress());
                InetSocketAddress address = getHost() == null ? new InetSocketAddress(getPort()) : new InetSocketAddress(getHost(),getPort());
                acceptorChannel.socket().bind(address);
                localPort = acceptorChannel.socket().getLocalPort();
                if(localPort < 0){
                    throw new IOException("Server channel not bound");
                }
                addBean(acceptorChannel);
            }
        }
    }

    @Override
    public void close() throws IOException {
        synchronized (this){
            if(acceptorChannel != null){
                removeBean(acceptorChannel);
                if(acceptorChannel.isOpen()){
                    acceptorChannel.close();
                }
            }
            acceptorChannel = null;
            localPort = -1;
        }
    }

    @Override
    protected void accept(int acceptId) throws IOException, InterruptedException {
        ServerSocketChannel serverSocketChannel;
        synchronized (this){
            serverSocketChannel = acceptorChannel;
        }
        if(serverSocketChannel != null && serverSocketChannel.isOpen() && selectorManager.isStarted()){
            SocketChannel socketChannel = serverSocketChannel.accept();
            socketChannel.configureBlocking(false);
            Socket socket = socketChannel.socket();
            configure(socket);
            selectorManager.register(socketChannel);
        }
    }


    public SelectChannelEndPoint newEndPoint(SocketChannel channel, SelectorManager.SelectorWorker worker, SelectionKey key) throws IOException{
        SelectChannelEndPoint endPoint = new SelectChannelEndPoint(channel,worker,key);
        endPoint.setConnection(worker.getManager().newConnection(channel,endPoint,key.attachment()));
        return endPoint;
    }


    public void closeEndPoint(SelectChannelEndPoint endPoint) {

    }


    public Connection newConnection(SocketChannel channel, final EndPoint endPoint) {
        return new HttpConnection(NioConnector.this,endPoint,getServer());
    }

    class NioSelectorManager extends SelectorManager {

        @Override
        public boolean dispatch(Runnable runnable) {
            ThreadPool threadPool = getThreadPool();
            if(threadPool == null){
                threadPool = getServer().getThreadPool();
            }
            return threadPool.dispatch(runnable);
        }

        @Override
        public void endPointUpgraded(SelectChannelEndPoint selectChannelEndPoint, Connection old) {
            connectionUpgrade();
        }

        @Override
        public void endPointOpend(SelectChannelEndPoint endPoint) {
            connectionOpened();
        }

        @Override
        public void closeEndPoint(SelectChannelEndPoint endPoint) {
            NioConnector.this.closeEndPoint(endPoint);
        }


        @Override
        public Connection newConnection(SocketChannel channel, EndPoint endPoint, Object att) {
            return NioConnector.this.newConnection(channel,endPoint);
        }

        @Override
        public SelectChannelEndPoint newEndPoint(SocketChannel channel, SelectorWorker worker, SelectionKey key) throws IOException{
            return NioConnector.this.newEndPoint(channel,worker,key);
        }



    }

}
