package com.dam.exception;

public class HttpSessionException extends RuntimeException{

    public HttpSessionException(){
        super();
    }

    public HttpSessionException(String msg){
        super(msg);
    }
}