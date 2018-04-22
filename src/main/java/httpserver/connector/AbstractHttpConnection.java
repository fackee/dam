package httpserver.connector;

import httpserver.connector.nio.buffer.Buffer;
import httpserver.connector.nio.buffer.NioBuffer;
import httpserver.connector.nio.buffer.ThreadLocalBuffer;
import httpserver.core.Server;
import httpserver.http.HttpField;
import httpserver.http.util.HttpParser;

import javax.xml.soap.SOAPPart;
import java.io.IOException;

/**
 * Created by zhujianxin on 2018/3/8.
 */
public class AbstractHttpConnection implements Connection{

    private Server server;
    private Connector connector;
    private EndPoint endPoint;
    protected HttpField httpField;
    private HttpParser httpParser;

    public AbstractHttpConnection(Server server,Connector connector,EndPoint endPoint){
        this.server = server;
        this.connector = connector;
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

    public EndPoint getEndPoint() {
        return endPoint;
    }

    public Connection handle() {
        System.out.println("dispatch-->handler,super");
        try {
            httpParser = new HttpParser(endPoint).parse();
        } catch (IOException e) {

        }
        httpField = httpParser.getHttpField();
//        try {
////            endPoint.blockWritable(10000L);
//        } catch (IOException e) {
//
//        }
//        try {
//            endPoint.blockWritable(10L);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        ((SelectChannelEndPoint)endPoint).updateKey();
        return this;
    }
}
