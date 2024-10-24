package main.java.com.librarySystem.repository;

import main.java.com.librarySystem.model.Book;
import main.java.com.librarySystem.user.User;

import java.util.Map;

public interface IBorrowedBookRepo {
    boolean addBorrowRecord(User user, Book book);
    Map<Book, Integer> getUserBorrowedBooks(User user);
    int totalBookBorrowed(User user);
    Map<User, Map<Book, Integer>> getBorrowRecord();
}
