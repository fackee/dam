package httpserver.connector;

import httpserver.core.Request;
import httpserver.core.Response;
import httpserver.core.Server;

/**
 * Created by geeche on 2018/2/23.
 */
public class HttpConnection extends AbstractHttpConnection{

    private Request request;
    private Response response;


    public HttpConnection(Server server, EndPoint endPoint) {
        super(server, endPoint);
    }
}
