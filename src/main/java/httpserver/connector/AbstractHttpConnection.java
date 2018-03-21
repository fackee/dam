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
    private EndPoint endPoint;
    private HttpField httpField;
    private Parser parser;

    private final NioBuffer requestBuffer = new NioBuffer(ThreadLocalBuffer.BufferSize.REQUEST_BUFFER_SIZE.ordinal());
    private final NioBuffer responseBuffer = new NioBuffer(ThreadLocalBuffer.BufferSize.REQUEST_BUFFER_SIZE.ordinal());

    private final ThreadLocalBuffer currentRequestBuffer;

    private final ThreadLocalBuffer currentResponseBuffer;

    public AbstractHttpConnection(Server server,EndPoint endPoint){
        this.server = server;
        this.endPoint = endPoint;
        currentRequestBuffer = new ThreadLocalBuffer(requestBuffer);
        currentResponseBuffer = new ThreadLocalBuffer(responseBuffer);
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
        endPoint.fill(requestBuffer);
        currentRequestBuffer.setNioBuffer(responseBuffer);
        //System.out.println(new String(requestBuffer.byteBuffer().array()));
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
