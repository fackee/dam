package httpserver.connector;

import com.sun.org.apache.regexp.internal.RE;
import httpserver.connector.nio.buffer.Buffer;
import httpserver.connector.nio.buffer.NioBuffer;
import httpserver.connector.nio.buffer.ThreadLocalBuffer;
import httpserver.core.Request;
import httpserver.core.Response;
import httpserver.core.Server;
import httpserver.http.HttpField;
import httpserver.http.util.Parser;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Created by zhujianxin on 2018/3/8.
 */
public class AbstractHttpConnection implements Connection{

    private Server server;
    private Connector connector;
    private EndPoint endPoint;
    private HttpField httpField;
    private Parser parser;

    private final ThreadLocalBuffer currentRequestBuffer = new ThreadLocalBuffer(ThreadLocalBuffer.BufferSize.REQUEST_BUFFER_SIZE.getSize());
    private final ThreadLocalBuffer currentResponseBuffer = new ThreadLocalBuffer(ThreadLocalBuffer.BufferSize.RESPONSE_BUFFER_SIZE.getSize());

    private static final String HTTP_MESSAGE_REGEX = "\r\n";

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

    public Connection handle() {
        Buffer tempbuffer = new NioBuffer();
        endPoint.fill(tempbuffer);
        currentRequestBuffer.fillBuffer(tempbuffer);
        tempbuffer = null;
        String[] lines = new String(currentRequestBuffer.getData()).split(HTTP_MESSAGE_REGEX);
        System.out.println(new String(currentRequestBuffer.getData()));
        try {
            endPoint.blockWritable(10L);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ((SelectChannelEndPoint)endPoint).updateKey();
        //parser
        //completeParser-->endPointUpdateKey->registerWrite
        //createRequest
        //loadApp
        //createResponse
        String target = null;
        Request request = null;
        Response response = null;
        //server.handle(target, request, response);
        return this;
    }
}
