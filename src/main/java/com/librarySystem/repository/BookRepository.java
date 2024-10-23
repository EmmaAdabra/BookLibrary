package main.java.com.librarySystem.repository;

import main.java.com.librarySystem.model.Book;
import main.java.com.librarySystem.user.RUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BookRepository implements IBookRepository {
    List<Book> books = new ArrayList<>();

    @Override
    public void addBook(Book book) {
        books.add(book);
    }

    @Override
    public List<Book> getBooks() {
        return books;
    }

    public Book getBookByISBN(String ISBN) {
        if(!books.isEmpty()) {
            for (Book book : books) {
                if(book.getISBN().equalsIgnoreCase(ISBN)) {
                    return book;
                }
            }
        }

        return null;
    }

    @Override
    public Book getBookByTitle(String title) {
        if(!books.isEmpty()) {
            for (Book book : books) {
                if(book.getTitle().equalsIgnoreCase(title)){
                    return book;
                }
            }
        }

        return null;
    }

    @Override
    public List<Book> getBookByCategory(String category) {
        List<Book> sameCategoryBooks = new ArrayList<>();

        if(!books.isEmpty()){
            for(Book book : books){
                if(book.getCategory().equalsIgnoreCase(category)){
                    sameCategoryBooks.add(book);
                }
            }

            if(sameCategoryBooks.size() >= 1) {
                return sameCategoryBooks;
            }
        }
        return null;
    }



}
