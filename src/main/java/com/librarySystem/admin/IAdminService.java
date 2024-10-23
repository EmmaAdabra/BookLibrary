package main.java.com.librarySystem.admin;

import main.java.com.librarySystem.model.Book;
import main.java.com.librarySystem.user.RUser;
import main.java.com.librarySystem.util.NewResponse;

import java.util.List;
import java.util.Map;

public interface IAdminService {
    NewResponse addBook(Book book);
    Map<RUser, Map<Book, Integer>> getBorrowRecord();
}
