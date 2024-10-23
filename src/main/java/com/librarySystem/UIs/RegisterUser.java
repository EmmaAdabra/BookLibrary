package main.java.com.librarySystem.UIs;

import main.java.com.librarySystem.Library;
import main.java.com.librarySystem.admin.Admin;
import main.java.com.librarySystem.user.User;
import main.java.com.librarySystem.util.IterateInput;
import main.java.com.librarySystem.util.IValidateInput;

public class RegisterUser {
    private String name;
    private String email;
    private String password;
//    private List<User> users = new ArrayList<>();
    private IValidateInput validateInput;
    public RegisterUser(IValidateInput validateInput) {
        this.validateInput = validateInput;
    }

    public void registrationUI() {
        int option;
        while (true) {
            System.out.println();
            System.out.println("---------------- Sign up / Login ---------------");
            System.out.println("1. Sign up");
            System.out.println("2. Login");
            System.out.println("0. Quit");
            System.out.println();
            option = IterateInput.intInput("option", 0, 2, validateInput::validateOption);
            switch (option) {
                case 1 -> getUserDetails();
                case 2 -> LoginUser.getLoginDetails();
                case 0 -> System.exit(0);
            }
        }
    }

    public void getUserDetails() {
        System.out.println();
        System.out.println("Enter your details");
        setName();
        setEmail();
        setPassword();

//        check if user is registered
        if(!isRegistered(email)) {
            chooseAccountType();
        } else {
            System.out.println("User already exist, enter option 2 to login");
        }
    }

    private void chooseAccountType() {
        int userOption;
        System.out.println();
        System.out.println("Choose account type:");
        System.out.println("1. User");
        System.out.println("2. Admin");
        userOption = IterateInput.intInput("Account type", 1, 2, validateInput::validateOption);
        createAccount(userOption);
    }

    private void createAccount(int accountType) {
        if (accountType == 2) {
            Admin admin = new Admin(name, email, password, validateInput);
            Library.users.add(admin);
        } else {
            User user = new User(name, email, password, validateInput);
            Library.users.add(user);
        }
        System.out.println("Account created successfully!!!");
    }

    private boolean isRegistered(String email){
        if(Library.users.size() == 0)
            return false;
        for(User user : Library.users) {
            if(email.equals(user.getUserEmail()))
                return true;
        }
        return false;
    }

    private void setName() {
        this.name = IterateInput.stringInput("username", validateInput::validateName);
    }

    private void setEmail() {
        this.email = IterateInput.stringInput("email",
                validateInput::validateEmail).toLowerCase();
    }
    private void setPassword() {
        this.password = IterateInput.stringInput("password",
                validateInput::validatePassword);
    }
}
