package com.amarjeet.project.bookmanagement.using.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class LibraryManagementUI extends JFrame {
    private LibraryManagementSystem librarySystem;
    private JTextField bookIdField, nameField, authorField;
    private JTextArea displayArea, historyArea;
    private JButton addButton, issueButton, returnButton, displayButton, searchButton, historyButton, exitButton;

    public LibraryManagementUI() {
        librarySystem = new LibraryManagementSystem();
        initUI();
    }

    private void initUI() {
        setTitle("Library Management System");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel for input fields
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 2, 10, 10));

        inputPanel.add(new JLabel("Book ID:"));
        bookIdField = new JTextField();
        inputPanel.add(bookIdField);

        inputPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Author:"));
        authorField = new JTextField();
        inputPanel.add(authorField);

        add(inputPanel, BorderLayout.NORTH);

        // Text areas to display books and history
        displayArea = new JTextArea(10, 40);
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        add(scrollPane, BorderLayout.CENTER);

        historyArea = new JTextArea(5, 40);
        historyArea.setEditable(false);
        JScrollPane historyScrollPane = new JScrollPane(historyArea);
        add(historyScrollPane, BorderLayout.SOUTH);

        // Buttons panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        addButton = new JButton("Add Book");
        buttonPanel.add(addButton);

        issueButton = new JButton("Issue Book");
        buttonPanel.add(issueButton);

        returnButton = new JButton("Return Book");
        buttonPanel.add(returnButton);

        displayButton = new JButton("Display Books");
        buttonPanel.add(displayButton);

        searchButton = new JButton("Search Books");
        buttonPanel.add(searchButton);

        historyButton = new JButton("Display History");
        buttonPanel.add(historyButton);

        exitButton = new JButton("Exit");
        buttonPanel.add(exitButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Button listeners using anonymous inner classes
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addNewBook();
            }
        });

        issueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                issueBook();
            }
        });

        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                returnBook();
            }
        });

        displayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayBooks();
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchBooks();
            }
        });

        historyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayHistory();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    // Add New Book
    private void addNewBook() {
        try {
            int bookId = Integer.parseInt(bookIdField.getText());
            String name = nameField.getText();
            String author = authorField.getText();

            librarySystem.addBook(bookId, name, author);
            clearFields();
            displayBooks();  // Refresh the display after adding a new book
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please try again.");
        }
    }

    // Issue Book
    private void issueBook() {
        try {
            int bookId = Integer.parseInt(bookIdField.getText());
            if (librarySystem.issueBook(bookId)) {
                JOptionPane.showMessageDialog(this, "Book issued successfully!");
                displayBooks();  // Refresh the book list and availability status after issuing
            } else {
                JOptionPane.showMessageDialog(this, "Book not available or doesn't exist.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please try again.");
        }
    }

    // Return Book
    private void returnBook() {
        try {
            int bookId = Integer.parseInt(bookIdField.getText());
            if (librarySystem.returnBook(bookId)) {
                JOptionPane.showMessageDialog(this, "Book returned successfully!");
                displayBooks();  // Refresh the book list and availability status after returning
            } else {
                JOptionPane.showMessageDialog(this, "Book not found or already available.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please try again.");
        }
    }

    // Display All Books
    private void displayBooks() {
        List<Book> books = librarySystem.getAllBooks();
        displayArea.setText(""); // Clear the current display
        if (books.isEmpty()) {
            displayArea.append("No books available in the library.\n");
        } else {
            for (Book book : books) {
                displayArea.append(book + "\n");  // Display book availability status
            }
        }
    }

    // Search for Books
    private void searchBooks() {
        String searchTerm = JOptionPane.showInputDialog(this, "Enter book name or author to search:");
        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            List<Book> books = librarySystem.searchBooks(searchTerm);
            displayArea.setText("");  // Clear the current display
            if (books.isEmpty()) {
                displayArea.append("No books found matching the search term.\n");
            } else {
                for (Book book : books) {
                    displayArea.append(book + "\n");
                }
            }
        }
    }

    // Display Action History
    private void displayHistory() {
        List<String> history = librarySystem.getHistory();
        historyArea.setText("");  // Clear history area
        if (history.isEmpty()) {
            historyArea.append("No history available.\n");
        } else {
            for (String log : history) {
                historyArea.append(log + "\n");
            }
        }
    }

    // Clear input fields
    private void clearFields() {
        bookIdField.setText("");
        nameField.setText("");
        authorField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LibraryManagementUI().setVisible(true);
            }
        });
    }
}
