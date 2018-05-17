package org.dam.server.handler;

import org.dam.http.Request;
import org.dam.http.Response;
import org.dam.utils.util.log.Logger;

/**
 * Created by geeche on 2018/1/27.
 */
public class SessionHandler extends AbstractHandler {

    public SessionHandler(){

    }

    @Override
    public boolean handle(Request baseRequest, Response baseResponse) {
        Logger.INFO("SessionHandle execute sessionInfo");
        return true;
    }
}
