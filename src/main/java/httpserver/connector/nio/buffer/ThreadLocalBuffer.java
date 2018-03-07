package httpserver.connector.nio.buffer;

/**
 * Created by zhujianxin on 2018/3/7.
 */
public class ThreadLocalBuffer{

    private final ThreadLocal<NioBuffer> nioBufferThreadLocal = new ThreadLocal<NioBuffer>();

    public ThreadLocalBuffer(final NioBuffer nioBuffer){
        nioBufferThreadLocal.set(nioBuffer);
    }

    private NioBuffer getNioBuffer(){
        return nioBufferThreadLocal.get();
    }







    class BufferHandlerImpl implements BufferHandler{

        @Override
        public String decode() {
            return getNioBuffer().bytes().toString();
        }

        @Override
        public byte[] encode(String resp) {
            return null;
        }
    }

    interface BufferHandler{

        String decode();


        byte[] encode(String resp);

    }

}
