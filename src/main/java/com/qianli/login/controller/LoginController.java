package com.qianli.login.controller;

import com.qianli.login.api.ILogin;
import com.qianli.login.entity.User;
import com.qianli.login.handler.AccountLockedException;
import com.qianli.login.handler.IncorrectLoginCredentialException;
import com.qianli.login.handler.InvalidLoginCredentialException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<User>> GetAllUsers()   
    {
        return new ResponseEntity<>(_login.getAllUsers(), HttpStatus.OK);
    }
    
    @PostMapping(value = "/login")
    public ResponseEntity<Object> login(@RequestBody User user){
        try {
            return new ResponseEntity<>(_login.login(user.getUsername(), user.getEmail(), user.getPassword()), HttpStatus.OK);
        } catch (AccountLockedException accountLockedException) {
            return new ResponseEntity<>(accountLockedException.getMessage(), HttpStatus.FORBIDDEN);
        } catch (IncorrectLoginCredentialException incorrectLoginCredentialException) {
            return new ResponseEntity<>(incorrectLoginCredentialException.getMessage(), HttpStatus.NOT_FOUND);
        } catch (InvalidLoginCredentialException invalidLoginCredentialException) {
            return new ResponseEntity<>(invalidLoginCredentialException.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
