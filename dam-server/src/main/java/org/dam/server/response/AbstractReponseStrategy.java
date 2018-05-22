package org.dam.server.response;

import org.dam.http.Request;
import org.dam.http.Response;
import org.dam.http.constant.HttpConstant;
import org.dam.utils.config.Configuration;
import org.dam.utils.util.StringUtil;

import java.util.Date;

import static org.dam.constant.Constants.VERSION;

public abstract class AbstractReponseStrategy implements ResponseStrategy{

    protected Response response;
    protected Request request;
    protected byte[] body;
    public AbstractReponseStrategy(){}

    public AbstractReponseStrategy(Request request,Response response){
        this.response = response;
        this.request = request;
    }
    public AbstractReponseStrategy(Request request,Response response,byte[] body){
        this.request = request;
        this.response = response;
        this.body = body;
    }

    @Override
    public void doGenerate(){
        response.setHttpHeader(VERSION,request.getHeader().getRequestHeader().getUrl().getHttpVersion());
        response.setHttpHeader(HttpConstant.HttpResponseLine.Server.toString(),
                Configuration.DefaultConfig.getInstance().getServerName());
        response.setHttpHeader(HttpConstant.HttpGeneralHeader.Date.getGeneralHeader(),StringUtil.getGMTDate(new Date()));
        if(hasBody()){
            response.setBodyBytes(body);
            response.setHttpHeader(HttpConstant.HttpEntity.Content_Length.toString(), String.valueOf(body.length));
        }
    }

    protected boolean hasBody(){
        return body != null && body.length > 0;
    }
}
