package UIs;

import UIs.LoginUser;
import library.Library;
import users.Librarian;
import users.User;
import util.IterateInput;
import util.Utils;
import util.ValidateInput;

public class RegisterUser {
    private String name;
    private String email;
    private String password;
//    private List<User> users = new ArrayList<>();
    private final ValidateInput VALIDATE = Utils.validate;

    public void registrationUI() {
        int option;
        while (true) {
            System.out.println();
            System.out.println("---------------- Sign up / Login ---------------");
            System.out.println("1. Sign up");
            System.out.println("2. Login");
            System.out.println("0. Quit");
            System.out.println();
            option = IterateInput.intInput("option", 0, 2, VALIDATE::validateOption);
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
        userOption = IterateInput.intInput("Account type", 1, 2, VALIDATE::validateOption);
        createAccount(userOption);
    }

    private void createAccount(int accountType) {
        if (accountType == 2) {
            Librarian admin = new Librarian(name, email, password);
            Library.users.add(admin);
        } else {
            User user = new User(name, email, password);
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
        this.name = IterateInput.stringInput("username", VALIDATE::validateName);
    }

    private void setEmail() {
        this.email = IterateInput.stringInput("email",
                VALIDATE::validateEmail).toLowerCase();
    }
    private void setPassword() {
        this.password = IterateInput.stringInput("password",
                VALIDATE::validatePassword);
    }
}
