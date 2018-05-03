package org.dam.exception;

public class HttpParserException extends RuntimeException{

    public HttpParserException(){}

    public HttpParserException(String msg){
        super(msg);
    }
}