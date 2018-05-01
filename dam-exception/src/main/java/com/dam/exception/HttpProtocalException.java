package com.dam.exception;

public class HttpProtocalException extends RuntimeException{

    public HttpProtocalException(){
        super();
    }

    public HttpProtocalException(String msg){
        super(msg);
    }

}