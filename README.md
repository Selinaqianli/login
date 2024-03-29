**LogIn API**
----
Account will be locked out for 15 minutes after exceeding 5 consecutive unsuccessful login attempts to block brute-force attacks.

[Core Code](https://github.com/Selinaqianli/login/blob/main/src/main/java/com/qianli/login/service/LoginServiceImpl.java)

***HTTP Requests***

* ### `/login` ```POST```
    * Response Code: 403
        * Request Body:
      ```yaml
            {
              "email": "",
              "username": "test1",
              "password": "wrongpsw"
            }
      ```
        * Response Body:
      ```yaml
            "Your account has been locked. There are n minutes left to be unlocked."
      ```
        * Description: <br /> The user has been locked, return error message to show the remaining time to be unlocked.
        * Image: <br /> ![](img/loginWhenAccountLocked.png)
    * Response Code: 404
        * Request Body:
      ```yaml
            {
              "email": "",
              "username": "test1",
              "password": "wrongpsw"
            }
      ```
        * Response Body:
      ```yaml
            "The username/email or password is not correct. You have x attempts left."
      ```
        * Description: <br /> Username/email is correct, but the password is wrong, while the account has not exceeded the maximal unsuccessful attempts, return error message to show how many attempts left.
        * Image: <br /> ![](img/loginWhenNotFound2.png)
    * Response Code: 404
        * Request Body:
      ```yaml
            {
              "email": "",
              "username": "test",
              "password": "wrongpsw"
            }
      ```
        * Response Body:
      ```yaml
            "The username/email or password is not correct."
      ```
        * Description: <br /> User does not exist with given username/email, return error message.
        * Image: <br /> ![](img/loginWhenNotFound1.png)
    * Response Code: 400
        * Request Body:
      ```yaml
            {
              "email": "",
              "username": "test1",
              "password": ""
            }
      ```
        * Response Body:
      ```yaml
            "The password cannot be empty"
      ```
        * Description: <br /> Username/email or password is empty, return error message.
        * Image: <br /> ![](img/loginWhenBadRequest.png)
    * Response Code: 200
        * Request Body:
      ```yaml
            {
              "email": "",
              "username": "test1",
              "password": "test1psw"
            }
      ```
        * Response Body:
      ```yaml
            {
              "id": 1,
              "email": "test1@gmail.com",
              "username": "test1",
              "password": "test1psw"
            }
      ```
        * Description: <br /> Username/email and password are matched, return all data of the user.
        * Image: <br /> ![](img/loginWhenOk.png)

* ### `/get-all-users` ```GET```
    * Response Code: 200
        * Request Body: N/A
        * Response Body:
      ```yaml
        [
            {
              "id": 1,
              "email": "test1@gmail.com",
              "username": "test1",
              "password": "test1psw"
            },
            ...
        ]
      ```
        * Description: get all users for testing `/login`
        * Image: <br /> ![](img/getAllUsers.png)

***Unit Tests***

![](img/unitTests.png)
