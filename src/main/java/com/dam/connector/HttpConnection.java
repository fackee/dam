package com.dam.connector;

import com.dam.core.Server;
import com.dam.http.*;
import com.dam.http.util.HttpGenerate;

import java.io.IOException;

/**
 * Created by geeche on 2018/2/23.
 */
public class HttpConnection extends AbstractHttpConnection{

    private Request request;
    private Response response;


    public HttpConnection(Connector connector, EndPoint endPoint, Server server) {
        super(server,connector,endPoint);
        request = new HttpRequest(new HttpHeader.HttpHeaderBuilder().builde());
        response = new HttpResponse();
    }

    @Override
    public Connection handle() {
        super.handle();
        getServer().handle(request,response);
        try {
            if(!getEndPoint().blockWritable(10000)){
                System.out.println("write Event OK? write back");
                HttpGenerate httpGenerate = new HttpGenerate(getEndPoint(),new HttpField.HttpBuilder().builde(),response).generate();
            }
        } catch (IOException e) {

        }
        return this;
    }
}
