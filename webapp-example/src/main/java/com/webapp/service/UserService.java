package com.webapp.service;

import com.webapp.dao.UserDAO;
import com.webapp.model.User;

public class UserService {

    private UserDAO userDAO = new UserDAO();

    public User userlogic(String userName){
        return userDAO.getUserByName(userName);
    }
}