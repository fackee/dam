package httpserver;

import httpserver.connector.NioConnector;

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
