package main.java.com.librarySystem.auth;

import main.java.com.librarySystem.util.NewResponse;

public interface IAuthServices {
    NewResponse verifyLogin(String email, String password);
}
