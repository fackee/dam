package httpserver.connector.nio.buffer;

/**
 * Created by zhujianxin on 2018/3/7.
 */
public class ThreadLocalBuffer{

    private final ThreadLocal<NioBuffer> nioBufferThreadLocal = new ThreadLocal<NioBuffer>();

    public ThreadLocalBuffer(final NioBuffer nioBuffer){
        nioBufferThreadLocal.set(nioBuffer);
    }

    private final NioBuffer getNioBuffer(){
        return nioBufferThreadLocal.get();
    }

    private final void setNioBuffer(final NioBuffer nioBuffer){
        if(nioBufferThreadLocal.get() != null){
            nioBufferThreadLocal.remove();
        }
        nioBufferThreadLocal.set(nioBuffer);
    }


}
