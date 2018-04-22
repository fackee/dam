package com.dam.connector.nio.buffer;

/**
 * Created by zhujianxin on 2018/3/7.
 */
public class ThreadLocalBuffer{

    private final ThreadLocal<NioBuffer> nioBufferThreadLocal = new ThreadLocal<NioBuffer>(){
        @Override
        protected NioBuffer initialValue() {
            return new NioBuffer();
        }
    };

    public ThreadLocalBuffer(final int defaultBufferSize){
        if(nioBufferThreadLocal.get() != null){
            nioBufferThreadLocal.remove();
        }
        nioBufferThreadLocal.set(new NioBuffer(defaultBufferSize));
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
    public void fillBuffer(Buffer buffer){
        if(buffer instanceof NioBuffer){
            final NioBuffer nioBuffer = (NioBuffer) buffer;
            if(nioBufferThreadLocal.get() != null){
                nioBufferThreadLocal.remove();
            }
            nioBufferThreadLocal.set(nioBuffer);
        }
    }

    public byte[] getData(){
        final byte[] data = nioBufferThreadLocal.get().bytes();
        if(data != null){
            return data;
        }
        return new String("request data null").getBytes();
    }

    public enum BufferSize{

        REQUEST_BUFFER_SIZE(1024*16),

        RESPONSE_BUFFER_SIZE(32*1024);

        public int size;
        BufferSize(int size) {
            this.size = size;
        }

        public int getSize(){
            return size;
        }
    }


}
