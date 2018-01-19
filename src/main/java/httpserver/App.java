package httpserver;

import httpserver.connector.StandardEventLooperProcessor;
import httpserver.connector.StandardSocketAcceptor;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        StandardSocketAcceptor standardSocketAcceptor = new StandardSocketAcceptor();
        StandardEventLooperProcessor standardEventLooperProcessor = new StandardEventLooperProcessor();
        //for(int i=0;i<5;i++){
            Thread accpet = new Thread(standardSocketAcceptor);
            accpet.start();
        //}
        Thread process = new Thread(standardEventLooperProcessor);
        process.start();
    }
}
