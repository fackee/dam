package httpserver.net.nio;

public interface EventHandler {

    public void execute(final NioChannel nioChannel);

}