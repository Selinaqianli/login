package com.qianli.login.entity;

public class User {
    private long id;
    private String email;
    private String username;
    private String password;
    
    public long getId() {
        return id;
    }
    
    public void setId(final long id) {
        this.id = id;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(final String email) {
        this.email = email;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(final String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(final String password) {
        this.password = password;
    }
}
