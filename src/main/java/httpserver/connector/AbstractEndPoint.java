package httpserver.connector;

import httpserver.connector.EndPoint;
import httpserver.connector.nio.buffer.Buffer;
import httpserver.util.thread.Task;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.nio.channels.ByteChannel;
import java.nio.channels.Channel;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SocketChannel;

/**
 * Created by geeche on 2018/2/3.
 */
public abstract class AbstractEndPoint implements EndPoint{

    protected final ByteChannel channel;
    protected final Socket socket;
    protected final InetSocketAddress local;
    protected final InetSocketAddress remote;
    protected volatile int maxIdleTime;
    private volatile boolean inShut;
    private volatile boolean outShut;

    public AbstractEndPoint(ByteChannel channel) throws IOException{
        super();
        this.channel = channel;
        socket = (channel instanceof SocketChannel)? ((SocketChannel)channel).socket() : null;
        if(socket!=null){
            local = (InetSocketAddress)socket.getLocalSocketAddress();
            remote = (InetSocketAddress)socket.getRemoteSocketAddress();
            maxIdleTime = socket.getSoTimeout();
        }else{
            local = remote = null;
        }
    }

    public AbstractEndPoint(ByteChannel channel,int maxIdleTime) throws IOException{
        super();
        this.channel = channel;
        socket = (channel instanceof SocketChannel)? ((SocketChannel)channel).socket() : null;
        if(socket!=null){
            local = (InetSocketAddress)socket.getLocalSocketAddress();
            remote = (InetSocketAddress)socket.getRemoteSocketAddress();
            socket.setSoTimeout(maxIdleTime);
        }else{
            local = remote = null;
        }
    }

    public Channel getChannel() {
        return channel;
    }

    protected final void shutdownChannelOutput()throws IOException{
        outShut = true;
        if(channel.isOpen()){
            if(socket != null){
                try {
                    if(!socket.isOutputShutdown()){
                        socket.shutdownOutput();
                    }
                }catch (SocketException e){
                    //TODO
                }finally {
                    if(inShut){
                        close();
                    }
                }
            }
        }
    }

    @Override
    public void shutdownOutput() throws IOException {
        shutdownChannelOutput();
    }

    @Override
    public boolean isOutputShutdown() {
        return inShut || !channel.isOpen() || socket != null && socket.isOutputShutdown();
    }

    protected final void shutdownChannelInput()throws IOException{
        inShut = true;
        if(channel.isOpen()){
            if(socket != null){
                try {
                    if(!socket.isInputShutdown()){
                        socket.shutdownInput();
                    }
                }catch (SocketException e){
                    //TODO
                }finally {
                    if(outShut){
                        close();
                    }
                }
            }
        }
    }

    @Override
    public void shutdownInput() throws IOException {
        shutdownChannelInput();
    }

    @Override
    public boolean isInputShutdown() {
        return inShut || !channel.isOpen() || socket !=null && socket.isInputShutdown();
    }

    @Override
    public String getLocalAddr() {
        if(socket ==null){
            return null;
        }
        if(local == null || local.getAddress() == null || local.getAddress().isAnyLocalAddress()){
            //TODO build a string  util
            return "0.0.0.0";
        }
        return local.getAddress().getHostAddress();
    }

    @Override
    public String getLocalHost() {
        if(socket ==null){
            return null;
        }
        if(local == null || local.getAddress() == null || local.getAddress().isAnyLocalAddress()){
            //TODO build a string  util
            return "0.0.0.0";
        }
        return local.getAddress().getCanonicalHostName();
    }

    @Override
    public int getLocalPort() {
        if(socket == null){
            return -1;
        }
        return local == null ? 0 : local.getPort();
    }

    @Override
    public String getRemoteAddr() {
        if(socket ==null){
            return null;
        }
        return remote == null ? null : remote.getAddress().getHostAddress();
    }

    @Override
    public String getRemoteHost() {
        if(socket ==null){
            return null;
        }
        return remote==null ? null : remote.getAddress().getCanonicalHostName();
    }

    @Override
    public int getRemotePort() {
        if(socket == null){
            return 0;
        }
        return remote == null ? -1:remote.getPort();
    }

    @Override
    public void close() throws IOException {
        channel.close();
    }

    @Override
    public int fill(Buffer buffer) {
        if(inShut){
            return -1;
        }

        return 0;
    }

    @Override
    public int flush(Buffer buffer) {
        return 0;
    }

    @Override
    public void flush() throws IOException {

    }

    @Override
    public boolean isBlocking() {
        return (channel instanceof SelectableChannel) || ((SelectableChannel)channel).isBlocking();
    }

    @Override
    public abstract boolean blockReadable(long millisecs) throws IOException;

    @Override
    public abstract boolean blockWritable(long millisecs) throws IOException;

    public abstract void updateKey();

    public abstract void doUpdateKey();

    public abstract void shedule() throws IOException;

    public abstract void dispatch();

    @Override
    public boolean isOpen() {
        return channel.isOpen();
    }

    @Override
    public Object getTransport() {
        return channel;
    }



}
