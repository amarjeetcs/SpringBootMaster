package com.amarjeet.project.bookmanagement.using.ui;

public class Book {
    private int bookId;
    private String name;
    private String author;
    private boolean isAvailable;

    // Constructor
    public Book(int bookId, String name, String author) {
        this.bookId = bookId;
        this.name = name;
        this.author = author;
        this.isAvailable = true; // By default, a new book is available
    }

    // Getters and setters
    public int getBookId() {
        return bookId;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public String toString() {
        return "Book ID: " + bookId + ", Name: " + name + ", Author: " + author + ", Available: " +
                (isAvailable ? "Yes" : "No");
    }
}
