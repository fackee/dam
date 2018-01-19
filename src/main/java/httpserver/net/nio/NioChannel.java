package httpserver.net.nio;

import httpserver.message.ByteBufferHandler;
import httpserver.message.Reader;
import httpserver.message.Writer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class NioChannel implements Reader,Writer{


    private SocketChannel socketChannel;

    private int eventKey;


    public NioChannel(SocketChannel socketChannel, int eventKey){
        this.socketChannel = socketChannel;
        this.eventKey = eventKey;
    }


    public NioChannel register(Selector selector)throws IOException{
        if(eventKey == SelectionKey.OP_ACCEPT){
            NioChannel nioChannel = new NioChannel((SocketChannel) socketChannel.register(selector,SelectionKey.OP_READ).channel(),SelectionKey.OP_READ);
            return nioChannel;
        }else if(eventKey == SelectionKey.OP_READ){
            NioChannel nioChannel = new NioChannel((SocketChannel) socketChannel.register(selector,SelectionKey.OP_WRITE).channel(),SelectionKey.OP_WRITE);
            return nioChannel;
        }else{
            return null;
        }
    }

    @Override
    public void read(ByteBufferHandler byteBufferHandler) throws IOException{
        ByteBuffer temp = ByteBuffer.allocate(512);
        int bufferLength = socketChannel.read(temp);
        while (!temp.hasRemaining()){
            temp = createBuffer(false,bufferLength);
            temp.clear();
            bufferLength = socketChannel.read(temp);
        }
        byte[] dst = new byte[bufferLength];
        temp.flip();
        temp.get(dst,0,bufferLength);
        byteBufferHandler.expendMessage(dst);
        temp.clear();
        temp = null;
    }

    private ByteBuffer createBuffer(boolean direct,int length){
        if(direct){
            if(length < Constant.KB){
                return  ByteBuffer.allocateDirect(Constant.KB);
            }else if(length < Constant.KB*4){
                return  ByteBuffer.allocateDirect(Constant.KB*4);
            }else if(length < Constant.KB*128){
                return ByteBuffer.allocateDirect(Constant.KB*128);
            }else if(length < Constant.MB){
                return  ByteBuffer.allocateDirect(Constant.MB);
            }else{
                return ByteBuffer.allocateDirect(Constant.MB*4);
            }
        }
        if(length < Constant.KB){
            return ByteBuffer.allocate(Constant.KB);
        }else if(length < Constant.KB*4){
            return  ByteBuffer.allocate(Constant.KB*4);
        }else if(length < Constant.KB*128){
            return ByteBuffer.allocate(Constant.KB*128);
        }else if(length < Constant.MB){
            return  ByteBuffer.allocate(Constant.MB);
        }else{
            return  ByteBuffer.allocate(Constant.MB*4);
        }
    }

    @Override
    public void write(ByteBufferHandler byteBufferHandler) throws IOException{
        String httpResponse = "HTTP/1.1 200 OK\r\n" +
                "Content-Length: 38\r\n" +
                "Content-Type: text/html\r\n" +
                "\r\n" +
                "<html><body>Hello World!</body></html>";

        byte[] httpResponseBytes = httpResponse.getBytes("UTF-8");
        byteBufferHandler.expendMessage(httpResponseBytes);
        socketChannel.write(ByteBuffer.wrap(byteBufferHandler.getMessage()));
    }

    public int getEventKey() {
        return eventKey;
    }
}