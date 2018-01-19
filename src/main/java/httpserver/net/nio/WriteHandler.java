package httpserver.net.nio;

import java.nio.ByteBuffer;

public abstract class WriteHandler implements EventHandler{

    public ByteBuffer writeBuffer;

    public WriteHandler(ByteBuffer writeBuffer){
        this.writeBuffer = writeBuffer;
    }

    public abstract void close();

}