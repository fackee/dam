package com.dam.io.nio.buffer;

import java.nio.ByteBuffer;

/**
 * Created by zhujianxin on 2018/3/7.
 */
public class NioBuffer implements Buffer {

    private final ByteBuffer byteBuffer;

    private static final int DEFAULT_BUFFER_SIZE = 4*1024;

    public NioBuffer(){
        this(DEFAULT_BUFFER_SIZE);
    }

    public NioBuffer(int size){
        this(size,true);
    }

    public NioBuffer(int size,boolean direct){
        if(direct){
            byteBuffer = ByteBuffer.allocateDirect(size);
        }else{
            byteBuffer = ByteBuffer.allocate(size);
        }
        byteBuffer.clear();
        byteBuffer.position(0);
    }

    @Override
    public int fill(byte b) {
        byteBuffer.put(b);
        return b;
    }

    @Override
    public int fill(byte[] bytes) {
        int len = bytes.length;
        return fill(bytes,0,len);
    }
    @Override
    public int fill(byte[] bytes, int offset, int length) {
        byteBuffer.put(bytes,offset,length);
        return length;
    }

    @Override
    public int capacity() {
        return byteBuffer.capacity();
    }

    @Override
    public int space() {
        return byteBuffer.capacity()-byteBuffer.position();
    }


    @Override
    public int position() {
        return byteBuffer.position();
    }

    @Override
    public void clear() {
        byteBuffer.clear();
    }

    @Override
    public void compact() {
        byteBuffer.compact();
    }

    @Override
    public void flip() {
        byteBuffer.flip();
    }

    @Override
    public ByteBuffer byteBuffer() {
        return this.byteBuffer;
    }

    @Override
    public byte[] bytes() {
        return bytes(0,position());
    }

    @Override
    public byte[] bytes(int offset, int length) {
        final byte[] dst = new byte[length];
        byteBuffer.flip();
        byteBuffer.get(dst,offset,length);
        return dst;
    }
}
