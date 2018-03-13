package httpserver.connector;

import httpserver.connector.nio.NioSelectorManager;
import httpserver.connector.nio.SelectorManager;
import httpserver.core.Server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Created by geeche on 2018/2/3.
 */
public class NioConnector extends AbstractConnector{

    private ServerSocketChannel acceptorChannel;
    private int localPort=-1;

    private final SelectorManager selectorManager = new NioSelectorManager();

    public NioConnector(Server server){
        super(server);
        addBean(selectorManager,true);
        setAcceptors(Runtime.getRuntime().availableProcessors()-2 <= 0 ? 1 : Runtime.getRuntime().availableProcessors()-2);
    }

    @Override
    public void doStart() throws Exception {
        selectorManager.setWorkLength(getAcceptors());
        super.doStart();
    }

    @Override
    public void open() throws IOException {
        synchronized (this){
            if(acceptorChannel == null){
                acceptorChannel = ServerSocketChannel.open();
                acceptorChannel.configureBlocking(true);
                acceptorChannel.socket().setReuseAddress(getReuseAddress());
                InetSocketAddress address = getHost() == null ? new InetSocketAddress(getPort()) : new InetSocketAddress(getHost(),getPort());
                acceptorChannel.socket().bind(new InetSocketAddress(8888));
                localPort = acceptorChannel.socket().getLocalPort();
                if(localPort < 0){
                    throw new IOException("Server channel not bound");
                }
                addBean(acceptorChannel);
            }
        }
    }

    @Override
    public void close() throws IOException {

    }

    @Override
    protected void accept(int acceptId) throws IOException, InterruptedException {
        ServerSocketChannel serverSocketChannel;
        synchronized (this){
            serverSocketChannel = acceptorChannel;
        }
        if(serverSocketChannel != null && serverSocketChannel.isOpen() && selectorManager.isStarted()){
            SocketChannel socketChannel = serverSocketChannel.accept();
            socketChannel.configureBlocking(false);
            Socket socket = socketChannel.socket();
            configure(socket);
            selectorManager.register(socketChannel);
        }
    }


    class NioSelectorManager extends SelectorManager{

        @Override
        public void endPointUpgraded(SelectChannelEndPoint selectChannelEndPoint, Connection old) {

        }

        @Override
        public boolean dispatch(Runnable runnable) {
            return getServer().getWorkerService().dispatch(runnable);
        }
    }
}
