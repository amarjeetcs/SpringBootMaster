package com.amarjeet.project.bookmanagement.seperate;

import java.util.*;

public class LibraryManagementSystem {

	private static Library library = new Library();
	private static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		// Pre-add some books to the library for testing
		library.addBook(new Book(101, "Java Programming", "John Doe"));
		library.addBook(new Book(102, "Data Structures", "Jane Smith"));
		library.addBook(new Book(103, "Design Patterns", "Robert Martin"));
		library.addBook(new Book(104, "The Pragmatic Programmer", "Andrew Hunt"));

		// Display the menu and handle user inputs
		while (true) {
			showMenu();
			int choice = scanner.nextInt();
			scanner.nextLine(); // Consume the newline left by nextInt()

			switch (choice) {
			case 1:
				addNewBook();
				break;
			case 2:
				issueBook();
				break;
			case 3:
				returnBook();
				break;
			case 4:
				searchBooks();
				break;
			case 5:
				library.displayBooks();
				break;
			case 6:
				library.displayHistory();
				break;
			case 7:
				System.out.println("Exiting the system...");
				return;
			default:
				System.out.println("Invalid choice. Please try again.");
			}
		}
	}

	// Show the main menu
	public static void showMenu() {
		System.out.println("\nLibrary Management System");
		System.out.println("1. Add a New Book");
		System.out.println("2. Issue a Book");
		System.out.println("3. Return a Book");
		System.out.println("4. Search Books");
		System.out.println("5. Display All Books");
		System.out.println("6. Display Book History");
		System.out.println("7. Exit");
		System.out.print("Enter your choice: ");
	}

	// Add a new book to the library
	public static void addNewBook() {
		try {
			System.out.print("Enter Book ID: ");
			int bookId = scanner.nextInt();
			scanner.nextLine(); // Consume newline
			System.out.print("Enter Book Name: ");
			String bookName = scanner.nextLine();
			System.out.print("Enter Author Name: ");
			String author = scanner.nextLine();

			Book newBook = new Book(bookId, bookName, author);
			library.addBook(newBook);
			System.out.println("Book added successfully!");
		} catch (Exception e) {
			System.out.println("Invalid input. Please try again.");
			scanner.nextLine(); // Consume invalid input
		}
	}

	// Issue a book
	public static void issueBook() {
		try {
			System.out.print("Enter Book ID to issue: ");
			int bookId = scanner.nextInt();

			boolean isIssued = library.issueBook(bookId);
			if (isIssued) {
				System.out.println("Book issued successfully!");
			} else {
				System.out.println("Sorry, the book is either not available or doesn't exist.");
			}
		} catch (Exception e) {
			System.out.println("Invalid input. Please try again.");
			scanner.nextLine(); // Consume invalid input
		}
	}

	// Return a book
	public static void returnBook() {
		try {
			System.out.print("Enter Book ID to return: ");
			int bookId = scanner.nextInt();

			boolean isReturned = library.returnBook(bookId);
			if (isReturned) {
				System.out.println("Book returned successfully!");
			} else {
				System.out.println("Sorry, the book is either already available or doesn't exist.");
			}
		} catch (Exception e) {
			System.out.println("Invalid input. Please try again.");
			scanner.nextLine(); // Consume invalid input
		}
	}

	// Search for books
	public static void searchBooks() {
		System.out.println("1. Search by Name");
		System.out.println("2. Search by Author");
		System.out.print("Enter your choice: ");
		int choice = scanner.nextInt();
		scanner.nextLine(); // Consume newline

		switch (choice) {
		case 1:
			System.out.print("Enter book name to search: ");
			String name = scanner.nextLine();
			List<Book> booksByName = library.searchBooksByName(name);
			if (booksByName.isEmpty()) {
				System.out.println("No books found with the name \"" + name + "\".");
			} else {
				System.out.println("Books found by name:");
				for (Book book : booksByName) {
					System.out.println(book);
				}
			}
			break;
		case 2:
			System.out.print("Enter author name to search: ");
			String author = scanner.nextLine();
			List<Book> booksByAuthor = library.searchBooksByAuthor(author);
			if (booksByAuthor.isEmpty()) {
				System.out.println("No books found by author \"" + author + "\".");
			} else {
				System.out.println("Books found by author:");
				for (Book book : booksByAuthor) {
					System.out.println(book);
				}
			}
			break;
		default:
			System.out.println("Invalid choice.");
		}
	}
}
