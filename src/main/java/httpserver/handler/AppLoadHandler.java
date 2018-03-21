package httpserver.handler;

import httpserver.core.Request;
import httpserver.core.Response;
import httpserver.http.HttpField;
import httpserver.http.util.Parser;

/**
 * Created by geeche on 2018/1/27.
 */
public class AppLoadHandler extends AbstractHandler{

    private String message;

    private HttpField httpField;

    public AppLoadHandler(String message, HttpField httpField){
        this.message = message;
        this.httpField = httpField;
    }

    @Override
    public void handle(String target, Request baseRequest, Response baseResponse) {

    }

    @Override
    public void destroy() {

    }


}
