import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

class Book {
    private String title;
    private String author;
    private int yearOfPublication;
    private int edition;

    public Book(String title, String author, int yearOfPublication, int edition) {
        this.title = title;
        this.author = author;
        this.yearOfPublication = yearOfPublication;
        this.edition = edition;
    }

    // Getters and setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYearOfPublication() {
        return yearOfPublication;
    }

    public void setYearOfPublication(int yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
    }

    public int getEdition() {
        return edition;
    }

    public void setEdition(int edition) {
        this.edition = edition;
    }

    @Override
    public String toString() {
        return "Title: " + title + ", Author: " + author + ", Year of Publication: " + yearOfPublication + ", Edition: " + edition;
    }
}

class User {
    private String name;
    private int ID;
    private List<BorrowingRecord> borrowingRecords;

    public User(String name, int ID) {
        this.name = name;
        this.ID = ID;
        this.borrowingRecords = new ArrayList<>();
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public List<BorrowingRecord> getBorrowingRecords() {
        return borrowingRecords;
    }

    public void addBorrowingRecord(BorrowingRecord record) {
        borrowingRecords.add(record);
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", ID=" + ID +
                '}';
    }
}

class BorrowingRecord {
    private Book book;
    private Date issueDate;
    private Date returnDate;

    public BorrowingRecord(Book book, Date issueDate) {
        this.book = book;
        this.issueDate = issueDate;
        // Set return date as issue date plus 14 days
        long returnTime = issueDate.getTime() + 14 * 24 * 60 * 60 * 1000L;
        this.returnDate = new Date(returnTime);
    }

    // Getters and setters
    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return "Title: " + book.getTitle() + ", Author: " + book.getAuthor() + ", Issue Date: " + dateFormat.format(issueDate);
    }
}

class Library {
    private List<Book> books;
    private List<User> users;

    public Library() {
        this.books = new ArrayList<>();
        this.users = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void removeBook(Book book) {
        books.remove(book);
    }

    public Book searchBookByTitle(String title) {
        for (Book book : books) {
            if (book.getTitle().equals(title)) {
                return book;
            }
        }
        return null;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void removeUser(User user) {
        users.remove(user);
    }

    public void displayAllBooks() {
        System.out.println("\nLibrary Books:");
        for (Book book : books) {
            System.out.println(book);
        }
    }

    public void displayUserBorrowingHistory(User user) {
        List<BorrowingRecord> borrowingRecords = user.getBorrowingRecords();
        if (borrowingRecords.isEmpty()) {
            System.out.println("\nNo borrowing history for " + user.getName());
        } else {
            System.out.println("\nBorrowing history of " + user.getName() + ":");
            for (BorrowingRecord record : borrowingRecords) {
                System.out.println(record);
            }
        }
    }

    public void returnBook(User user, Book book) {
        for (BorrowingRecord record : user.getBorrowingRecords()) {
            if (record.getBook().equals(book) && record.getReturnDate() == null) {
                record.setReturnDate(new Date());
                break; 
            }
        }
    }

    public void displayReturnDetails(User user) {
        System.out.println("\nBooks to be Returned by " + user.getName() + ":");
        for (BorrowingRecord record : user.getBorrowingRecords()) {
            if (record.getReturnDate() != null) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                System.out.println("Title: " + record.getBook().getTitle() + ", Author: " + record.getBook().getAuthor() + ", Due Date: " + dateFormat.format(record.getReturnDate()));
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Library library = new Library();
        Scanner scanner = new Scanner(System.in);

        // Adding books
        Book book1 = new Book("Harry Potter", "J.K. Rowling", 1997, 1);
        Book book2 = new Book("To Kill a Mockingbird", "Harper Lee", 1960, 1);
        Book book3 = new Book("The Great Gatsby", "F. Scott Fitzgerald", 1925, 1);
        Book book4 = new Book("1984", "George Orwell", 1949, 1);
        Book book5 = new Book("The Catcher in the Rye", "J.D. Salinger", 1951, 1);

        library.addBook(book1);
        library.addBook(book2);
        library.addBook(book3);
        library.addBook(book4);
        library.addBook(book5);

        // Getting user details
        System.out.println("Welcome! Please Enter User Details:");
        System.out.print("Name: ");
        String userName = scanner.nextLine();
        System.out.print("ID: ");
        int userID = scanner.nextInt();
        scanner.nextLine(); // Consume newline character
        User user = new User(userName, userID);
        library.addUser(user);

        BorrowingRecord record1 = new BorrowingRecord(book1, new Date());
        user.addBorrowingRecord(record1);
        BorrowingRecord record2 = new BorrowingRecord(book2, new Date());
        user.addBorrowingRecord(record2);

        System.out.println("\nHello " + user.getName() + "!");

        // Displaying menu
        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Borrowed Details");
            System.out.println("2. Return Details");
            System.out.println("3. All Book Details");
            System.out.println("4. Exit");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    library.displayUserBorrowingHistory(user);
                    break;
                case 2:
                    library.returnBook(user, book1); // Simulating return of book1
                    library.returnBook(user, book2); // Simulating return of book2
                    library.displayReturnDetails(user);
                    break;
                case 3:
                    library.displayAllBooks();
                    break;
                case 4:
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid option!");
            }
        }
    }
}

