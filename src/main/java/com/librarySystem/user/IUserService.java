package main.java.com.librarySystem.user;

import main.java.com.librarySystem.model.Book;
import main.java.com.librarySystem.util.NewResponse;

import java.util.List;
import java.util.Map;

public interface IUserService {
    Boolean addUser(RUser newUser);
    List<RUser> getUsers();
//    NewResponse verifyLogin(String email, String password);
    NewResponse getUserByEmail(String email);
    NewResponse getAllBooks();
    NewResponse searchBook(String searchType, String search);
    NewResponse borrowBook(RUser user, String bookTitle);
    Map<Book, Integer> viewBorrowedBooks(RUser user);
    NewResponse returnBook(RUser user, String bookTitle, int returnQty);
}

