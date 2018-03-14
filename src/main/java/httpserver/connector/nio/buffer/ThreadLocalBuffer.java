package httpserver.connector.nio.buffer;

/**
 * Created by zhujianxin on 2018/3/7.
 */
public class ThreadLocalBuffer{

    private final ThreadLocal<NioBuffer> nioBufferThreadLocal = new ThreadLocal<NioBuffer>();

    public ThreadLocalBuffer(final NioBuffer nioBuffer){
        nioBufferThreadLocal.set(nioBuffer);
    }

    public NioBuffer getNioBuffer(){
        return nioBufferThreadLocal.get();
    }

    public void setNioBuffer(final NioBuffer nioBuffer){
        if(nioBufferThreadLocal.get() != null){
            nioBufferThreadLocal.remove();
        }
        nioBufferThreadLocal.set(nioBuffer);
    }


    public enum BufferSize{

        REQUEST_BUFFER_SIZE(1024*16),

        RESPONSE_BUFFER_SIZE(32*1024);

        BufferSize(int size) {}
    }

}
