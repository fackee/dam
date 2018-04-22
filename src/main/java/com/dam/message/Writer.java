package com.dam.message;

import java.io.IOException;

public interface Writer {

    public void write(ByteBufferHandler byteBufferHandler)throws IOException;

}