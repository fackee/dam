package com.dam.handler;

import com.dam.http.Request;
import com.dam.http.Response;

/**
 * Created by geeche on 2018/1/27.
 */
public class SessionHandler extends AbstractHandler {

    public SessionHandler(){

    }

    @Override
    public boolean handle(Request baseRequest, Response baseResponse) {
        baseResponse.setContent(baseResponse.getContent());
        return true;
    }
}
