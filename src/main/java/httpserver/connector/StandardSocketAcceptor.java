package httpserver.connector;

import httpserver.net.nio.Constant;
import httpserver.net.nio.EventLooper;
import httpserver.net.nio.EventQueue;
import httpserver.net.nio.NioChannel;
import sun.nio.ch.ServerSocketAdaptor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.atomic.AtomicLong;

public class StandardSocketAcceptor implements Acceptor,Runnable{

    private ServerSocketChannel serverSocketChannel;

    private int tcpPort;

    private volatile boolean isAccept = true;

    private final AtomicLong connetorCount = new AtomicLong();

    public StandardSocketAcceptor() {
        this(8888);
    }

    public StandardSocketAcceptor(int tcpPort) {
        this.tcpPort = tcpPort;
    }

    @Override
    public void accept() throws IOException{
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(tcpPort));
        while (true){
            //TODO remove to configuration
            if(connetorCount.get() > 10000){
                isAccept = false;
            }
            if(isAccept){
                SocketChannel acceptSocket = serverSocketChannel.accept();
                acceptSocket.configureBlocking(false);
                connetorCount.getAndIncrement();
                NioChannel nioChannel = new NioChannel(acceptSocket,SelectionKey.OP_ACCEPT);
                System.out.println("accept");
                EventQueue.enQueue(nioChannel);
            }
        }
    }

    @Override
    public boolean isAccept() {
        return isAccept;
    }

    @Override
    public Long getConnetorCount() {
        return connetorCount.get();
    }

    @Override
    public void run() {
        try {
            accept();
        } catch (IOException e) {

        }
    }

    public static void main(String[] args) throws IOException{
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(8888));
        while (true){
            SocketChannel socketChannel = serverSocketChannel.accept();
            ByteBuffer byteBuffer = ByteBuffer.allocate(512);
            int status = socketChannel.read(byteBuffer);
            System.out.println(status);
            byte[] bytes = new byte[status];
            byteBuffer.flip();
            byteBuffer.get(bytes,0,status);
            System.out.println(new String(bytes));
        }
    }
}