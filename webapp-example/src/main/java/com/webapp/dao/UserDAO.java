package com.webapp.dao;

import com.webapp.model.User;

public class UserDAO {

    public User getUserByName(String name){
        User user = new User();
        user.setUserName(name);
        return user;
    }
}