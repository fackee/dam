package com.webapp.controller;

import com.toylet.annotation.Request;
import com.toylet.annotation.Toylet;

import java.util.HashMap;
import java.util.Map;

@Toylet
public class Dynamic {

    @Request(url = "/dynamic")
    public Map<String,Object> dynamic(){
        Map<String,Object> result= new HashMap<>(1);


        return result;
    }
}