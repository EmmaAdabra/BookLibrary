package library;

import java.time.LocalDate;
import java.util.Objects;

public class Book {
    private String title;
    private String author;
    private int bookID;
    private String category;
    private String ISBN;

    private int amountBorrowed = 0;

    private int amountLeft = 0;
    private int quantity;
    private LocalDate dateAdded;

    public Book(String title, String author, String category, String ISBN, int quantity) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.ISBN = ISBN;
        this.quantity = quantity;
        setDateAdded();
        setBookID();
    }

    public void getBookDetails() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "Title: " + title + "\nAuthor: " + author + "\nISBN: " + ISBN +
                "\nCategory: " + category + "\nQuantity: " + quantity
                + "\nDate Added: " + dateAdded + "\nAmount Borrowed: " + getAmountBorrowed();
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

    private void setDateAdded(){
        this.dateAdded = LocalDate.now();
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    private void setBookID() {
        this.bookID = (Library.books.size() + 1);
    }

    public int getQuantity(){
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

    public int getAmountBorrowed() {
        return amountBorrowed;
    }

    public void setAmountBorrowed(int amountBorrowed) {
        this.amountBorrowed += amountBorrowed;
    }

    public int getAmountLeft() {
        return amountLeft;
    }

    public void setAmountLeft(int amountLeft) {
        this.amountLeft = amountLeft;
    }
}