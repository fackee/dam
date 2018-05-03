package org.dam.start;

import org.dam.start.config.Configuration;
import org.dam.start.load.LoadWebappsCache;
import org.dam.server.Connector;
import org.dam.server.Server;
import org.dam.server.nio.NioConnector;
import sun.misc.OSEnvironment;

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
        //System.out.println(App.class.getResource(""));
    }
}
