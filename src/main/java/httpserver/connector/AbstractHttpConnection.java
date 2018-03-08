package httpserver.connector;

import httpserver.connector.nio.buffer.Buffer;
import httpserver.connector.nio.buffer.ThreadLocalBuffer;
import httpserver.core.Server;
import httpserver.http.HttpField;
import httpserver.http.util.Parser;

/**
 * Created by zhujianxin on 2018/3/8.
 */
public class AbstractHttpConnection implements Connection{

    private Server server;
    private EndPoint endPoint;
    private HttpField httpField;
    private Parser parser;
    private ThreadLocalBuffer currentBuffer;

    public AbstractHttpConnection(Server server,EndPoint endPoint){
        this.server = server;
        this.endPoint = endPoint;

    }

    @Override
    public void setServer(Server server) {
        this.server = server;
    }

    @Override
    public Server getServer() {
        return server;
    }
}
