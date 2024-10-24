package main.java.com.librarySystem.admin;

import main.java.com.librarySystem.model.Book;
import main.java.com.librarySystem.user.User;
import main.java.com.librarySystem.util.Response;

import java.util.Map;

public interface IAdminService {
    Response addBook(Book book);
    Map<User, Map<Book, Integer>> getBorrowRecord();
}
