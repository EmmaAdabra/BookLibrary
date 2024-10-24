package main.java.com.librarySystem.user;

import main.java.com.librarySystem.model.Role;

import java.util.Objects;

public class User {
    private String userName;
    private String userEmail;
    private String userPassword;
    private Role role;

    private boolean isLoggedIn;

    public User(String name, String email, String password, Role role) {
        this.userName = name;
        this.userEmail = email;
        this.userPassword = password;
        this.role = role;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public Role getRole() {
        return role;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void login(){
        this.isLoggedIn = true;
    }

    public void logout(){
        this.isLoggedIn = false;
    }

    @Override
    public String toString() {
        return
                "Name: " + userName + "\n" +
                "Email: " + userEmail + "\n" +
                "Role: " + role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return getUserEmail().equals(user.getUserEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserEmail());
    }
}
