package org.dam.server.handler;

import org.dam.http.Request;
import org.dam.http.Response;

/**
 * Created by geeche on 2018/1/27.
 */
public class SessionHandler extends AbstractHandler {

    public SessionHandler(){

    }

    @Override
    public boolean handle(Request baseRequest, Response baseResponse) {
        return true;
    }
}
