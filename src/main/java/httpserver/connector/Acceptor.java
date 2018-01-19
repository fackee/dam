package httpserver.connector;

import java.io.IOException;

public interface Acceptor {

    public void accept() throws IOException;

    public boolean isAccept();

    public Long getConnetorCount();

}