package com.amarjeet.project.bookmanagement.using.ui;

import java.sql.*;
import java.util.*;

public class LibraryManagementSystem {

    // Add a new book to the database
    public void addBook(int bookId, String name, String author) {
        String query = "INSERT INTO books (book_id, name, author, is_available) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, bookId);
            stmt.setString(2, name);
            stmt.setString(3, author);
            stmt.setBoolean(4, true); // New book is available by default
            stmt.executeUpdate();
            logHistory("Book added: " + name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Issue a book
    public boolean issueBook(int bookId) {
        String query = "UPDATE books SET is_available = ? WHERE book_id = ?";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setBoolean(1, false); // Mark as not available
            stmt.setInt(2, bookId);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                logHistory("Book issued: " + bookId);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Return a book
    public boolean returnBook(int bookId) {
        String query = "UPDATE books SET is_available = ? WHERE book_id = ?";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setBoolean(1, true); // Mark as available
            stmt.setInt(2, bookId);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                logHistory("Book returned: " + bookId);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Search for books by name or author
    public List<Book> searchBooks(String searchTerm) {
        List<Book> result = new ArrayList<>();
        String query = "SELECT * FROM books WHERE name LIKE ? OR author LIKE ?";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, "%" + searchTerm + "%");
            stmt.setString(2, "%" + searchTerm + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int bookId = rs.getInt("book_id");
                String name = rs.getString("name");
                String author = rs.getString("author");
                boolean isAvailable = rs.getBoolean("is_available");
                result.add(new Book(bookId, name, author));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // Get all books from the database
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM books";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int bookId = rs.getInt("book_id");
                String name = rs.getString("name");
                String author = rs.getString("author");
                boolean isAvailable = rs.getBoolean("is_available");
                books.add(new Book(bookId, name, author));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    // Log history of actions (Add, Issue, Return)
    private void logHistory(String action) {
        String query = "INSERT INTO book_history (action) VALUES (?)";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, action);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get action history from the database
    public List<String> getHistory() {
        List<String> history = new ArrayList<>();
        String query = "SELECT * FROM book_history ORDER BY action_time DESC";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String action = rs.getString("action");
                Timestamp actionTime = rs.getTimestamp("action_time");
                history.add(action + " at " + actionTime);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return history;
    }
}
