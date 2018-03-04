package httpserver.message;

import java.io.IOException;
import java.nio.ByteBuffer;

public interface Reader {

    public void read() throws IOException;

}