package httpserver.connector;

import java.nio.channels.Selector;

public interface Processor {

    public void process(Selector selector);

}