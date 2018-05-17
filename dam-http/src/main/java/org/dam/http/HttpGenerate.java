package org.dam.http;

import org.dam.io.EndPoint;
import org.dam.io.nio.buffer.Buffer;
import org.dam.io.nio.buffer.NioBuffer;
import org.dam.io.nio.buffer.ThreadLocalBuffer;
import org.dam.http.bean.HttpField;
import org.dam.utils.util.log.Logger;

import java.io.IOException;

public class HttpGenerate {

    private EndPoint endPoint;
    private HttpField httpField;
    private HttpResponse response;
    private final ThreadLocalBuffer currentResponseBuffer = new ThreadLocalBuffer(ThreadLocalBuffer.BufferSize.RESPONSE_BUFFER_SIZE.getSize());

    public HttpGenerate(EndPoint endPoint) {
        this(endPoint, new HttpField.HttpBuilder().builde());
    }

    public HttpGenerate(EndPoint endPoint, HttpField httpField) {
        this(endPoint, httpField, new HttpResponse());
    }

    public HttpGenerate(EndPoint endPoint, HttpField httpField, HttpResponse response) {
        this.endPoint = endPoint;
        this.httpField = httpField;
        this.response = response;
    }

    public HttpGenerate generate() throws IOException {
        Logger.INFO("HeaderMessage:\n{}",new String(response.getHeaderBytes()));
        final Buffer header = new NioBuffer(response.getHeaderBytes().length);
        final Buffer body = new NioBuffer(
                response.getBodyBytes() == null ? 0 : response.getBodyBytes().length);
        if (generateComplete(header, body)) {
            flush(header, body);
        }
        return this;
    }

    private boolean generateComplete(Buffer header, Buffer body) {

        final byte[] headerByte = response.getHeaderBytes();
        header.byteBuffer().position(0);
        header.byteBuffer().put(headerByte);
        final byte[] bodyByte = response.getBodyBytes();
        if (bodyByte != null && bodyByte.length > 0) {
            body.byteBuffer().position(0);
            body.byteBuffer().put(bodyByte);
        }
        return true;

    }

    private void flush(Buffer header, Buffer body) {
        try {
            endPoint.flush(header, body);
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

    public void setResponseContent(HttpResponse response) {
        this.response = response;
    }
}