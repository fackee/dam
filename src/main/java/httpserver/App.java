package httpserver;

import httpserver.connector.Connector;
import httpserver.connector.NioConnector;
import httpserver.core.Server;

import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception{
        Server server = new Server();
        Connector connector = new NioConnector(server);
        server.setConnector(connector);
        server.serve();

    }
}
