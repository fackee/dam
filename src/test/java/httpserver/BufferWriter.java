package httpserver;

import httpserver.message.ByteBufferHandler;

import java.nio.ByteBuffer;

public class BufferWriter implements Runnable{

    private ByteBuffer byteBuffer;

    private String message;

    private  ByteBufferHandler byteBufferHandler;

    public BufferWriter(ByteBuffer byteBuffer,String message,ByteBufferHandler byteBufferHandler){
        this.byteBuffer = byteBuffer;
        this.message = message;
        this.byteBufferHandler = byteBufferHandler;
    }


    @Override
    public void run() {
        synchronized (byteBuffer){
            if(byteBufferHandler.expendMessage(message.getBytes())){
                System.out.println("writeMessage:" + message);
                byteBuffer.notifyAll();
            }else{
                try {
                    byteBuffer.wait();
                } catch (InterruptedException e) {

                }
            }
        }
    }
}