package httpserver.message;

import java.io.IOException;
import java.nio.ByteBuffer;

public interface Reader {

    public void read(ByteBufferHandler byteBufferHandler) throws IOException;

}