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
    private String UserName;
    private String UserEmail;
    private String UserPassword;
    private boolean isOnline;
    private ArrayList<Object> borrowedBook = new ArrayList<>();

    public User(String name, String email, String password) {
        this.UserName = name;
        this.UserEmail = email;
        this.UserPassword = password;
    }

    public void login(String email, String password) {
        isOnline = true;
    };
    public void logout() {
        isOnline = false;
    };
}
