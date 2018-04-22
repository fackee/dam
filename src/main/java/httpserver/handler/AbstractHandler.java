package httpserver.handler;

import httpserver.core.AbstractLifeCycle;
import httpserver.http.Request;
import httpserver.http.Response;
import httpserver.core.Server;

/**
 * Created by geeche on 2018/1/27.
 */
public abstract class AbstractHandler extends AbstractLifeCycle implements Handler{

    private Server server;

    @Override
    public abstract void handle(Request baseRequest, Response baseResponse);

    @Override
    public Server getServer() {
        return server;
    }

    @Override
    public void setServer(Server server) {
        this.server = server;
    }

    @Override
    public void destroy() {

    }
}
