package httpserver;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by zhujianxin on 2018/3/8.
 */
    public class MultiPortEcho
    {
        private int ports[];
        private ByteBuffer echoBuffer = ByteBuffer.allocate( 1024 );
        private ByteBuffer eb = ByteBuffer.allocate( 1024 );
        String httpResponse = "HTTP/1.1 200 OK\r\n" +
                "Content-Length: 38\r\n" +
                "Content-Type: text/html\r\n" +
                "\r\n" +
                "<html><body>Hello World!</body></html>";
        public MultiPortEcho( int ports[] ) throws IOException {
            this.ports = ports;

            go();
        }

        private void go() throws IOException {
            // Create a new selector
            Selector selector = Selector.open();

            // Open a listener on each port, and register each one
            // with the selector
            for (int i=0; i<ports.length; ++i) {
                ServerSocketChannel ssc = ServerSocketChannel.open();
                ssc.configureBlocking( false );
                ServerSocket ss = ssc.socket();
                InetSocketAddress address = new InetSocketAddress( ports[i] );
                ss.bind( address );

                SelectionKey key = ssc.register( selector, SelectionKey.OP_ACCEPT );

                System.out.println( "Going to listen on "+ports[i] );
            }

            while (true) {
                int num = selector.select();

                Set selectedKeys = selector.selectedKeys();
                Iterator it = selectedKeys.iterator();

                while (it.hasNext()) {
                    SelectionKey key = (SelectionKey)it.next();

                    if ((key.readyOps() & SelectionKey.OP_ACCEPT)
                            == SelectionKey.OP_ACCEPT) {
                        // Accept the new connection
                        ServerSocketChannel ssc = (ServerSocketChannel)key.channel();
                        SocketChannel sc = ssc.accept();
                        sc.configureBlocking( false );

                        // Add the new connection to the selector
                        SelectionKey newKey = sc.register( selector, SelectionKey.OP_READ );
                        it.remove();

                    } else if ((key.readyOps() & SelectionKey.OP_READ)
                            == SelectionKey.OP_READ) {
                        // Read the data
                        SocketChannel sc = (SocketChannel)key.channel();

                        // Echo data
                        int bytesEchoed = 0;
                        sc.read(echoBuffer);
                        WritableByteChannel wbc = (WritableByteChannel)sc;
                        eb.put(httpResponse.getBytes());
                        eb.flip();
                        wbc.write(eb);
                        System.out.println(new String(echoBuffer.array()));
                        it.remove();
                    }else if((key.readyOps() & SelectionKey.OP_WRITE)
                            == SelectionKey.OP_WRITE){
                        SocketChannel sc = (SocketChannel)key.channel();
                        eb.put(httpResponse.getBytes());
                        eb.flip();
                        sc.write(eb);
                        eb.clear();
                    }

                }

            }
        }

        static public void main( String args[] ) throws Exception {


            new MultiPortEcho(new int[]{8888});
        }
    }

