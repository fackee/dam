package com.dam.connector;

import com.dam.connector.nio.buffer.Buffer;

import java.io.IOException;

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

    public void flush() throws IOException;

    public int flush(Buffer buffer) throws IOException;

    public int flush(Buffer header, Buffer body) throws IOException;

    public boolean isBlocking();

    public boolean blockReadable(long millisecs) throws IOException;

    public boolean blockWritable(long millisecs) throws IOException;

    public boolean isOpen();

    public Object getTransport();


}
