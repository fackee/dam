package httpserver.handler;

import httpserver.core.Context;
import httpserver.core.Request;
import httpserver.core.Response;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by geeche on 2018/1/27.
 */
public class HandleWrapper {

    private final List<Handler> handlers = new CopyOnWriteArrayList<Handler>();


    public void handle() {
    }
}
