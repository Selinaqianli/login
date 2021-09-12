package com.qianli.login.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qianli.login.api.ILogin;
import com.qianli.login.entity.User;
import com.qianli.login.handler.AccountLockedException;
import com.qianli.login.handler.IncorrectLoginCredentialException;
import com.qianli.login.handler.InvalidLoginCredentialException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements ILogin {
    
    @Autowired
    private RedisTemplate redisTemplate;
    
    // prefix of redis key for counting failed password
    private static String PASSWORD_ERROR_COUNT = "password_error_count_";
    
    @Override
    public User login(final String username, final String email, final String password) {
        try {
            // invalid inputs
            if ((username == null || username.isEmpty()) && (email == null || email.isEmpty())) {
                throw new InvalidLoginCredentialException("The username/email cannot be empty.");
            }
    
            if (password == null || password.isEmpty()) {
                throw new InvalidLoginCredentialException("The password cannot be empty.");
            }
    
            // find the user by username/email, throw an exception if no user exists
            User matchedUser = getUser(username, email);
            if (matchedUser == null) throw new IncorrectLoginCredentialException("The username/email or password is not correct.");
            
            // generate the redis key for the user
            String redisKey = PASSWORD_ERROR_COUNT + username;
            Integer errorCount = (Integer) redisTemplate.opsForValue().get(redisKey);
            if (errorCount == null) {
                // redisKey doesn't exist, then generate a key that exists for 15 min
                errorCount = 0;
                redisTemplate.opsForValue().set(redisKey, errorCount, 15L, TimeUnit.MINUTES);
            }
            
            // check if the account is locked
            if (errorCount >= 5) {
                Long expireTime = redisTemplate.getExpire(redisKey);
                BigDecimal bigDecimal = new BigDecimal(expireTime/60).setScale(0, BigDecimal.ROUND_CEILING);
                throw new AccountLockedException("Your account has been locked. There are " + bigDecimal + " minutes left to be unlocked.");
            }
            
            // check the password
            if (matchedUser.getPassword().equals(password)) {
                // the password is correct
                redisTemplate.delete(redisKey);
                return matchedUser;
            } else {
                // the password is not correct, count the error
                errorCount++;
                redisTemplate.opsForValue().set(redisKey, errorCount, 15L, TimeUnit.MINUTES);
                throw new IncorrectLoginCredentialException("Your username or password is not correct. You have " + (5-errorCount) + " times attempts.");
            }
        } catch (Exception e) {
            throw e;
        }
    }
    
    @Override
    public List<User> getAllUsers() {
        try {
            String usersInString = new String(Files.readAllBytes(Paths.get("src/main/java/com/qianli/login/data/TestUsers.json")), StandardCharsets.UTF_8);
            ObjectMapper mapper = new ObjectMapper();
            List<User> users = mapper.readValue(usersInString, new TypeReference<List<User>>(){});
            
            return users;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    private User getUser(String username, String email) {
        if (username.isEmpty() && email.isEmpty()) return null;
        
        List<User> users = getAllUsers();
        for(User user: users) {
            if (user.getUsername().equals(username) || user.getEmail().equals(email)) return user;
        }
        return null;
    }
}
