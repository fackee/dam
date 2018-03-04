package httpserver.connector;

import httpserver.connector.nio.AsyncConnection;
import httpserver.connector.nio.Connection;
import httpserver.connector.nio.SelectorManager;

import java.io.EOFException;
import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

/**
 * Created by geeche on 2018/2/3.
 */
public class SelectChannelEndPoint extends AbstractEndPoint implements ConnectedEndPoint{
    private SelectorManager manager;
    private SelectorManager.SelectorWorker worker;
    private SelectionKey key;

    private AsyncConnection connection;
    private int interestOps;

    public SelectChannelEndPoint(SocketChannel channel, SelectorManager.SelectorWorker worker,SelectionKey key) throws IOException{
        super(channel);
        this.worker = worker;
        this.key = key;
    }

    public SelectionKey getSelectionKey() {
        synchronized (this){
            return key;
        }
    }

    public SelectorManager getManager() {
        return manager;
    }


    @Override
    public void setConnection(Connection conn) {
        Connection old = connection;
        connection = (AsyncConnection)conn;
        if(old != null && old != connection){
            manager.endPointUpgraded(this,old);
        }
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public boolean blockReadable(long millisecs) throws IOException{
        synchronized (this){
            if(isInputShutdown()){
                throw new EOFException();
            }
            //long now = worker.getNow();
            //long end =  now + millisecs;

        }
    }

    @Override
    public boolean blockWritable(long millisecs) {
        return false;
    }


    public void doUpdateKey() {
        synchronized (this){
            if(getChannel().isOpen()){
                if(key == null){}
            }else{
                if(key !=null && key.isValid()){
                    key.cancel();
                }

            }
        }
    }
}
