package com.dam.handler;

import com.dam.core.LifeCycle;
import com.dam.core.Server;
import com.dam.http.Request;
import com.dam.http.Response;

/**
 * Created by geeche on 2018/1/26.
 */
public interface Handler extends LifeCycle {

    public void handle(Request baseRequest, Response baseResponse);

    public Server getServer();

    public void setServer(Server server);

    public void destroy();

}
