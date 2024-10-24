package main.java.com.librarySystem.repository;

import main.java.com.librarySystem.model.Book;

import java.util.List;

public interface IBookRepository {

    void addBook(Book book);
    List<Book> getBooks();
    Book getBookByISBN(String ISBN);
    Book getBookByTitle(String title);
    List<Book> getBookByCategory(String category);
}

