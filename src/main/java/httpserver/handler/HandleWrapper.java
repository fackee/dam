package httpserver.handler;

import httpserver.exception.HttpProtocalException;
import httpserver.http.*;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by geeche on 2018/1/27.
 */
public class HandleWrapper extends AbstractHandler{

    private final List<Handler> handlerChain;

    public HandleWrapper(LinkedList<Handler> handlers){
        handlerChain = Collections.synchronizedList(handlers);
        addDefaultHandler();
    }

    private void addDefaultHandler(){
        handlerChain.add(new HttpHandler());
        handlerChain.add(new SessionHandler());
        handlerChain.add(new AppLoadHandler());
    }

    @Override
    public void handle(Request request, Response response) {
        for(Handler handler : handlerChain){
            try {
                handler.handle(request,response);
            }catch (Exception e){
                break;
            }
        }
    }
}
