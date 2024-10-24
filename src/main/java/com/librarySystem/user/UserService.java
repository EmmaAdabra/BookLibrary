package main.java.com.librarySystem.user;

import main.java.com.librarySystem.model.Book;
import main.java.com.librarySystem.repository.IBookRepository;
import main.java.com.librarySystem.repository.IBorrowedBookRepo;
import main.java.com.librarySystem.repository.IUserRepository;
import main.java.com.librarySystem.util.Response;

import java.util.List;
import java.util.Map;

public class UserService implements IUserService{
    protected IBookRepository bookRepository;
    protected IUserRepository userRepository;
    protected IBorrowedBookRepo borrowedBooksRepo;
    public UserService(IUserRepository userRepository, IBookRepository bookRepository,
                       IBorrowedBookRepo borrowedBooksRepo) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.borrowedBooksRepo = borrowedBooksRepo;
    }

    @Override
    public Boolean addUser(User newUser) {
        if(userRepository.getUserByEmail(newUser.getUserEmail()) != null){
            return false;
        }

        userRepository.add(newUser);

        return true;
    }

    @Override
    public List<User> getUsers() {
        List<User> users = userRepository.getUsers();
        if(users.size() == 0){
            return null;
        }

        return users;
    }

    @Override
    public Response getAllBooks() {
        List<Book> books = bookRepository.getBooks();
        if(books.size() == 0) {
            return new Response(false, "No book found", null);
        }

        return new Response(true, "books found", books);
    }

    @Override
    public Response getUserByEmail(String email) {
        User user = userRepository.getUserByEmail(email);
        if(user == null){
            return new Response(false, "User not found", null);
        }

        return new Response(true, "found user", user);
    }

    @Override
    public Response searchBook(String searchType, String search) {
        switch (searchType) {
            case "title" -> {
                Book searchBook = bookRepository.getBookByTitle(search);

                if(searchBook != null) {
                    return new Response(true, "Book found", searchBook);
                }
            }

            case "ISBN" -> {
                Book searchBook = bookRepository.getBookByISBN(search);

                if(searchBook != null){
                    return new Response(true, "Book found", searchBook);
                }
            }

            case "category" -> {
                List<Book> searchBooks = bookRepository.getBookByCategory(search);

                if(searchBooks != null){
                    return new Response(true, "Book found", searchBooks);
                } else {
                    return new Response(false, "No book in this category", null);
                }
            }
        }

        return new Response(false, search + " not found", null);
    }

    @Override
    public Response borrowBook(User user, String title) {
        final int MAX_BORROW_BOOKS = 3;

        Response response = null;
        Book toBeBorrowedBook = bookRepository.getBookByTitle(title);

        if(toBeBorrowedBook == null){
            return new Response(false, title + " not found", null);
        }

        if(borrowedBooksRepo.totalBookBorrowed(user) >= MAX_BORROW_BOOKS) {
            return new Response(false, "You can't borrow more than "
                    + MAX_BORROW_BOOKS + " books", null);
        }

        if(!toBeBorrowedBook.canBorrow()) {
            return new Response(false, "You Can't borrow " + title + ", only one copy left", null);
        }

        borrowedBooksRepo.addBorrowRecord(user, toBeBorrowedBook);
        toBeBorrowedBook.updateAvailableCopies(toBeBorrowedBook.getAvailableCopies() - 1);
        return new Response(true, "you have successfully borrowed " + title, null);
    }

    @Override
    public Map<Book, Integer> viewBorrowedBooks(User user) {
        var userBorrowedBooks = borrowedBooksRepo.getUserBorrowedBooks(user);

        return userBorrowedBooks;
    }

    @Override
    public Response returnBook(User user, String bookTitle, int returnQty) {
        var userBorrowedBook = borrowedBooksRepo.getUserBorrowedBooks(user);

        if(userBorrowedBook.isEmpty()) {
            return new Response(false, "You have not borrowed any book", null);
        }

        var toBeReturnedBook = bookRepository.getBookByTitle(bookTitle);

        if(toBeReturnedBook == null) {
            return new Response(false,
                    bookTitle + ", it is not in our system", null);
        }

        if(userBorrowedBook.get(toBeReturnedBook) == null) {
            return new Response(false, "You didn't borrow " + bookTitle, null);
        }

        int qtyBorrowed = userBorrowedBook.get(toBeReturnedBook);


        if(returnQty > qtyBorrowed) {
            String copy = qtyBorrowed > 1 ? "copies" : "copy";
            return new Response(false, "You can only return "
                    + qtyBorrowed + " " + copy + " of " + bookTitle + " or less", null);
        }

//        userBorrowedBook.merge(toBeReturnedBook, -returnQty, (oldValue, newValue) -> (oldValue + newValue <= 0)  ?
//                null: oldValue + newValue);
        userBorrowedBook.compute(toBeReturnedBook, (book, bookQty) ->
                (bookQty == null || bookQty - returnQty <= 0 ? null : bookQty - returnQty));
        toBeReturnedBook.updateAvailableCopies(toBeReturnedBook.getAvailableCopies() + returnQty);
        return new Response(true, "You have successfully return " + bookTitle, null);
    }
}
