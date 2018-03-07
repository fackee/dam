package httpserver.connector;

import httpserver.connector.nio.SelectorManager;
import httpserver.util.thread.ExecutorThreadPool;
import httpserver.util.thread.Task;

import java.io.EOFException;
import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;

/**
 * Created by geeche on 2018/2/3.
 */
public class SelectChannelEndPoint extends AbstractEndPoint implements ConnectedEndPoint{

    private SelectorManager manager;
    private SelectorManager.SelectorWorker worker;
    private SelectionKey key;
    private AsyncHttpConnection connection;

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
        connection = (AsyncHttpConnection)conn;
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
        return false;
    }

    @Override
    public boolean blockWritable(long millisecs) throws IOException{
        synchronized (this){
            if(isOutputShutdown()){
                throw new EOFException();
            }

        }
        return false;
    }

    @Override
    public void dispatch(Task task){
        connection.getServer().getWorkerService().dispatch(task);
    }

    public void doUpdateKey() throws IOException {
        synchronized (this){
            if(getChannel().isOpen()){
                if(key == null){
                    if(getChannel().isOpen()){
                        getChannel().close();
                    }
                }else{
                    worker.addWork(this);
                }
            }else{
                if(key !=null && key.isValid()){
                    key.cancel();
                }
            }
        }
    }

}
