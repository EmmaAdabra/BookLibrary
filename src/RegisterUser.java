import java.util.ArrayList;

public class RegisterUser {
    private String name;
    private String email;
    private String password;
    private User user;
    private ArrayList<User> users = new ArrayList<>();
    private ValidateInput validate;

    public RegisterUser(ValidateInput validate) {
        this.validate = validate;
    }

    private void getUserDetails() {
        Console.clearBuffer();
        System.out.println();
        System.out.println("Enter your details");
        setName();
        setEmail();
        setPassword();
        chooseAccountType();
    }

    private void chooseAccountType() {
        byte userOption;
        System.out.println();
        System.out.println("Choose account type:");
        System.out.println("1. User");
        System.out.println("2. Admin");
        userOption = IterateInput.byteInput("Account type", (byte)1, (byte)2, validate::validateOption);
        createAccount(userOption);
    }

    private void createAccount(byte accountType) {
        if (accountType == 2) {
            System.out.println("Admin user not available yet");
            chooseAccountType();
        }
        user = new User(name, email, password);
        users.add(user);
        System.out.println("Account created successfully!!!\n");
        registrationUI();
    }

    public void registrationUI() {
        byte option;
        System.out.println("1. Sign up");
        System.out.println("2. Login");
        System.out.println("0. Quit");
        System.out.println();
        option = IterateInput.byteInput("option", (byte)0, (byte)2, validate::validateOption);
        switch (option) {
            case 1:
                getUserDetails();
                break;
            case 2:
                System.out.println("Login coming soon");
                break;
            case 0:
                System.exit(0);
        }
    }
    private void setName() {
        this.name = IterateInput.stringInput("username", validate::validateName);
    }

    private void setEmail() {
        this.email = IterateInput.stringInput("email", validate::validateEmail);
    }
    private void setPassword() {
        this.password = IterateInput.stringInput("password", validate::validatePassword);
    }
}
