package com.dam.io;

import com.dam.io.connection.Connection;

/**
 * Created by geeche on 2018/2/23.
 */
public interface ConnectedEndPoint extends EndPoint {

    public void setConnection(Connection connection);

    public Connection getConnection();


}
