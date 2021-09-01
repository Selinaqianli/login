package com.qianli.login.controller;

import com.qianli.login.api.ILogin;
import com.qianli.login.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LoginController {
    @Autowired
    private ILogin _login;
    
    @GetMapping(value = "/get-all-users")
    public List<User> GetAllUsers()   
    {
        return _login.getAllUsers();
    }
    
    @PostMapping(value = "/login")
    public User login(@RequestBody User user){
        return _login.login(user.getUsername(), user.getEmail(), user.getPassword());
    }
}
