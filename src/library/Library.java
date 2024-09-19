package library;

import users.User;
import util.*;
import response.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Library {
    public static List<Book> books = new ArrayList<>();
    public static List<User> users = new ArrayList<>();
    private final ValidateInput VALIDATE = Utils.validate;
   private static HashMap<Borrower, List<TypeOfBorrowedBook>> bookBorrowers = new HashMap<>();

//   type of users who have borrowed a book
   public static class Borrower {
       private String name;
       private String email;


    public Borrower  (String name, String email) {
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
       private final String ISBN;

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

    public void setAmountBorrowed(int qtyToBeBorrowed) {
           amountBorrowed += qtyToBeBorrowed;
    }
   }

    public void borrowBook(Borrower user) {
        String bookTitle;
        Response response;
        System.out.println();
        bookTitle = Console.readString("Book title");
        System.out.println();

//        check if user can borrow book
        response = canBorrowBook(user, bookTitle);

        if(response.code == 1) {
            TypeOfBorrowedBook toBeBorrowedBook = (TypeOfBorrowedBook) response.obj;
            toBeBorrowedBook.setAmountBorrowed(1);
            List<TypeOfBorrowedBook> borrowedBooks;

            if(bookBorrowers.containsKey(user)) {
                borrowedBooks = bookBorrowers.get(user);
                if(borrowedBooks.contains(toBeBorrowedBook)) {
                    int indexOfToBeBorrowedBook = borrowedBooks.indexOf(toBeBorrowedBook);
                    TypeOfBorrowedBook alreadyExistBook = borrowedBooks.get(indexOfToBeBorrowedBook);
                    alreadyExistBook.setAmountBorrowed(1);
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

    private Response canBorrowBook(Borrower user, String bookTitle) {
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
               if(totalBookBorrowed == 3) {
                   return new Response(0, "you can't borrow more than 3 books", null);
               }
           }

           for(Book bk : books) {
               if(bk.getTitle().equalsIgnoreCase(bookTitle)) { //check if book exist in library
                   if((bk.getQuantity() - bk.getAmountBorrowed() > 1)) {
                       // check if amount of book available is greater than 1
                       bk.setAmountBorrowed(1);
                       book = new TypeOfBorrowedBook(bk.getTitle(), bk.getAuthor(), bk.getISBN());
                       return new Response(
                               1, "You have successfully borrowed " + bookTitle, book);
                   }
                  return new Response(
                          0, "You can't borrow " + bookTitle + ", only 1 copy left", null);
               }
           }
           return new Response(0, bookTitle + " do not exist", null);
       }

       return new Response(0, "No book added to library", null);

    }
    public void viewBorrowedBook(){
        System.out.println();
        System.out.println("--------------- Book Borrowers ---------------");
        System.out.println();
        if(!bookBorrowers.isEmpty()) {
            for(Borrower user : bookBorrowers.keySet()){
                System.out.println(user);
                for (TypeOfBorrowedBook book :  bookBorrowers.get(user))
                    System.out.println(book);
                System.out.println();
            }
        } else {
            System.out.println("0 Book borrowed");
        }

    }



    public void addBook() {
        String title;
        String author ;
        String category;
        String ISBN;
        int quantity;

        System.out.println();
        System.out.println("--------------- Enter book details ---------------");
        title = Console.readString("Title");
        author = Console.readString("Author");
        category = Console.readString("Category");
        ISBN = Console.readString("ISBN");
        quantity = Console.readInt("Quantity");

        if(isBookExist(ISBN)) {
            System.out.println(title + " already exist\n");
        } else {
            Book book = new Book(title, author, category, ISBN, quantity);
            books.add(book);
            System.out.println(title + " added successfully\n");
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
        int option;

        System.out.println();
        System.out.println("Search by:");
        System.out.println("1. Book Title");
        System.out.println("2. Book ISBN");
        System.out.println("3. Book Author");
        System.out.println("4. Book Category");
        System.out.println("0. Main menu");
        System.out.println();

        option = IterateInput.intInput("Option", 0, 4, VALIDATE::validateOption);
        System.out.println();
        String query;
        switch (option) {
            case 1 -> {
                query = Console.readString("Book Title");
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
                query = Console.readString("Book ISBN");
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
                query = Console.readString("Book Author");
                List<Book> result = searchBookByAuthor(query);

                if(result == null)
                    System.out.println("Book not found");
                else {
                    System.out.println("--------------- Search Result ---------------");
                    result.forEach(book -> {
                        System.out.println(book);
                        System.out.println();
                    });
                }
            }

            case 4 -> {
                query = Console.readString("Book Category");
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
        bookISBN = Console.readString("Enter book ISBN");

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

    public static List<TypeOfBorrowedBook> getBooksBorrowedByUSer(Borrower user) {
        return bookBorrowers.get(user);
    }

    public void viewAllRegisteredUsers(){
       if(!users.isEmpty()) {
           System.out.println();
           System.out.println("--------------- All Registered Users ---------------");
           System.out.println();
           users.forEach(user -> {
               System.out.println(user);
               System.out.println();
           });
       } else {
           System.out.println("0 registered user");
       }
    }

    public void returnBorrowedBook(Borrower user){
        String returnBookTile;
        int totalNumberOfBookBorrowed = 0;
        int numberOfReturnCopies;
        TypeOfBorrowedBook toBeReturnedBook = null;
        boolean bookFoundFlag = false;
        Book maninBook = null;

        System.out.println();

        if(bookBorrowers.containsKey(user)){
            List<TypeOfBorrowedBook> userBorrowedBooks = bookBorrowers.get(user);
            System.out.println("--------------- Your Borrowed Books ---------------");
            System.out.println();
            userBorrowedBooks.forEach(System.out::println);
            System.out.println();
            returnBookTile = Console.readString("Book Title You Want To Return");
            for(TypeOfBorrowedBook book : userBorrowedBooks){
                if(book.title.equals(returnBookTile)){
                    for (Book bk : books){
                        if(bk.getTitle().equals(returnBookTile))
                            maninBook = bk;
                    }
                    toBeReturnedBook = book;
                    bookFoundFlag = true;
                    totalNumberOfBookBorrowed = book.getAmountBorrowed();
                }
            }

            if(bookFoundFlag) {
                if(totalNumberOfBookBorrowed  > 1) {
                    numberOfReturnCopies =  IterateInput.intInput("How many copies do you wish to return",
                            1, totalNumberOfBookBorrowed, VALIDATE::validateOption);
                    toBeReturnedBook.setAmountBorrowed(-numberOfReturnCopies);
                    String copy = (numberOfReturnCopies > 1) ? "copies" : "copy";
                    System.out.println("You have returned " + numberOfReturnCopies + " "
                            + copy + " of " + returnBookTile);
                    maninBook.setAmountBorrowed(-numberOfReturnCopies);
                    if(toBeReturnedBook.getAmountBorrowed() <= 0){
                        userBorrowedBooks.remove(toBeReturnedBook);
                    }
                } else {
                    userBorrowedBooks.remove(toBeReturnedBook);
                    maninBook.setAmountBorrowed(-1);
                    System.out.println("You have returned " + returnBookTile);
                }

                if(userBorrowedBooks.isEmpty()) {
                    bookBorrowers.remove(user);
                }

            } else {
                System.out.println(returnBookTile + " not found among your borrowed book");
            }
        } else {
            System.out.println("You have 0 borrowed book");
        }
    }
}
