package library.UI.controller;
import library.CatalogComponent.entities.Book;
import library.LoanManagementComponent.exceptions.BookAlreadyOnLoanException;
import library.MemberManagementComponent.exceptions.MemberNotFoundException;
import library.CatalogComponent.repository.BookRepository;
import library.LoanManagementComponent.repository.LoanRepository;
import library.MemberManagementComponent.repository.MemberRepository;
import library.LoanManagementComponent.service.LoanService;
import library.LoanManagementComponent.service.FineCalculator;
import library.CatalogComponent.factory.BookFactory;
import library.ReportingComponent.LoanReport;
import library.ReportingComponent.MemberSummary;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class LibraryController {
    private BookRepository bookRepository;
    private MemberRepository memberRepository;
    private LoanRepository loanRepository;
    private LoanService loanService;
    private Scanner scanner;
    private final Map<Integer, Runnable> menuActions;

    public LibraryController(BookRepository bookRepository, MemberRepository memberRepository, LoanRepository loanRepository, LoanService loanService) {
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
        this.loanRepository = loanRepository;
        this.loanService = loanService;
        this.scanner = new Scanner(System.in);
        FineCalculator fineCalculator = new FineCalculator();
        this.loanService = new LoanService(loanRepository, bookRepository, memberRepository, fineCalculator);

        this.menuActions = Map.of(
                1, this::listAvailableBooks,
                2, this::borrowBook,
                3, this::returnBook,
                4, this::viewMemberSummary,
                5, this::viewLoanReport,
                6, this::createBook,
                7, this::listOverdueLoans
        );
    }

    public void run() {
        while (true) {
            System.out.println("\nLibrary Management System");
            System.out.println("1. List Available Books");
            System.out.println("2. Borrow Book");
            System.out.println("3. Return Book");
            System.out.println("4. View Member Summary");
            System.out.println("5. View Loan Report");
            System.out.println("6. Create Book");
            System.out.println("7. List Overdue Loans");
            System.out.println("8. Exit");
            System.out.print("Choose an option: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 8.");
                continue;
            }

            if (choice == 8) {
                System.out.println("Exiting the system. Goodbye!");
                break;
            }

            Runnable action = menuActions.get(choice);
            if (action != null) {
                action.run();
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void listAvailableBooks() {
        try {
            List<Book> books = bookRepository.listAvailableBooks();
            System.out.println("Available Books:");
            if (books.isEmpty()) {
                System.out.println("No available books at the moment.");
                return;
            }
            books.stream()
                    .sorted((b1, b2) -> b1.getTitle().compareToIgnoreCase(b2.getTitle()))
                    .forEach(book ->
                            System.out.println(book.getId() + ": " + book.getTitle())
                    );
        } catch (Exception e) {
            System.out.println("Error retrieving available books: " + e.getMessage());
        }
    }

    private void borrowBook() {
        try {
            System.out.print("Enter Member ID: ");
            int memberId = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter Book ID: ");
            int bookId = Integer.parseInt(scanner.nextLine());
            loanService.borrowBook(memberId, bookId);
            System.out.println("Book borrowed successfully.");
        } catch (MemberNotFoundException | BookAlreadyOnLoanException e) {
            System.out.println("Error borrowing book: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter numeric values for IDs.");
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    private void returnBook() {
        try {
            System.out.println("Enter Loan ID to return: ");
            int loanId = Integer.parseInt(scanner.nextLine());
            loanService.returnBook(loanId);
            System.out.println("Book returned successfully.");
        } catch (Exception e) {
            System.out.println("Error returning book: " + e.getMessage());
        }
    }

    private void viewMemberSummary() {
        try {
            System.out.print("Enter Member ID: ");
            int memberId = Integer.parseInt(scanner.nextLine());
            MemberSummary summary = loanService.generateMemberSummary(memberId);
            System.out.println("Member Summary for ID " + memberId + ":");
            System.out.println("Name: " + summary.getMemberName());
            System.out.println("Active Loans: " + summary.getActiveLoans());
            System.out.println("Total Fines: $" + summary.getTotalFines());
        } catch (MemberNotFoundException e) {
            System.out.println("Error retrieving member summary: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a numeric Member ID.");
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    private void viewLoanReport() {
        try {
            System.out.print("Enter Loan ID: ");
            int loanId = Integer.parseInt(scanner.nextLine());
            LoanReport report = loanService.generateLoanReport(loanId);
            System.out.println(report);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a numeric Loan ID.");
        }
        catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    private void createBook() {
        try {
            System.out.println("Choose Book Type (1. Printed, 2. EBook, 3. Reference): ");
            int typeChoice = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter Title: ");
            String title = scanner.nextLine();
            System.out.print("Enter Author: ");
            String author = scanner.nextLine();
            System.out.print("Enter Year: ");
            int year = Integer.parseInt(scanner.nextLine());
            Book book = null;
            switch (typeChoice) {
                case 1:
                    System.out.print("Enter Number of Pages: ");
                    int pages = Integer.parseInt(scanner.nextLine());
                    book = BookFactory.createBook(0, title, author, true, "printed", pages, null, null);
                    bookRepository.save(book);
                    System.out.println("Printed Book created successfully.");
                    break;
                case 2:
                    System.out.print("Enter File Format: ");
                    String fileFormat = scanner.nextLine();
                    book = BookFactory.createBook(0, title, author, true, "ebook", 0, fileFormat, null);
                    bookRepository.save(book);
                    System.out.println("EBook created successfully.");
                    break;
                case 3:
                    System.out.print("Enter File Format: ");
                    String subjectArea = scanner.nextLine();
                    book = BookFactory.createBook(0, title, author, true, "reference", 0, null, subjectArea);
                    bookRepository.save(book);
                    System.out.println("Reference Book created successfully.");
                    break;
                default:
                    System.out.println("Invalid book type choice.");
                    break;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter numeric values where required.");
        } catch (Exception e) {
            System.out.println("Error creating book: " + e.getMessage());
        }
    }

    private void listOverdueLoans() {
        try {
            loanService.listOverdueLoans();
        } catch (Exception e) {
            System.out.println("Error listing overdue loans: " + e.getMessage());
        }
    }
}