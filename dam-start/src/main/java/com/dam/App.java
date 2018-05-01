package com.dam;

import com.dam.bridge.LoadAppJar;
import com.dam.config.Configuration;
import com.dam.load.LoadWebappsCache;
import com.dam.nio.NioConnector;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
//    static {
//        try {
//            LoadAppJar.loadApps("");
//        } catch (IOException e) {
//
//        }
//    }
    public static void run() {
        Configuration configuration = new Configuration().config();
        LoadWebappsCache cache = new LoadWebappsCache().cache();
        Server server = new Server();
        Connector connector = new NioConnector(server);
        connector.setPort(8080);
        server.setConnector(connector);
        server.serve();
        System.out.println("Dam started");
    }

    public static void main( String[] args ) throws Exception{
        run();
    }
}
