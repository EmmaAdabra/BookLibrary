package main.java.com.librarySystem.user;

import main.java.com.librarySystem.model.Book;
import main.java.com.librarySystem.util.Response;

import java.util.List;
import java.util.Map;

public interface IUserService {
    Boolean addUser(User newUser);
    List<User> getUsers();
//    NewResponse verifyLogin(String email, String password);
    Response getUserByEmail(String email);
    Response getAllBooks();
    Response searchBook(String searchType, String search);
    Response borrowBook(User user, String bookTitle);
    Map<Book, Integer> viewBorrowedBooks(User user);
    Response returnBook(User user, String bookTitle, int returnQty);
}

