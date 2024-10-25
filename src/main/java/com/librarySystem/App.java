package main.java.com.librarySystem;

import main.java.com.librarySystem.admin.AdminController;
import main.java.com.librarySystem.auth.AuthController;
import main.java.com.librarySystem.repository.*;
import main.java.com.librarySystem.user.UserController;
import main.java.com.librarySystem.util.*;

public class App {
    public static void main(String[] args) {
        IBookRepository bookRepository = new BookRepository();
        IUserRepository userRepository = new UserRepository();
        IBorrowedBookRepo borrowedBooksRepo = new BorrowedBookRepo();
        IValidateInput validate = new ValidateUserInput();
        var adminController = new AdminController(userRepository, bookRepository, borrowedBooksRepo, validate);
        var userController = new UserController(userRepository, bookRepository,
                borrowedBooksRepo, validate);
        var authController = new AuthController(adminController, userController, userRepository, validate);

        Boolean running = true;

        System.out.println();
        System.out.println("                  Virtual Library                   ".toUpperCase());
        String heading = "---------------- Sign up / Login ---------------";
        String[] menuOptions = new String[]{"Sign up", "Login", "Exit"};

        while (running) {
            DisplayHelpers.displayMenu(heading, menuOptions, "");
            int option = IterateInput.intInput("Option", 1, 3, validate::validateUserOption);

            switch (option){
                case 1 -> userController.signUp();
                case 2 -> authController.login();
                case 3 -> {
                    running = false;
                    System.exit(0);
                }
            }
        }
    }
}
