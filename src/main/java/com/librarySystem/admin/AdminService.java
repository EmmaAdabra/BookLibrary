package main.java.com.librarySystem.admin;

import main.java.com.librarySystem.model.Book;
import main.java.com.librarySystem.repository.IBookRepository;
import main.java.com.librarySystem.repository.IBorrowedBookRepo;
import main.java.com.librarySystem.repository.IUserRepository;
import main.java.com.librarySystem.user.User;
import main.java.com.librarySystem.user.UserService;
import main.java.com.librarySystem.util.Response;

import java.util.Collections;
import java.util.Map;


public class AdminService extends UserService implements IAdminService {
//    private IBookRepository bookRepository;
//    private IUserRepository userRepository;

    public AdminService(IUserRepository userRepository, IBookRepository bookRepository,
                        IBorrowedBookRepo borrowedBooksRepo){
        super(userRepository, bookRepository, borrowedBooksRepo);
//        this.bookRepository = bookRepository;
//        this.userRepository = userRepository;
    }

    @Override
    public Response addBook(Book book) {
        if(bookRepository.getBookByISBN(book.getISBN()) != null){
            return new Response(false, book.getTitle() + " already exist", null);
        }

        bookRepository.addBook(book);
        return new Response(true, "success", null);
    }

    @Override
    public Map<User, Map<Book, Integer>> getBorrowRecord() {
        var borrowRecords = borrowedBooksRepo.getBorrowRecord();
        if(!borrowRecords.isEmpty()){
            return borrowRecords;
        }

        return Collections.emptyMap();
    }
}
