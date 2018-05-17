package org.dam.server.response;

import org.dam.http.Request;
import org.dam.http.Response;
import org.dam.http.constant.HttpConstant;
import org.dam.http.constant.HttpMedia;

import static org.dam.constant.Constants.STATUS;

public class ServerErrorResponseStrategy extends AbstractReponseStrategy{


    public ServerErrorResponseStrategy(Request request,Response response,byte[] body){
        super(request,response,body);
    }
    @Override
    public void doGenerate() {
        super.doGenerate();
        response.setHttpHeader(STATUS,HttpConstant.HttpStatusCode.Internal_Server_Error.getDesc());
        response.setHttpHeader(HttpConstant.HttpEntity.Content_Type.toString(),HttpMedia.Accept.TEXT_HTML.getAccept());
    }
}
