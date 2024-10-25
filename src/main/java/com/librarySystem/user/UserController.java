package main.java.com.librarySystem.user;

import main.java.com.librarySystem.model.Book;
import main.java.com.librarySystem.model.Role;
import main.java.com.librarySystem.repository.IBookRepository;
import main.java.com.librarySystem.repository.IBorrowedBookRepo;
import main.java.com.librarySystem.repository.IUserRepository;
import main.java.com.librarySystem.util.*;

import java.util.List;

public class UserController {
    protected IUserService userService;
    protected Scanner scanner;
    protected IValidateInput validateInput;

    protected User loggedInUser;

    public UserController(IUserRepository userRepository, IBookRepository bookRepository,
                          IBorrowedBookRepo borrowedBooksRepo,
                          Scanner scanner, IValidateInput validateInput) {
        this.userService = new UserService(userRepository, bookRepository, borrowedBooksRepo);
        this.scanner = scanner;
        this.validateInput = validateInput;
    }

    public void signUp() {
        System.out.println();
        System.out.println("Enter your details");

//        get user details
        String name = IterateInput.stringInput("Name", validateInput::validateName);
        String email = IterateInput.stringInput("Email", validateInput::validateEmail);
        String password = IterateInput.stringInput("Password", validateInput::validatePassword);
        System.out.println();
        System.out.println("Choose account type:");
        System.out.println("1. Regular user");
        System.out.println("2. Admin");
        int roleOption = IterateInput.intInput("Choose role", 1, 2, validateInput::validateUserOption);
        Role role = roleOption == 1 ? Role.USER : Role.ADMIN;
        System.out.println();

//        create user
        User newUser = new User(name, email, password, role);

//        add user to repository
        Boolean isRegister = userService.addUser(newUser);
        if (!isRegister) {
            System.out.println("User already exist");
        } else {
            System.out.println("User registered successfully");
        }

    }

    public void handleLogin(User user) {
        if (user.getRole() == Role.USER) {
            this.loggedInUser = user;
            user.login();
            displayMenu(user);
        }
    }

    protected void displayMenu(User user) {
        String userDetails = user.getUserName() + " (" + user.getUserEmail() + ")";
        String heading = "--------------- Main Menu ---------------";
        String[] menuOptions = new String[]{"View All Books", "Search for Book",
                "Borrow Book", "Return Book", "View Borrowed Books", "Logout"};

        while (user.isLoggedIn()) {
            DisplayHelpers.displayMenu(heading, menuOptions, userDetails);
            int userOption = IterateInput.intInput("Option", 1, menuOptions.length, validateInput::validateUserOption);

            switch (userOption) {
                case 1 -> viewAllBooks();
                case 2 -> searchForBook();
                case 3 -> borrowBook();
                case 4 -> returnBook();
                case 5 -> viewBorrowedBooks(loggedInUser);
                case 6 -> user.logout();
            }
        }
    }

    protected void viewAllBooks() {
        System.out.println();
        System.out.println("--------------- All Books ---------------");
        System.out.println();

        var books = userService.getAllBooks();

        if (books.status) {
            var allBooks = (List<Book>) books.obj;
            for (Book book : allBooks) {
                System.out.println(book);
                if (allBooks.size() > 1) {
                    System.out.println();
                }
            }
        } else {
            System.out.println(books.message);
        }
    }

    protected void searchForBook() {
        String query;
        Response response = null;
        System.out.println();
        String heading = "Search by:";
        String[] searchOptions = new String[]{"Book Title", "Book ISBN", "Book Category"};
        DisplayHelpers.displaySubMenu(heading, searchOptions);

        int userOption = IterateInput.intInput("Option", 1, searchOptions.length,
                validateInput::validateUserOption);

        switch (userOption) {
            case 1 -> {
                query = scanner.readString("Book Title");
                response = userService.searchBook("title", query);
            }

            case 2 -> {
                query = scanner.readString("Book ISBN");
                response = userService.searchBook("ISBN", query);
            }

            case 3 -> {
                query = scanner.readString("Book Category");
                response = userService.searchBook("category", query);
            }
        }

        System.out.println();
        System.out.println("--------------- Search Result ---------------");
        System.out.println();
        if(response.status){
            if (response.obj instanceof Book book) {
                System.out.println(book);

            } else  {
                var foundBooks = (List<Book>) response.obj;
                for (Book book : foundBooks) {
                    System.out.println(book);

                    if(foundBooks.size() > 1) {
                        System.out.println();
                    }
                }
            }
        } else {
            System.out.println(response.message);
        }
    }

    protected void borrowBook(){
        System.out.println();
        String bookTitle = scanner.readString("Book title");
        System.out.println();

        var response = userService.borrowBook(loggedInUser, bookTitle);

        if(response.status) {
            System.out.println(response.message);
        } else {
            System.out.println(response.message);
        }
    }


    protected void viewBorrowedBooks(User user) {
        System.out.println();
        System.out.println("--------------- All Borrowed Books ---------------");
        System.out.println();
//        String bookDetails;
        var userBorrowedBooks = userService.viewBorrowedBooks(user);

        if(userBorrowedBooks.isEmpty()) {
            System.out.println("You have not borrow any book yet");
        } else {
            int size = userBorrowedBooks.size();
            userBorrowedBooks.forEach((key, value) -> {
                DisplayHelpers.displayBorrowedBook(key, value);
                if(size > 1) {
                    System.out.println();
                }
            });
        }
    }

    protected void returnBook() {
        System.out.println("--------------- Return Borrowed Books ---------------");
        System.out.println();
        String bookTitle = scanner.readString("Enter Title You Want To Return");
        int returnQty = scanner.readInt("Return Quantity");
        System.out.println();
        var response = userService.returnBook(loggedInUser, bookTitle, returnQty);

        if(response.status) {
            System.out.println(response.message);
        } else {
            System.out.println(response.message);
        }
    }
}
