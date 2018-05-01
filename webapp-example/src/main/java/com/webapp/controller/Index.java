package com.webapp.controller;


import com.toylet.annotation.Parameter;
import com.toylet.annotation.Request;
import com.toylet.annotation.Toylet;
import com.toylet.annotation.URL;

@Toylet
public class Index {
    @Request(url = "/index",method = "GET",resultType = "page")
    public String index(@Parameter(paramName = "userName")String username){
        return "toylet.html";
    }
}