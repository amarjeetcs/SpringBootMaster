package com.amarjeet.project.bookmanagement.seperate;



import java.util.*;

public class Library {
    private Map<Integer, Book> books;
    private List<String> history;

    public Library() {
        books = new HashMap<>();
        history = new ArrayList<>();
    }

    // Add a new book to the library
    public void addBook(Book book) {
        books.put(book.getBookId(), book);
        history.add("Book added: " + book);
    }

    // Issue a book
    public boolean issueBook(int bookId) {
        Book book = books.get(bookId);
        if (book != null && book.isAvailable()) {
            book.setAvailable(false);
            history.add("Book issued: " + book);
            return true;
        }
        return false;
    }

    // Return a book
    public boolean returnBook(int bookId) {
        Book book = books.get(bookId);
        if (book != null && !book.isAvailable()) {
            book.setAvailable(true);
            history.add("Book returned: " + book);
            return true;
        }
        return false;
    }

    // Search for books by name
    public List<Book> searchBooksByName(String name) {
        List<Book> result = new ArrayList<>();
        for (Book book : books.values()) {
            if (book.getName().toLowerCase().contains(name.toLowerCase())) {
                result.add(book);
            }
        }
        return result;
    }

    // Search for books by author
    public List<Book> searchBooksByAuthor(String author) {
        List<Book> result = new ArrayList<>();
        for (Book book : books.values()) {
            if (book.getAuthor().toLowerCase().contains(author.toLowerCase())) {
                result.add(book);
            }
        }
        return result;
    }

    // Display all books in the library
    public void displayBooks() {
        if (books.isEmpty()) {
            System.out.println("No books available in the library.");
        } else {
            for (Book book : books.values()) {
                System.out.println(book);
            }
        }
    }

    // Display history log
    public void displayHistory() {
        if (history.isEmpty()) {
            System.out.println("No history available.");
        } else {
            for (String log : history) {
                System.out.println(log);
            }
        }
    }
}
