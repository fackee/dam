package httpserver.connector;

import httpserver.net.nio.Constant;
import httpserver.net.nio.EventLooper;
import httpserver.net.nio.NioChannel;

import java.io.IOException;
import java.nio.channels.Selector;
import java.util.Iterator;

public class StandardEventLooperProcessor implements Processor,Runnable{

    private Selector selector;

    @Override
    public void process(Selector selector) {
        try {
            EventLooper.run(selector);
        }catch (IOException ioe){

        }
    }

    @Override
    public void run() {
        try {
            selector = Selector.open();
            while (true){
                process(selector);
            }
        } catch (IOException e) {

        }
    }
}