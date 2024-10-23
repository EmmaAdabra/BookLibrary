package main.java.com.librarySystem.UIs;

import main.java.com.librarySystem.Library;
import main.java.com.librarySystem.user.User;
import main.java.com.librarySystem.util.IterateInput;
import main.java.com.librarySystem.util.Utils;
import main.java.com.librarySystem.util.IValidateInput;

import java.util.List;

public class LoginUser {
    private static final IValidateInput VALIDATE = Utils.validate;
    static List<User> users = Library.users;

    public static void getLoginDetails(){
        System.out.println();
        System.out.println("--------------- Login Details ---------------");
        String email = IterateInput.stringInput("email", VALIDATE::validateEmail);
        String password = IterateInput.stringInput("password", VALIDATE::validatePassword);
        if(!(users.size() == 0)) {
            User user = validateLoginDetails(email, password);
            if(!(user == null)) {
                user.login();
            }
            else
                System.out.println("Invalid login detail");
        }
        else
            System.out.println("No registered user");
    }

    private static User validateLoginDetails(String email, String password){
        if (users.size() > 0) {
            for(User user : users) {
                if(email.equalsIgnoreCase(user.getUserEmail()) && password.equals(user.getUserPassword()))
                    return user;
            }
        }
        return null;
    }
}
