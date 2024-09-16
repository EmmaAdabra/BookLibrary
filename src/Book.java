import java.time.LocalDate;
import java.util.Objects;

public class Book {
    private String title;
    private String author;
    private byte bookID;
    private String category;
    private String ISBN;

    private byte amountBorrowed = 0;

    private byte amountLeft = 0;
    private byte quantity;
    private LocalDate dateAdded;

    public Book(String title, String author, String category, String ISBN, byte quantity) {
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
        return "Title: " + title + "\nAuthor: " + author + "\nBook ID: " + bookID +
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

    public void setQuantity(byte quantity) {
        this.quantity = quantity;
    }

    private void setBookID() {
        this.bookID = (byte) (Library.books.size() + 1);
    }

    public byte getQuantity(){
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

    public byte getAmountBorrowed() {
        return amountBorrowed;
    }

    public void setAmountBorrowed(byte amountBorrowed) {
        this.amountBorrowed += amountBorrowed;
    }

    public byte getAmountLeft() {
        return amountLeft;
    }

    public void setAmountLeft(byte amountLeft) {
        this.amountLeft = amountLeft;
    }
}
