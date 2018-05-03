package org.dam.server.handler;


import org.dam.server.Server;
import org.dam.http.Request;
import org.dam.http.Response;
import org.dam.utils.lifecycle.AbstractLifeCycle;

;

/**
 * Created by geeche on 2018/1/27.
 */
public abstract class AbstractHandler extends AbstractLifeCycle implements Handler {

    private Server server;

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
}
