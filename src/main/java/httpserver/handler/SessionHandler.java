package httpserver.handler;

import httpserver.http.Request;
import httpserver.http.Response;

/**
 * Created by geeche on 2018/1/27.
 */
public class SessionHandler extends AbstractHandler{

    public SessionHandler(){

    }

    @Override
    public void handle(Request baseRequest, Response baseResponse) {
        baseResponse.setContent(baseResponse.getContent()+"session over");
    }
}
