package httpserver.connector;

import httpserver.connector.EndPoint;
import httpserver.connector.nio.Connection;

/**
 * Created by geeche on 2018/2/23.
 */
public interface ConnectedEndPoint extends EndPoint{

    public void setConnection(Connection connection);

    public Connection getConnection();

}
