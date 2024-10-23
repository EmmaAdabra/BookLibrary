package main.java.com.librarySystem.user;

import main.java.com.librarySystem.model.Book;
import main.java.com.librarySystem.repository.IBookRepository;
import main.java.com.librarySystem.repository.IBorrowedBookRepo;
import main.java.com.librarySystem.repository.IUserRepository;
import main.java.com.librarySystem.util.NewResponse;

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
    public Boolean addUser(RUser newUser) {
        if(userRepository.getUserByEmail(newUser.getUserEmail()) != null){
            return false;
        }

        userRepository.add(newUser);

        return true;
    }

    @Override
    public List<RUser> getUsers() {
        List<RUser> users = userRepository.getUsers();
        if(users.size() == 0){
            return null;
        }

        return users;
    }

    @Override
    public NewResponse getAllBooks() {
        List<Book> books = bookRepository.getBooks();
        if(books.size() == 0) {
            return new NewResponse(false, "No book found", null);
        }

        return new NewResponse(true, "books found", books);
    }

    @Override
    public NewResponse getUserByEmail(String email) {
        RUser user = userRepository.getUserByEmail(email);
        if(user == null){
            return new NewResponse(false, "User not found", null);
        }

        return new NewResponse(true, "found user", user);
    }

    @Override
    public NewResponse searchBook(String searchType, String search) {
        switch (searchType) {
            case "title" -> {
                Book searchBook = bookRepository.getBookByTitle(search);

                if(searchBook != null) {
                    return new NewResponse(true, "Book found", searchBook);
                }
            }

            case "ISBN" -> {
                Book searchBook = bookRepository.getBookByISBN(search);

                if(searchBook != null){
                    return new NewResponse(true, "Book found", searchBook);
                }
            }

            case "category" -> {
                List<Book> searchBooks = bookRepository.getBookByCategory(search);

                if(searchBooks != null){
                    return new NewResponse(true, "Book found", searchBooks);
                } else {
                    return new NewResponse(false, "No book in this category", null);
                }
            }
        }

        return new NewResponse(false, search + " not found", null);
    }

    @Override
    public NewResponse borrowBook(RUser user, String title) {
        final int MAX_BORROW_BOOKS = 3;

        NewResponse response = null;
        Book toBeBorrowedBook = bookRepository.getBookByTitle(title);

        if(toBeBorrowedBook == null){
            return new NewResponse(false, title + " not found", null);
        }

        if(borrowedBooksRepo.totalBookBorrowed(user) >= MAX_BORROW_BOOKS) {
            return new NewResponse(false, "You can't borrow more than "
                    + MAX_BORROW_BOOKS + " books", null);
        }

        if(!toBeBorrowedBook.canBorrow()) {
            return new NewResponse(false, "You Can't borrow " + title + ", only one copy left", null);
        }

        borrowedBooksRepo.addBorrowRecord(user, toBeBorrowedBook);
        toBeBorrowedBook.updateAvailableCopies(toBeBorrowedBook.getAvailableCopies() - 1);
        return new NewResponse(true, "you have successfully borrowed " + title, null);
    }

    @Override
    public Map<Book, Integer> viewBorrowedBooks(RUser user) {
        var userBorrowedBooks = borrowedBooksRepo.getUserBorrowedBooks(user);

        return userBorrowedBooks;
    }

    @Override
    public NewResponse returnBook(RUser user, String bookTitle, int returnQty) {
        var userBorrowedBook = borrowedBooksRepo.getUserBorrowedBooks(user);

        if(userBorrowedBook.isEmpty()) {
            return new NewResponse(false, "You have not borrowed any book", null);
        }

        var toBeReturnedBook = bookRepository.getBookByTitle(bookTitle);

        if(toBeReturnedBook == null) {
            return new NewResponse(false,
                    bookTitle + ", it is not in our system", null);
        }

        if(userBorrowedBook.get(toBeReturnedBook) == null) {
            return new NewResponse(false, "You didn't borrow " + bookTitle, null);
        }

        int qtyBorrowed = userBorrowedBook.get(toBeReturnedBook);


        if(returnQty > qtyBorrowed) {
            String copy = qtyBorrowed > 1 ? "copies" : "copy";
            return new NewResponse(false, "You can only return "
                    + qtyBorrowed + " " + copy + " of " + bookTitle + " or less", null);
        }

//        userBorrowedBook.merge(toBeReturnedBook, -returnQty, (oldValue, newValue) -> (oldValue + newValue <= 0)  ?
//                null: oldValue + newValue);
        userBorrowedBook.compute(toBeReturnedBook, (book, bookQty) ->
                (bookQty == null || bookQty - returnQty <= 0 ? null : bookQty - returnQty));
        toBeReturnedBook.updateAvailableCopies(toBeReturnedBook.getAvailableCopies() + returnQty);
        return new NewResponse(true, "You have successfully return " + bookTitle, null);
    }
}
