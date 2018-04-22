package com.dam.connector.nio.buffer;

import java.nio.ByteBuffer;

/**
 * Created by geeche on 2018/2/23.
 */
public interface Buffer {


    int fill(byte b);

    int fill(byte[] bytes);

    int fill(byte[] bytes, int offset, int length);


    int capacity();


    int space();

    int position();

    void clear();


    void compact();


    void flip();

    ByteBuffer byteBuffer();


    byte[] bytes();

    byte[] bytes(int offset, int length);



}
