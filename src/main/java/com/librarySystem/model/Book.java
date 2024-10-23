package main.java.com.librarySystem.model;

import java.time.LocalDate;
import java.util.Objects;

public class Book {
    private String title;
    private String author;
    private String category;
    private String ISBN;


    private int quantity;
    private int availableCopies;
    private LocalDate dateAdded;

    public Book(String title, String author, String category, String ISBN, int quantity) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.ISBN = ISBN;
        setQuantity(quantity);
        this.dateAdded = setDateAdded();
    }

    public boolean canBorrow(){
        if(availableCopies > 1) {
            return true;
        }

        return false;
    }

    @Override
    public String toString() {
        return "Title: " + title + "\nAuthor: " + author + "\nISBN: " + ISBN +
                "\nCategory: " + category + "\nQuantity: " + quantity
                + "\nDate Added: " + dateAdded + "\nAvailable Copies: " + getAvailableCopies();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book book)) return false;
        return getISBN().equals(book.getISBN());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getISBN());
    }

    private LocalDate setDateAdded() {
        return LocalDate.now();
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.availableCopies = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getTitle() {
        return title;
    }

    public String getISBN() {
        return ISBN;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void updateAvailableCopies(int availableCopies) {

        this.availableCopies = availableCopies;
    }
}