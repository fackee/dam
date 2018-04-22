package httpserver.handler;

import httpserver.core.Server;
import httpserver.http.Request;
import httpserver.http.Response;
import httpserver.http.HttpField;

/**
 * Created by geeche on 2018/1/27.
 */
public class AppLoadHandler extends AbstractHandler{

    public AppLoadHandler(){

    }

    @Override
    public void handle(Request baseRequest, Response baseResponse) {
        baseResponse.setContent(baseResponse.getContent()+"appload over");
    }
}
