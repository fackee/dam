package org.dam.start;

import org.dam.server.Connector;
import org.dam.server.Server;
import org.dam.server.config.Configuration;
import org.dam.server.cache.WebappsCache;
import org.dam.server.nio.NioConnector;
import org.dam.utils.util.log.Logger;

import java.io.FileNotFoundException;
import java.util.Map;

/**
 * Hello world!
 */
public class App {

    public static void run() {
        Configuration configuration = null;
        try {
            configuration = new Configuration().config();
        } catch (FileNotFoundException e) {
            Logger.ERROR("read configuration error:{}",
                    Logger.printStackTraceToString(e.fillInStackTrace()));
            System.exit(0);
        }
        WebappsCache webappsCache = new WebappsCache(configuration).cacheClassesApp();
        Server server = new Server(configuration,webappsCache);
        Connector connector = new NioConnector(server);
        connector.setPort(80);
        server.setConnector(connector);
        server.serve();
        Logger.INFO("========================Dam started========================");
    }

    public static void main(String[] args) throws Exception {
        run();
    }
}
