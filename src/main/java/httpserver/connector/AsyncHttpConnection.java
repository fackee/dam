package httpserver.connector;

import httpserver.core.Server;

/**
 * Created by geeche on 2018/2/23.
 */
public class AsyncHttpConnection implements Connection{

    private Server server;

    @Override
    public void setServer(Server server) {
        this.server = server;
    }

    @Override
    public Server getServer() {
        return server;
    }
}
