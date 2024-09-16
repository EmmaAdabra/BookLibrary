import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Library {
    public static List<Book> books = new ArrayList<>();
    public static List<User> users = new ArrayList<>();
    private ValidateInput validate = Main.validate;
   private static HashMap<Borrower, List<TypeOfBorrowedBook>> bookBorrowers = new HashMap<>();

//   type of users who have borrowed a book
   public static class Borrower {
       private String name;
       private String email;


    public Borrower(String name, String email) {
           this.name = name;
           this.email = email;
       }

       @Override
       public boolean equals(Object obj){
           if(this == obj)
               return true;
           if(!(obj instanceof Borrower borrowers))
               return false;
           return email.equals(borrowers.email);
       }

       @Override
       public int hashCode() {
           return email.hashCode();
       }

       @Override
       public String toString() {
           return name + "(" + email + ")";
       }


}

//   borrowed book class
   public static class TypeOfBorrowedBook{
       private String title;
       private String author;
       private String ISBN;

    private int amountBorrowed = 0;

       public TypeOfBorrowedBook(String title, String author, String ISBN) {
           this.title = title;
           this.author = author;
           this.ISBN = ISBN;
       }
       @Override
       public String toString() {
           return "Title: " + title + ", Author: " + author + ", ISBN: " + ISBN
                   + ", Amount borrowed: " + getAmountBorrowed();
       }

       @Override
       public boolean equals(Object o) {
           if (this == o) return true;
           if (!(o instanceof TypeOfBorrowedBook that)) return false;
           return ISBN.equals(that.ISBN);
       }

       @Override
       public int hashCode() {
           return Objects.hash(ISBN);
       }

    public int getAmountBorrowed() {
        return amountBorrowed;
    }

    public void setAmountBorrowed() {
        amountBorrowed++;
    }
   }

    public void borrowBook(Borrower user) {
        String bookTitle;
        Utilities.Response response;
        Console.clearBuffer();
        System.out.println();
        System.out.print("Book title: ");
        bookTitle = Console.readString();
        System.out.println();

//        check if user can borrow book
        response = canBorrowBook(user, bookTitle);

        if(response.code == 1) {
            TypeOfBorrowedBook toBeBorrowedBook = (TypeOfBorrowedBook) response.obj;
            toBeBorrowedBook.setAmountBorrowed();
            List<TypeOfBorrowedBook> borrowedBooks;
            if(bookBorrowers.containsKey(user)) {
                borrowedBooks = bookBorrowers.get(user);
                if(borrowedBooks.contains(toBeBorrowedBook)) {
                    int indexOfToBeBorrowedBook = borrowedBooks.indexOf(toBeBorrowedBook);
                    TypeOfBorrowedBook alreadyExistBook = borrowedBooks.get(indexOfToBeBorrowedBook);
                    alreadyExistBook.setAmountBorrowed();
                    borrowedBooks.set(indexOfToBeBorrowedBook, alreadyExistBook);
                }
                else
                    borrowedBooks.add(toBeBorrowedBook);
            }
            else {
                borrowedBooks = new ArrayList<>();
                borrowedBooks.add(toBeBorrowedBook);
                bookBorrowers.put(user, borrowedBooks);
            }
            System.out.println(response.message);
        }
        else
            System.out.println(response.message);
    }

    private Utilities.Response canBorrowBook(Borrower user, String bookTitle) {
       if(!books.isEmpty()) {
           TypeOfBorrowedBook book;
//        see how to modify this logic later
//        check if user have borrowed more than 3 books
           if(bookBorrowers.containsKey(user)) {
               List<TypeOfBorrowedBook> borrowedBooks = bookBorrowers.get(user);
               int totalBookBorrowed = 0;
               for(TypeOfBorrowedBook borrowedBook : borrowedBooks){
                   totalBookBorrowed += borrowedBook.getAmountBorrowed();
               }
               if(totalBookBorrowed >= 3) {
                   return new Utilities.Response(0, "you can't borrow more than 3 book", null);
               }
           }
           for(Book bk : books) {
               if(bk.getTitle().equalsIgnoreCase(bookTitle)) {
                   if((bk.getQuantity() - bk.getAmountBorrowed() > 1)) {
                       bk.setAmountBorrowed((byte) 1);
                       book = new TypeOfBorrowedBook(bk.getTitle(), bk.getAuthor(), bk.getISBN());
                       return new Utilities.Response(
                               1, "You have successfully borrowed " + bookTitle, book);
                   }
                  return new Utilities.Response(
                          0, "You can't borrow " + bookTitle + ", only 1 copy left", null);
               }
           }
           return new Utilities.Response(0, bookTitle + " do not exist", null);
       }

       return new Utilities.Response(0, "No book added to library", null);

    }
    public void viewBorrowedBook(){
        System.out.println();
        System.out.println("--------------- Book Borrowers ---------------");
        System.out.println();
        for(Borrower user : bookBorrowers.keySet()){
            System.out.println(user);
            for (TypeOfBorrowedBook book :  bookBorrowers.get(user))
                System.out.println(book);
            System.out.println();
        }
    }



    public void addBook() {
        Console.clearBuffer();
        String title;
        String author ;
        String category;
        String ISBN;
        byte quantity;

        System.out.println();
        System.out.println("--------------- Enter book details ---------------");
        System.out.print("Title: ");
        title = Console.readString();
        System.out.print("Author: ");
        author = Console.readString();
        System.out.print("Category: ");
        category = Console.readString();
        System.out.print("ISBN: ");
        ISBN = Console.readString();
        System.out.print("Quantity: ");
        quantity = Console.readByte();

        if(isBookExist(ISBN)) {
            System.out.println(title + " already exist\n");
        } else {
            Book book = new Book(title, author, category, ISBN, quantity);
            books.add(book);
            System.out.println("Book added successfully\n");
        }
    }

    private boolean isBookExist(String ISBN) {
        if(books.isEmpty())
            return false;
        for (Book book : books) {
            if(book.getISBN().equalsIgnoreCase(ISBN)) {
                return true;
            }
        }
        return false;
    }


    public void viewAllBook(){
        System.out.println();
        System.out.println("--------------- All Books ---------------");
        System.out.println();
        if(books.isEmpty())
            System.out.println("No book in the library, add book now");
        for(Book book : books) {
            System.out.println(book);
            System.out.println();
        }
    }

    private Book searchBookByIBSN(String ISBN) {
        for(Book book : books) {
            if(book.getISBN().equalsIgnoreCase(ISBN))
                return book;
        }
        return  null;
    }

    private Book searchBookByTitle(String title) {
        if(!books.isEmpty()) {
            for(Book book : books) {
                if(book.getTitle().equalsIgnoreCase(title))
                    return book;
            }
        }
        return  null;
    }

    private ArrayList<Book> searchBookByAuthor(String author) {
        if(!books.isEmpty()) {
            ArrayList<Book> searchResult = new ArrayList<>();
            for(Book book : books) {
                if(book.getAuthor().equalsIgnoreCase(author))
                    searchResult.add(book);
            }
            if(searchResult.size() > 0)
                return searchResult;
        }
        return  null;
    }

    private ArrayList<Book> searchBookByCategory(String category){
        if(!books.isEmpty()) {
            ArrayList<Book> searchResult = new ArrayList<>();
            for(Book book : books) {
                if(book.getCategory().equalsIgnoreCase(category))
                    searchResult.add(book);
            }
            if(searchResult.size() > 0)
                return searchResult;
        }
        return  null;
    }

    public void searchBook(){
//        search interface
        byte option;

        System.out.println();
        System.out.println("Search by:");
        System.out.println("1. Book Title");
        System.out.println("2. Book ISBN");
        System.out.println("3. Book Author");
        System.out.println("4. Book Category");
        System.out.println("0. Main menu");
        System.out.println();

        option = IterateInput.byteInput("Option", (byte)0, (byte)4, validate::validateOption);
        Console.clearBuffer();

        System.out.println();
        String query;
        switch (option) {
            case 1 -> {
                System.out.print("Book Title: ");
                query = Console.readString();
                System.out.println();
                Book result = searchBookByTitle(query);

                if(result == null)
                    System.out.println("Book not found");
                else {
                    System.out.println("--------------- Search Result ---------------");
                    System.out.println(result);
                }
            }

            case 2 -> {
                System.out.print("Book ISBN: ");
                query = Console.readString();
                System.out.println();
                Book result = searchBookByIBSN(query);

                if(result == null)
                    System.out.println("Book not found");
                else {
                    System.out.println("--------------- Search Result ---------------");
                    System.out.println(result);
                }
            }

            case 3 -> {
                System.out.print("Book Author: ");
                query = Console.readString();
                List<Book> result = searchBookByAuthor(query);

                if(result == null)
                    System.out.println("Book not found");
                else {
                    System.out.println("--------------- Search Result ---------------");
                    for (Book book : result) {
                        System.out.println(book);
                        System.out.println();
                    }
                }
            }

            case 4 -> {
                System.out.print("Book Category: ");
                query = Console.readString();
                System.out.println();
                List<Book> result = searchBookByCategory(query);

                if(result == null)
                    System.out.println("Book not found");
                else {
                    System.out.println("--------------- Search Result ---------------");
                    for (Book book : result) {
                        System.out.println(book);
                        System.out.println();
                    }
                }
            }

            case 0 -> System.out.println("coming soon");
        }
    }

    public void removeBook(){
        boolean bookFound = false;
//        UI
        String bookISBN;
        System.out.println();
        System.out.print("Enter book ISBN: ");
        Console.clearBuffer();
        bookISBN = Console.readString();

        if(!books.isEmpty()) {
            for(Book book : books) {
                if(book.getISBN().equalsIgnoreCase(bookISBN)) {
                    if(book.getAmountBorrowed() == 0){
                        books.remove(book);
                        System.out.println(book.getTitle() + " removed from the library");
                    }
                    else {
                        System.out.println("Can't remove " + book.getTitle() + ", " +
                                "have" + book.getAmountBorrowed() + "copies");
                    }
                    bookFound = true;
                    break;
                }
            }

            if(!bookFound)
                System.out.println("No book match this ISBN");
        }
        else
            System.out.println("No book found in library");
    }

    public static List<TypeOfBorrowedBook> getBookBorrowers(Borrower user) {
       List<TypeOfBorrowedBook> borrowedBooks = bookBorrowers.get(user);
       return  borrowedBooks;
    }

}
