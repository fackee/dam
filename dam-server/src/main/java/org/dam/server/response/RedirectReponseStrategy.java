package org.dam.server.response;

import org.dam.http.Request;
import org.dam.http.Response;
import org.dam.http.constant.HttpConstant;
import org.dam.http.constant.HttpMedia;

import static org.dam.constant.Constants.STATUS;

public class RedirectReponseStrategy extends AbstractReponseStrategy{

    private String extend;

    public RedirectReponseStrategy(Request request,Response response, String extend){
        super(request,response);
        this.extend = extend;
    }

    @Override
    public void doGenerate() {
        super.doGenerate();
        response.setHttpHeader(STATUS,HttpConstant.HttpStatusCode.FOUND.getDesc());
        response.setHttpHeader(HttpConstant.HttpEntity.Content_Type.toString(),HttpMedia.Accept.TEXT_HTML.getAccept());
        response.setHttpHeader(HttpConstant.HttpResponseLine.Location.getResponseLine(),extend);
    }
}
