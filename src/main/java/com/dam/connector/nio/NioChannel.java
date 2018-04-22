package com.dam.connector.nio;

import com.dam.connector.Constant;
import com.dam.message.ByteBufferHandler;
import com.dam.message.Reader;
import com.dam.message.Writer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NioChannel implements Reader,Writer {

    private SocketChannel socketChannel;

    private ByteBufferHandler byteBufferHandler;

    public NioChannel(SocketChannel socketChannel){
        this.socketChannel = socketChannel;

    }


    @Override
    public void read() throws IOException{
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

    public SocketChannel getSocketChannel() {
        return socketChannel;
    }
}