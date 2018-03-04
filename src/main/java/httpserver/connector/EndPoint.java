package httpserver.connector;

import httpserver.connector.nio.buffer.Buffer;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Created by geeche on 2018/2/3.
 */
public interface EndPoint {

    public void shutdownOutput() throws IOException;

    public boolean isOutputShutdown();

    public void shutdownInput() throws IOException;

    public boolean isInputShutdown();

    public String getLocalAddr();

    public String getLocalHost();

    public int getLocalPort();

    public String getRemoteAddr();

    public String getRemoteHost();

    public int getRemotePort();

    public void close() throws IOException;

    public int fill(Buffer buffer);

    public int flush(Buffer buffer);

    public boolean isBlocking();

    public boolean blockReadable(long millisecs) throws IOException;

    public boolean blockWritable(long millisecs) throws IOException;

    public boolean isOpen();

    public Object getTransport();

    public void flush() throws IOException;

}
