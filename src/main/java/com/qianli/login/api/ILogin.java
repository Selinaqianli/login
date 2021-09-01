package com.qianli.login.api;

import com.qianli.login.entity.User;

import java.util.List;

public interface ILogin {
    User login(String username, String email, String password);
    
    List<User> getAllUsers();
}
