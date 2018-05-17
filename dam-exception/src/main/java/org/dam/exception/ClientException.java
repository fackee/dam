package org.dam.exception;

public class ClientException extends RuntimeException{

    public ClientException(){
        super();
    }

    public ClientException(String msg){
        super(msg);
    }
}
