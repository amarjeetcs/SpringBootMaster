package com.amarjeet.librarymanagementinsingleclass;

import java.util.*;

public class ConsoleLibraryManagementSystemApplication {

	// Book class to represent a book
	static class Book {
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

		// Getters and Setters
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
			return "Book ID: " + bookId + ", Name: " + name + ", Author: " + author + ", Available: "
					+ (isAvailable ? "Yes" : "No");
		}
	}

	// Library class to manage the collection of books
	static class Library {
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

	// Main class for managing user interaction
	static class LibraryManagement {

		private static Library library = new Library();
		private static Scanner scanner = new Scanner(System.in);

		public static void main(String[] args) {
			// Pre-add some books to the library for testing
//			library.addBook(new Book(101, "Java Programming", "John Doe"));
//			library.addBook(new Book(102, "Data Structures", "Jane Smith"));
//			library.addBook(new Book(103, "Design Patterns", "Robert Martin"));
//			library.addBook(new Book(104, "The Pragmatic Programmer", "Andrew Hunt"));

			// Display the menu and handle user inputs
			while (true) {
				showMenu();
				int choice = scanner.nextInt();
				scanner.nextLine(); // Consume the newline left by nextInt()
				//editbook, deletebook
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
}
