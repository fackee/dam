package org.dam.exception;

/**
 * Created by geeche on 2018/5/6.
 */
public class PropertiesNotFoundException extends RuntimeException{

    public PropertiesNotFoundException(){}

    public PropertiesNotFoundException(String msg){
        super(msg);
    }
}
