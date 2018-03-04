package httpserver;

import httpserver.connector.nio.AbstractConnector;
import httpserver.connector.nio.NioConnector;
import httpserver.core.Server;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception{
        NioConnector connector = new NioConnector() ;
        connector.doStart();
    }
}
