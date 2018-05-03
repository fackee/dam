package org.dam.exception;

public class AppLoadException extends RuntimeException{

    public AppLoadException(){
        super();
    }

    public AppLoadException(String msg){
        super(msg);
    }
}