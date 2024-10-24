package main.java.com.librarySystem.repository;

import main.java.com.librarySystem.model.Book;
import main.java.com.librarySystem.user.RUser;

import java.util.Map;

public interface IBorrowedBookRepo {
    boolean addBorrowRecord(RUser user, Book book);
    Map<Book, Integer> getUserBorrowedBooks(RUser user);
    int totalBookBorrowed(RUser user);
    Map<RUser, Map<Book, Integer>> getBorrowRecord();
}
