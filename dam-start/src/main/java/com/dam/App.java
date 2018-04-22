package com.dam;

import com.dam.connector.Connector;
import com.dam.connector.NioConnector;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception{
        Server server = new Server();
        Connector connector = new NioConnector(server);
        connector.setPort(8080);
        server.setConnector(connector);
        server.serve();
    }
}
