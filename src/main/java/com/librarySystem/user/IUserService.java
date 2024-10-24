package main.java.com.librarySystem.user;

import main.java.com.librarySystem.model.Book;
import main.java.com.librarySystem.util.Response;

import java.util.List;
import java.util.Map;

public interface IUserService {
    Boolean addUser(RUser newUser);
    List<RUser> getUsers();
//    NewResponse verifyLogin(String email, String password);
    Response getUserByEmail(String email);
    Response getAllBooks();
    Response searchBook(String searchType, String search);
    Response borrowBook(RUser user, String bookTitle);
    Map<Book, Integer> viewBorrowedBooks(RUser user);
    Response returnBook(RUser user, String bookTitle, int returnQty);
}

