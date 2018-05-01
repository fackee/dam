package com.dam.handler;

import com.dam.Server;
import com.dam.http.Request;
import com.dam.http.Response;
import com.dam.lifecycle.LifeCycle;

/**
 * Created by geeche on 2018/1/26.
 */
public interface Handler extends LifeCycle {

    public boolean handle(Request baseRequest, Response baseResponse);

    public Server getServer();

    public void setServer(Server server);

    public void destroy();

}
