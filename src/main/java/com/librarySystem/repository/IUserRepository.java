package main.java.com.librarySystem.repository;

import main.java.com.librarySystem.user.User;

import java.util.List;

public interface IUserRepository {
    List<User> getUsers();

    void add(User user);
    User getUserByEmail(String email);
}
