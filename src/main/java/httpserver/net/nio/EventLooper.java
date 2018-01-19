package httpserver.net.nio;

import httpserver.message.ByteBufferHandler;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class EventLooper {

    private static final ByteBuffer READ_BUFFER = ByteBuffer.allocate(5024);

    private static final ByteBuffer WTITE_BUFFER = ByteBuffer.allocate(5024);

    private static final ByteBufferHandler readByteBufferHandler = new ByteBufferHandler(READ_BUFFER);

    private static final ByteBufferHandler writeByteBufferHandler = new ByteBufferHandler(WTITE_BUFFER);

    public static void run(Selector selector) throws IOException{
        NioChannel nextNioChannel = EventQueue.deQueue();
        while (nextNioChannel != null){
            if(nextNioChannel.getEventKey() == SelectionKey.OP_READ){
                new ReadHandler(readByteBufferHandler) {
                    @Override
                    public void registerWriteEvent() {

                    }
                    @Override
                    public void execute(final NioChannel nioChannel) {
                        try {
                            nioChannel.read(readByteBufferHandler);
                        } catch (IOException e) {

                        }
                    }
                }.execute(nextNioChannel);
                System.out.println("readFromByteBufferHandler\n"+new String(readByteBufferHandler.getMessage()));
            }
            if(nextNioChannel.getEventKey() == SelectionKey.OP_WRITE){
                new WriteHandler(WTITE_BUFFER) {
                    @Override
                    public void close() {

                    }

                    @Override
                    public void execute(final NioChannel nioChannel) {
                        try {
                            nioChannel.write(writeByteBufferHandler);
                        } catch (IOException e) {

                        }
                    }
                }.execute(nextNioChannel);
            }
            NioChannel newChannel = nextNioChannel.register(selector);
            if(newChannel != null){
                EventQueue.enQueue(newChannel);
            }
            nextNioChannel = EventQueue.deQueue();
        }
    }

}