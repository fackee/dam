package com.dam.handler;

import com.dam.http.Request;
import com.dam.http.Response;
import com.dam.lifecycle.LifeCycle;
import com.dam.server.Server;

/**
 * Created by geeche on 2018/1/26.
 */
public interface Handler extends LifeCycle {

    public void handle(Request baseRequest, Response baseResponse);

    public Server getServer();

    public void setServer(Server server);

    public void destroy();

}
