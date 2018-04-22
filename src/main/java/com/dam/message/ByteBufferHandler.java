package com.dam.message;

import java.nio.ByteBuffer;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

public class ByteBufferHandler {

    private volatile boolean readFlip = false;

    private ByteBuffer readBuffer;

    private volatile int readPosition = 0;

    private volatile int writePosition = 0;

    private final Queue<Integer> writeMarker = new LinkedBlockingDeque<Integer>(1024);

    public ByteBufferHandler(ByteBuffer readBuffer){
        this.readBuffer = readBuffer;
    }

    public int getCapacity(){
        return readBuffer.capacity();
    }

    public int getPosition(){
        return readBuffer.position();
    }

    public int getReadPosition(){
        return readPosition;
    }

    public int getWritePosition(){
        return writePosition;
    }

    public synchronized boolean expendMessage(byte[] message){
        int messageLength = message.length;
        if(writePosition + messageLength > getCapacity()){
            readFlip = true;
            if(messageLength - getCapacity() + writePosition > readPosition){
                return false;
            }
            int messageIndex = 0;
            while (messageIndex < messageLength){
                readBuffer.put(writePosition,message[messageIndex++]);
                writePosition = ( writePosition +1 )%getCapacity();
            }
            writeMarker.offer(messageLength);
            return true;
        }
        if(readFlip){
            if(readPosition < writePosition){
                readFlip = false;
            }
            if(writePosition + messageLength > readPosition){
                return false;
            }
            int messageIndex = 0;
            while (messageIndex < messageLength ){
                readBuffer.put(writePosition++,message[messageIndex++]);
            }
            writeMarker.offer(messageLength);
            return true;
        }
        int messageIndex = 0;
        while (messageIndex < messageLength ){
            readBuffer.put(writePosition++,message[messageIndex++]);
        }
        writeMarker.offer(messageLength);
        return true;
    }

    public synchronized byte[] getMessage(){
        if(writeMarker.size() == 0){
            return null;
        }
        int nextMessageLength = writeMarker.poll();
        byte[] dst = new byte[nextMessageLength];
        int dstIndex = 0;
        while (dstIndex < nextMessageLength){
            dst[dstIndex++] = readBuffer.get(readPosition);
            readPosition = (readPosition+1)%getCapacity();
        }
        return dst;
    }


}