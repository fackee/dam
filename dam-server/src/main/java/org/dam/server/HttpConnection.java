package org.dam.server;

import org.dam.http.*;
import org.dam.http.bean.HttpField;
import org.dam.http.HttpGenerate;
import org.dam.io.EndPoint;
import org.dam.io.connection.Connection;
import org.dam.utils.util.log.Logger;

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
        request.setCookies(getHttpField().getCookies());
        request.setMultipart(getHttpField().getMultipart());
        getServer().handle(request,response);
        try {
            if(!getEndPoint().blockWritable(5000)){
                Logger.INFO("wakeup from write and continue generate http response{},{}",getEndPoint(),response);
                HttpGenerate httpGenerate = new HttpGenerate(getEndPoint(),new HttpField.HttpBuilder().builde(),response).generate();
            }
        } catch (IOException e) {

        }finally {
            try {
                getEndPoint().close();
                Logger.INFO("http finished close endpoint:{}",getEndPoint());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return this;
    }
}
