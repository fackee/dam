package httpserver.connector;

import httpserver.core.Server;

/**
 * Created by geeche on 2018/2/23.
 */
public interface Connection {


    void setServer(Server server);

    Server getServer();

}
