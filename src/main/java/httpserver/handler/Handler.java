package httpserver.handler;

import httpserver.core.LifeCycle;
import httpserver.http.Request;
import httpserver.http.Response;
import httpserver.core.Server;

/**
 * Created by geeche on 2018/1/26.
 */
public interface Handler extends LifeCycle{

    public void handle(Request baseRequest, Response baseResponse);

    public Server getServer();

    public void setServer(Server server);

    public void destroy();

}
