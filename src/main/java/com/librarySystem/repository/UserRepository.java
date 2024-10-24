package main.java.com.librarySystem.repository;

import main.java.com.librarySystem.user.User;

import java.util.ArrayList;
import java.util.List;

public class UserRepository implements IUserRepository {
    List<User> users = new ArrayList<>();

    @Override
    public List<User> getUsers() {
        return users;
    }

    @Override
    public void addUser(User user) {
        users.add(user);
    }

    @Override
    public User getUserByEmail(String email) {
        for(User user : users) {
            if(user.getUserEmail().equalsIgnoreCase(email)){
                return user;
            }
        }
        return null;
    }
}
