package main.java.com.librarySystem.repository;

import main.java.com.librarySystem.user.RUser;

import java.util.List;

public interface IUserRepository {
    List<RUser> getUsers();

    void add(RUser user);
    RUser getUserByEmail(String email);
}
