package org.dam.server.response.generator;

import org.dam.http.Request;
import org.dam.http.Response;
import org.dam.http.HttpHelper;
import org.dam.server.handler.HandleWrapper;
import org.dam.server.response.*;

public class ResponseGenerator {

    private ResponseStrategy responseStrategy;
    private Request request;
    private Response response;
    private String statusCode;
    private Object extend;
    private HandleWrapper.HandleResult handleResult;
    public ResponseGenerator(HandleWrapper.HandleResult handleResult, Request request, Response response){
        this.handleResult = handleResult;
        this.request = request;
        this.response = response;
        this.statusCode = this.handleResult.getResultStatus();
        this.extend = this.handleResult.getExtend();
    }

    public void generate(){
        if(HttpHelper.is20X(statusCode)){
            responseStrategy = new OKResponseStrategy(handleResult,request,response);
        }
        if(HttpHelper.is30X(statusCode)){
            responseStrategy = new RedirectReponseStrategy(request,response,(String) extend);
        }
        if(HttpHelper.is40X(statusCode)){
            responseStrategy = new ClientErrorResponseStrategy(request,response,HttpHelper.handleError((String)extend));
        }
        if(HttpHelper.is50X(statusCode)){
            responseStrategy = new ServerErrorResponseStrategy(request,response,HttpHelper.handleError((String)extend));
        }
        responseStrategy.doGenerate();
    }
}
