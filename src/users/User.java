package users;

import library.Library;
import util.IterateInput;
import util.Utils;
import validateInput.ValidateInput;

import java.util.List;

/**
 * Represents a user in the library system.
 * This class maintains information about the user's credentials, online status,
 * and a list of borrowed books.
 * It provides methods for logging in and out.
 */
public class User {
    Library library = new Library();
    ValidateInput validate = Utils.validate;
    private String userName;
    private String userEmail;
    private String userPassword;

    public String privilege;
    public boolean isOnline;

    public User(String name, String email, String password) {
        this.userName = name;
        this.userEmail = email;
        this.userPassword = password;
        setPrivilege();
    }

    public void userUI(){
        while (isOnline) {
            System.out.println();
            System.out.println("--------------- Main Menu ---------------");
            System.out.println("1. To view all book");
            System.out.println("2. Search for book");
            System.out.println("3. Borrow book");
            System.out.println("4. View borrowed books");
            System.out.println("5. Return borrowed books");
            System.out.println("0. Logout");
            System.out.println();
            int option = IterateInput.intInput( "Option", 0, 5, validate::validateOption);

            switch (option) {
                case 1 -> library.viewAllBook();
                case 2 -> library.searchBook();
                case 3 -> library.borrowBook(new Library.Borrower(getUserName(), getUserEmail()));
                case 4 -> viewBorrowedBooks();
                case 5 -> library.returnBorrowedBook(new Library.Borrower(getUserName(), getUserEmail()));
                case 0 -> logout();
            }
        }
    }

    public void login() {
        System.out.println();
        System.out.println("-----------------------------------------");
        System.out.println();
        System.out.println("Welcome " + userName + " (" + userEmail + ")");
        isOnline = true;
        userUI();
    }
    public void logout() {
        isOnline = false;
    }

    public void viewBorrowedBooks(){
        List<Library.TypeOfBorrowedBook> borrowedBooks;
        System.out.println();
        System.out.println("--------------- Your Borrowed Books ---------------");
        System.out.println();
        borrowedBooks = Library.getBooksBorrowedByUSer(new Library.Borrower(getUserName(), getUserEmail()));
        if(borrowedBooks != null) {
            borrowedBooks.forEach(System.out::println);
        }
        else {
            System.out.println("You have 0 borrowed book");
        }
    }

    @Override
    public String toString(){
        return "Name: " + userName + "\n" + "Email: " + userEmail
                + "\n" + "Privilege: " + privilege;
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

    public String getPrivilege() {
        return privilege;
    }

    public void setPrivilege() {
        this.privilege = "USER";
    }
}
