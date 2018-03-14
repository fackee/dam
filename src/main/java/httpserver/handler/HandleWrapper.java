package httpserver.handler;

import httpserver.core.Context;
import httpserver.core.Request;
import httpserver.core.Response;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by geeche on 2018/1/27.
 */
public class HandleWrapper {

    private final List<Handler> handlerChain;

    public HandleWrapper(LinkedList<Handler> handlers){
        handlerChain = Collections.synchronizedList(handlers);
    }


    public void handle() {
        handlerChain.forEach(handler -> {
            handler.handle("",null,null);
        });
    }
}
