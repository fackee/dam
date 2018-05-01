package com.dam.http.util;

import com.dam.io.EndPoint;
import com.dam.io.nio.buffer.Buffer;
import com.dam.io.nio.buffer.NioBuffer;
import com.dam.io.nio.buffer.ThreadLocalBuffer;
import com.dam.http.HttpField;
import com.dam.http.HttpResponse;
import com.dam.http.Response;

import java.io.IOException;

public class HttpGenerate {

    private EndPoint endPoint;
    private HttpField httpField;
    private Response response;
    private final ThreadLocalBuffer currentResponseBuffer = new ThreadLocalBuffer(ThreadLocalBuffer.BufferSize.RESPONSE_BUFFER_SIZE.getSize());
    public HttpGenerate(EndPoint endPoint){
        this(endPoint,new HttpField.HttpBuilder().builde());
    }
    public HttpGenerate(EndPoint endPoint, HttpField httpField){
        this(endPoint,httpField,new HttpResponse());
    }
    public HttpGenerate(EndPoint endPoint, HttpField httpField, Response response){
        this.endPoint = endPoint;
        this.httpField = httpField;
        this.response = response;
    }

    public HttpGenerate generate() throws IOException{
            final Buffer header = new NioBuffer();
            final Buffer body = new NioBuffer();
            if(generateComplete(header,body)){
                flush(header,body);
            }
        return this;
    }

    private boolean generateComplete(Buffer header, Buffer body) {
        final byte[] headerByte = response.getContent();
        header.byteBuffer().put(headerByte);
        return true;
    }

    private void flush(Buffer header, Buffer body){
        try {
            System.out.println("write:"+endPoint);
            endPoint.flush(header,body);
        } catch (IOException e) {

        }
    }

    public EndPoint getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(EndPoint endPoint) {
        this.endPoint = endPoint;
    }

    public HttpField getHttpField() {
        return httpField;
    }

    public void setHttpField(HttpField httpField) {
        this.httpField = httpField;
    }

    public Response getResponseContent() {
        return response;
    }

    public void setResponseContent(Response response) {
        this.response = response;
    }
}