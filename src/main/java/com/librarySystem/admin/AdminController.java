package main.java.com.librarySystem.admin;

import main.java.com.librarySystem.model.Book;
import main.java.com.librarySystem.model.Role;
import main.java.com.librarySystem.repository.IBookRepository;
import main.java.com.librarySystem.repository.IBorrowedBookRepo;
import main.java.com.librarySystem.repository.IUserRepository;
import main.java.com.librarySystem.user.User;
import main.java.com.librarySystem.user.UserController;
import main.java.com.librarySystem.util.*;

import java.util.List;
import java.util.Set;

public class AdminController extends UserController {
    private IAdminService adminService;

    public AdminController(IUserRepository userRepository, IBookRepository bookRepository,
                           IBorrowedBookRepo borrowedBooksRepo, IValidateInput validateInput) {
        super(userRepository, bookRepository, borrowedBooksRepo, validateInput);
        this.adminService = new AdminService(userRepository, bookRepository, borrowedBooksRepo);
    }

    @Override
    public void handleLogin(User user) {
        if (user.getRole() == Role.ADMIN) {
//            this.loggedInUser = user;
            user.login();
            displayMenu(user);
        }
    }

    @Override
    protected void displayMenu(User user) {
        String userDetails = user.getUserName() + " (" + user.getUserEmail() + ")";
        System.out.println();
        String heading = "--------------- Main Menu ---------------";
        String[] menuOptions = new String[]{
                "Add book",
                "View all books",
                "Search for Book",
                "View all users",
                "View Borrow Record",
                "Register User",
                "Logout"
        };

        while (user.isLoggedIn()) {
            DisplayHelpers.displayMenu(heading, menuOptions, userDetails);
            int userOption = IterateInput.intInput("Option", 1, menuOptions.length, validateInput::validateUserOption);

            switch (userOption) {
                case 1 -> addBook();
                case 2 -> viewAllBooks();
                case 3 -> searchForBook();
                case 4 -> viewUsers();
                case 5 -> viewBorrowRecord();
                case 6 -> signUp();
                case 7 -> user.logout();
            }
        }
    }

    private void addBook() {
        System.out.println();
        System.out.println("--------------- Enter book details ---------------");
        String title = CustomScanner.readString("Title");
        String author = CustomScanner.readString("Author");
        String category = CustomScanner.readString("Category");
        String ISBN = CustomScanner.readString("ISBN");
        int quantity = CustomScanner.readInt("Quantity");
        System.out.println();

        var newBook = new Book(title, author, category, ISBN, quantity);
        var response = adminService.addBook(newBook);
        if (response.status) {
            System.out.println(title + " added successfully");
        } else {
            System.out.println(title + " already exist");
        }
    }

    private void viewUsers() {
        System.out.println();
        System.out.println("---------------- All registered users ---------------");
        System.out.println();
        List<User> users = userService.getUsers();
        if (users == null) {
            System.out.println("0 registered users");
        } else {
            users.forEach(user -> {
                System.out.println(user);
                if (users.size() > 1) System.out.println();
            });
        }
    }

    private void viewBorrowRecord(){
        System.out.println();
        System.out.println("--------------- Borrowed Books Record ---------------");
        System.out.println();

        var borrowRecord = adminService.getBorrowRecord();

        if(!borrowRecord.isEmpty()) {
            int userCount = borrowRecord.size();
            Set<User> users = borrowRecord.keySet();

            users.forEach(user -> {
                String userDetails = user.getUserName() + " (" + StringUtility.maskEmail(user.getUserEmail()) +")";
                System.out.println(userDetails);
                var borrowedBooks = borrowRecord.get(user);
                int borrowedQty = borrowedBooks.size();
                borrowedBooks.forEach((key, value) -> {
                    DisplayHelpers.displayBorrowedBook(key, value);
                    if (borrowedQty > 1) {
                        System.out.println();
                    }
                });
                if(userCount > 1){
                    System.out.println();
                }
            });
        } else {
            System.out.println("No Borrow Record");
        }
    }
}
