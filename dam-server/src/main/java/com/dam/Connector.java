package com.dam;


import com.dam.lifecycle.LifeCycle;

import java.io.IOException;

/**
 * Created by geeche on 2018/1/25.
 */
public interface Connector extends LifeCycle {

    String getName();

    void open() throws IOException;

    void close() throws IOException;

    void setServer(Server server);

    Server getServer();


    String getHost();

    void setHost(String hostName);

    void setPort(int port);

    int getPort();


    Object getConnection();

}
