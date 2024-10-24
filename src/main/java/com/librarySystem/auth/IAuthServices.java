package main.java.com.librarySystem.auth;

import main.java.com.librarySystem.util.Response;

public interface IAuthServices {
    Response verifyLogin(String email, String password);
}
