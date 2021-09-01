package com.qianli.login;

import com.qianli.login.entity.User;
import com.qianli.login.handler.InvalidLoginCredentialException;
import com.qianli.login.service.LoginServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoginTest {
    @Autowired
    private LoginServiceImpl loginService;
    
    @Test
    public void LoginWithUserNameWhenOk() {
        User user = loginService.login("test1", "", "test1psw");
        Assert.assertEquals(1, user.getId());
        Assert.assertEquals("test1", user.getUsername());
        Assert.assertEquals("test1@gmail.com", user.getEmail());
        Assert.assertEquals("test1psw", user.getPassword());    
    }
    
    @Test
    public void LoginWithEmailWhenOk() {
        User user = loginService.login("", "test1@gmail.com", "test1psw");
        Assert.assertEquals(1, user.getId());
        Assert.assertEquals("test1", user.getUsername());
        Assert.assertEquals("test1@gmail.com", user.getEmail());
        Assert.assertEquals("test1psw", user.getPassword());
    }
    
    @Test
    public void LoginWithEmptyUsernameAndEmailWhenBadRequest() {
        try {
            User user = loginService.login("", null, "test1psw");
        } catch (Exception e) {
            Assert.assertEquals((e instanceof InvalidLoginCredentialException), true);
            Assert.assertEquals(e.getMessage(), "The username/email cannot be empty.");
        }
    }
    
    @Test
    public void LoginWithEmptyPasswordWhenBadRequest() {
        try {
            User user = loginService.login("test1", "", "");
        } catch (Exception e) {
            Assert.assertEquals((e instanceof InvalidLoginCredentialException), true);
            Assert.assertEquals(e.getMessage(), "The password cannot be empty.");
        }
    }
    
    // uncomment to manually test
    /*@Test
    public void LoginWithWrongPassword() {
        try {
            User user = loginService.login("test2", "", "wrongpsw");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }*/
}
