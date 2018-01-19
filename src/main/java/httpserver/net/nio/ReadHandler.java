package httpserver.net.nio;

import httpserver.message.ByteBufferHandler;

import java.nio.ByteBuffer;

public abstract class ReadHandler implements EventHandler {

    private ByteBufferHandler readBufferHandler;

    public ReadHandler(ByteBufferHandler readBufferHandler){
        this.readBufferHandler = readBufferHandler;
    }

    public abstract void registerWriteEvent() ;

}