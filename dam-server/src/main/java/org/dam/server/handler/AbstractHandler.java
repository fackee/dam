package org.dam.server.handler;


import org.dam.server.response.ResponseStrategy;
import org.dam.server.Server;
import org.dam.http.Request;
import org.dam.http.Response;
import org.dam.utils.lifecycle.AbstractLifeCycle;

;

/**
 * Created by geeche on 2018/1/27.
 */
public abstract class AbstractHandler extends AbstractLifeCycle implements Handler {

    private ResponseStrategy responseStrategy;

    private Server server;

    protected HandleWrapper.HandleResult handleResult;
    @Override
    public abstract boolean handle(Request baseRequest, Response baseResponse);

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

    public void setResponseStrategy(ResponseStrategy responseStrategy) {
        this.responseStrategy = responseStrategy;
    }

    public ResponseStrategy getResponseStrategy() {
        return responseStrategy;
    }

    public void setHandleResult(HandleWrapper.HandleResult handleResult) {
        this.handleResult = handleResult;
    }

    public HandleWrapper.HandleResult getHandleResult() {
        return handleResult;
    }
}
