import java.time.LocalDate;

public class Book {
    private final String title;
    private final String author;
    private byte bookID;
    private final String category;
    private LocalDate dateAdded;

    public Book(String title, String author, String category) {
        this.title = title;
        this.author = author;
        this.category = category;
        setDateAdded();
    }

    public void getBookDetails() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "Title: " + title + "\nAuthor: " + author + "\nBook ID: " + bookID +
                "\nCategory: " + category + "\nDate Added: " + dateAdded;
    }

    private void setDateAdded(){
        this.dateAdded = LocalDate.now();
    }
}
