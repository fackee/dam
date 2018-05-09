package org.dam.server;

import org.dam.http.*;
import org.dam.http.util.HttpGenerate;
import org.dam.io.EndPoint;
import org.dam.io.connection.Connection;
import java.io.IOException;

/**
 * Created by geeche on 2018/2/23.
 */
public class HttpConnection extends AbstractHttpConnection {

    private Server server;
    private Request request;
    private HttpResponse response;
    private Connector connector;

    public HttpConnection(Connector nioConnector, EndPoint endPoint, Server server) {
        super(endPoint,server,nioConnector);
        this.server = server;
        this.connector = nioConnector;
        request = new HttpRequest();
        response = new HttpResponse();

    }

    @Override
    public Connection handle() {
        super.handle();
        request.setHttpHeader(getHttpField().getHttpHeader());
        request.setHttpParameter(getHttpField().getHttpParameter());
        request.setCookie(getHttpField().getCookie());
        request.setSession(getHttpField().getSession());
        getServer().handle(request,response);
        try {
            if(!getEndPoint().blockWritable(10000)){
                System.out.println("write Event OK? write back");
                HttpGenerate httpGenerate = new HttpGenerate(getEndPoint(),new HttpField.HttpBuilder().builde(),response).generate();
            }
        } catch (IOException e) {

        }finally {
            try {
                getEndPoint().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return this;
    }
}
