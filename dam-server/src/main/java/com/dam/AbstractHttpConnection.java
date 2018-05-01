package com.dam;

import com.dam.http.HttpField;
import com.dam.http.util.HttpParser;
import com.dam.io.EndPoint;
import com.dam.io.connection.Connection;

import java.io.IOException;

/**
 * Created by zhujianxin on 2018/3/8.
 */
public class AbstractHttpConnection implements Connection {

    private Server server;
    private Connector connector;
    private EndPoint endPoint;
    protected HttpField httpField;
    private HttpParser httpParser;

    public AbstractHttpConnection(EndPoint endPoint,Server server,Connector connector){
        this.server = server;
        this.connector = connector;
        this.endPoint = endPoint;
    }

    @Override
    public Connection handle() {
        System.out.println("dispatch-->handler,super");
        try {
            httpParser = new HttpParser(endPoint).parse();
        } catch (IOException e) {

        }
        httpField = httpParser.getHttpField();
//        try {
////            endPoint.blockWritable(10000L);
//        } catch (IOException e) {
//
//        }
//        try {
//            endPoint.blockWritable(10L);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        ((SelectChannelEndPoint)endPoint).updateKey();
        return this;
    }

    public Server getServer() {
        return server;
    }

    public Connector getConnector() {
        return connector;
    }

    public EndPoint getEndPoint() {
        return endPoint;
    }

    public HttpField getHttpField() {
        return httpField;
    }

    public HttpParser getHttpParser() {
        return httpParser;
    }
}