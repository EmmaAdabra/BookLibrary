import java.util.List;

/**
 * Represents a user in the library system.
 *
 * This class maintains information about the user's credentials, online status,
 * and a list of borrowed books.
 *
 * It provides methods for logging in and out.
 */
public class User {
    Library library = new Library();
    ValidateInput validate = Main.validate;
    private String userName;
    private String userEmail;
    private String userPassword;
    public boolean isOnline;
    private List<Library.TypeOfBorrowedBook> borrowedBooks;


    public User(String name, String email, String password) {
        this.userName = name;
        this.userEmail = email;
        this.userPassword = password;
    }

    public void userUI(){
        while (isOnline) {
            System.out.println();
            System.out.println("--------------- Main Menu ---------------");
            System.out.println("1. To view all book");
            System.out.println("2. Search for book");
            System.out.println("3. Borrow book");
            System.out.println("4. View borrowed books");
            System.out.println("0. Logout");
            System.out.println();
            int option = IterateInput.intInput( "Option", 0, 4, validate::validateOption);

            switch (option) {
                case 1 -> library.viewAllBook();
                case 2 -> library.searchBook();
                case 3 -> library.borrowBook(new Library.Borrower(getUserName(), getUserEmail()));
                case 4 -> viewBorrowedBooks();
                case 0 -> logout();
            }
        }
    }

    public void login() {
        System.out.println();
        System.out.println("-----------------------------------------");
        System.out.println();
        System.out.println("Welcome " + userName + "(" + userEmail + ")");
        isOnline = true;
        userUI();
    }
    public void logout() {
        isOnline = false;
    }

    public void viewBorrowedBooks(){
        System.out.println();
        System.out.println("--------------- You Borrowed Books ---------------");
        System.out.println();
        borrowedBooks = Library.getBookBorrowers(new Library.Borrower(getUserName(), getUserEmail()));
        if(borrowedBooks != null) {
            borrowedBooks.forEach(System.out::println);
        }
        else {
            System.out.println("You have 0 borrowed book");
        }
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
}
