import java.util.ArrayList;

/**
 * Represents a user in the library system.
 *
 * This class maintains information about the user's credentials, online status,
 * and a list of borrowed books.
 *
 * It provides methods for logging in and out.
 */
public class User {
    private String userName;
    private String userEmail;
    private String userPassword;
    private boolean isOnline;
    private ArrayList<Object> borrowedBook = new ArrayList<>();

    public User(String name, String email, String password) {
        this.userName = name;
        this.userEmail = email;
        this.userPassword = password;
    }

    public void login(String email, String password) {
        isOnline = true;
    }
    public void logout() {
        isOnline = false;
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
}
