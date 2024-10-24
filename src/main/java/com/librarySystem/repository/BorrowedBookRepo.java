package main.java.com.librarySystem.repository;

import main.java.com.librarySystem.model.Book;
import main.java.com.librarySystem.user.User;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class BorrowedBookRepo implements IBorrowedBookRepo {
    Map<User, Map<Book, Integer>> borrowRecords = new HashMap<>();

    @Override
    public boolean addBorrowRecord(User user, Book book) {
        if(user == null || book == null) {
            return false;
        }

//        uses computeIfAbsent method
//        Map<Book, Integer> userBorrowedBooks = borrowRecords.computeIfAbsent(user, k -> new HashMap<>());

//        uses merge method
//        userBorrowedBooks.merge(book, 1, Integer::sum);

//        uses compute method
//        userBorrowedBooks.compute(book, (b, count) -> (count == null) ? 1 : count + 1);

//        if(userBorrowedBooks.containsKey(book)){
//            int currentNumberOfBorrowedBk = userBorrowedBooks.get(book);
//            userBorrowedBooks.put(book, currentNumberOfBorrowedBk + 1);
//        } else {
//            userBorrowedBooks.put(book, 1);
//        }

//        uses getOrDefault method
        Map<Book, Integer> userBorrowedBooks = borrowRecords.getOrDefault(user, new HashMap<>());
        userBorrowedBooks.put(book, userBorrowedBooks.getOrDefault(book, 0) + 1);
        borrowRecords.put(user, userBorrowedBooks);

        return true;
    }

    @Override
    public Map<Book, Integer> getUserBorrowedBooks(User user) {
        return borrowRecords.getOrDefault(user, Collections.emptyMap());
    }

    @Override
    public int totalBookBorrowed(User user) {
        var userBorrowedBooks = getUserBorrowedBooks(user);

        if(userBorrowedBooks == null) return 0;

        int totalBookBorrowed = userBorrowedBooks.values().stream().mapToInt(Integer::intValue).sum();

        return totalBookBorrowed;
    }

    @Override
    public Map<User, Map<Book, Integer>> getBorrowRecord() {
        return borrowRecords;
    }
}

