package com.dam.handler;


import com.dam.Server;
import com.dam.http.Request;
import com.dam.http.Response;
import com.dam.lifecycle.AbstractLifeCycle;

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
