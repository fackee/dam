package httpserver;

import httpserver.message.ByteBufferHandler;

import java.nio.ByteBuffer;

public class BufferReader implements Runnable{

    private ByteBuffer byteBuffer;

    private  ByteBufferHandler byteBufferHandler;

    public BufferReader(ByteBuffer byteBuffer,ByteBufferHandler byteBufferHandler){
        this.byteBuffer = byteBuffer;
        this.byteBufferHandler = byteBufferHandler;
    }

    @Override
    public void run() {
        synchronized (byteBuffer){
            if(null == byteBufferHandler.getMessage()){
                try {
                    byteBuffer.wait();
                    System.out.println("    ReadMessage:"+new String(byteBufferHandler.getMessage()));
                } catch (InterruptedException e) {

                }
            }else{
                byteBuffer.notifyAll();
            }
        }
    }
}