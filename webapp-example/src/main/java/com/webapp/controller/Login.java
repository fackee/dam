package com.webapp.controller;

import com.dam.http.HttpRequest;
import com.dam.http.Session;
import com.toylet.annotation.Parameter;
import com.toylet.annotation.Request;
import com.toylet.annotation.Toylet;
import com.webapp.service.UserService;

import java.util.HashMap;
import java.util.Map;

@Toylet
public class Login {

    private UserService userService = new UserService();
    @Request(url = "",method = "POST",resultType = "page")
    public Map<String,Object> login(@Parameter(paramName = "uname")String userName, @Parameter(paramName = "pwd")String password, HttpRequest request){
        Session session = request.getSession();

        Map<String,Object> result = new HashMap<>(1,1);
        result.put("success.tsp",userService.userlogic(userName));
        return result;
    }

}