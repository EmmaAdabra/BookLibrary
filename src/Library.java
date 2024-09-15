import java.util.ArrayList;
import java.util.List;

public class Library {
    public static List<Book> books = new ArrayList<>();
    public static List<User> users = new ArrayList<>();
    private ValidateInput validate;

    public Library(ValidateInput validate) {
        this.validate = validate;
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
        if(books.size() == 0)
            return false;
        for (Book book : books) {
            if(book.getISBN().equalsIgnoreCase(ISBN)) {
                return true;
            }
        }
        return false;
    }

    public void viewAllBook(){
        System.out.println("--------------- All Books ---------------");
        if(books.size() == 0)
            System.out.println("No book in the library, add book now");
        for(Book book : books) {
            System.out.println();;
            System.out.println(book);
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
        if(!(books.size() == 0)) {
            for(Book book : books) {
                if(book.getTitle().equalsIgnoreCase(title))
                    return book;
            }
        }
        return  null;
    }

    private ArrayList<Book> searchBookByAuthor(String author) {
        if(!(books.size() == 0)) {
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
        if(!(books.size() == 0)) {
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

    private void searchBook(){
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
                    System.out.println("Search result:");
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
                    System.out.println("Search result:");
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
                    System.out.println("Search result:");
                    for (Book book : result) {
                        System.out.println(book);
                        System.out.println();
                    }
                }
            }

            case 0 -> System.out.println("coming soon");
        }
    }

    public void libraryUI(){
        while (true) {
            System.out.println();
            System.out.println("--------------- Main Menu ---------------");
            System.out.println();
            System.out.println("1. Add book");
            System.out.println("2. To view all book");
            System.out.println("3. Search for book");
            System.out.println("4. Remove book");
            System.out.println("0. Quit");
            System.out.println();
            byte option = IterateInput.byteInput( "Option", (byte) 0, (byte)3, validate::validateOption);

            switch (option) {
                case 1 -> addBook();
                case 2 -> viewAllBook();
                case 3 -> searchBook();
                case 4 -> removeBook();
                case 0 -> System.exit(0);
            }
        }
    }

    private void removeBook(){
//        UI
        String bookISBN;
        System.out.println();
        System.out.print("Enter book ISBN: ");
        bookISBN = Console.readString();

        if(books.size() > 0) {
            for(Book book : books) {
                if(book.getISBN().equalsIgnoreCase(bookISBN)) {
                    if(book.getAmountBorrowed() == 0){
                        books.remove(book);
                        System.out.println(book.getTitle() + " removed from the library");
                    }
                    else
                        System.out.println("Can't remove " + book.getTitle() + ", " +
                                "have" + book.getAmountBorrowed() + "copies");
                }
            }
        }
    }
}
