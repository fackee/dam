package httpserver.message;

import java.nio.ByteBuffer;

public class ByteBufferUtil {
    private ByteBuffer byteBuffer;

    ByteBufferUtil(ByteBuffer byteBuffer){
        this.byteBuffer = byteBuffer;
    }

    boolean hasNext(){
        return byteBuffer.hasRemaining();
    }

    byte next(){
        return byteBuffer.get();
    }

    void clear(){
        if(!byteBuffer.hasRemaining()){
            byteBuffer.clear();
        }
    }
}