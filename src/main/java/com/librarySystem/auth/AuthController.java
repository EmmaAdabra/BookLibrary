package main.java.com.librarySystem.auth;

import main.java.com.librarySystem.admin.AdminController;
import main.java.com.librarySystem.repository.IUserRepository;
import main.java.com.librarySystem.user.RUser;
import main.java.com.librarySystem.user.UserController;
import main.java.com.librarySystem.util.IValidateInput;
import main.java.com.librarySystem.util.IterateInput;
import main.java.com.librarySystem.util.Response;

public class AuthController {
    UserController userController;
    AdminController adminController;
    IValidateInput validateInput;
    IAuthServices authServices;

    public AuthController(AdminController adminController,
                          UserController userController, IUserRepository userRepository, IValidateInput validateInput) {
        this.userController = userController;
        this.adminController = adminController;
        this.validateInput = validateInput;
        this.authServices = new AuthServices(userRepository);
    }

    public void login(){
        System.out.println();
        System.out.println("--------------- Login Details ---------------");
        String email = IterateInput.stringInput("email", validateInput::validateEmail);
        String password = IterateInput.stringInput("password", validateInput::validatePassword);

        Response response = authServices.verifyLogin(email, password);
        if(response.status) {
           RUser loggedInUser = (RUser)response.obj;

            switch (loggedInUser.getRole()){
                case ADMIN -> {
                    adminController.handleLogin(loggedInUser);
                }
                case USER -> {
                    userController.handleLogin(loggedInUser);
                }
            }
        } else {
            System.out.println();
            System.out.println(response.message);
        }
    }
}
