package main.java.com.librarySystem.repository;

import main.java.com.librarySystem.model.Book;
import main.java.com.librarySystem.user.RUser;

import java.util.List;
import java.util.Map;

public interface IBookRepository {

    void addBook(Book book);
    List<Book> getBooks();
    Book getBookByISBN(String ISBN);
    Book getBookByTitle(String title);
    List<Book> getBookByCategory(String category);
}

