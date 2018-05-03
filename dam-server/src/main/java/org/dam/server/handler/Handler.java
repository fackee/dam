package org.dam.server.handler;

import org.dam.server.Server;
import org.dam.http.Request;
import org.dam.http.Response;
import org.dam.utils.lifecycle.LifeCycle;

/**
 * Created by geeche on 2018/1/26.
 */
public interface Handler extends LifeCycle {

    public boolean handle(Request baseRequest, Response baseResponse);

    public Server getServer();

    public void setServer(Server server);

    public void destroy();

}
