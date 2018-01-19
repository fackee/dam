package httpserver.message;

import java.io.IOException;
import java.nio.ByteBuffer;

public interface Writer {

    public void write(ByteBufferHandler byteBufferHandler)throws IOException;

}