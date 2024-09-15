import java.time.LocalDate;

public class Book {
    private String title;
    private String author;
    private byte bookID;
    private String category;
    private String ISBN;
    private byte amountBorrowed = 0;
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
                "\nCategory: " + category + "\nQuantity: " + quantity + "\nDate Added: " + dateAdded;
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
}
