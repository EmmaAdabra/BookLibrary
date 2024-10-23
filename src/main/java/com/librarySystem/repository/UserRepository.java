package main.java.com.librarySystem.repository;

import main.java.com.librarySystem.user.RUser;

import java.util.ArrayList;
import java.util.List;

public class UserRepository implements IUserRepository {
    List<RUser> users = new ArrayList<>();

    @Override
    public List<RUser> getUsers() {
        return users;
    }

    @Override
    public void add(RUser user) {
        users.add(user);
    }

    @Override
    public RUser getUserByEmail(String email) {
        for(RUser user : users) {
            if(user.getUserEmail().equalsIgnoreCase(email)){
                return user;
            }
        }
        return null;
    }
}
