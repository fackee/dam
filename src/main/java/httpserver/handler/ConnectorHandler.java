package httpserver.handler;

import httpserver.core.AbstractLifeCycle;
import httpserver.core.Request;
import httpserver.core.Response;
import httpserver.core.Server;
import jdk.internal.org.objectweb.asm.Handle;

/**
 * Created by zhujianxin on 2018/3/19.
 */
public class ConnectorHandler extends AbstractLifeCycle implements Handler{




    @Override
    public void handle(String target, Request baseRequest, Response baseResponse) {

    }

    @Override
    public Server getServer() {
        return null;
    }

    @Override
    public void setServer(Server server) {

    }

    @Override
    public void destroy() {

    }
}
