 package edu.aitu.oop3.controller;
import edu.aitu.oop3.entities.Book;
import edu.aitu.oop3.entities.Member;
import edu.aitu.oop3.exceptions.BookAlreadyOnLoanException;
import edu.aitu.oop3.exceptions.MemberNotFoundException;
import edu.aitu.oop3.repositories.BookRepository;
import edu.aitu.oop3.repositories.LoanRepository;
import edu.aitu.oop3.repositories.MemberRepository;
import edu.aitu.oop3.service.LoanService;
import edu.aitu.oop3.service.FineCalculator;

import java.util.List;
import java.util.Scanner;

public class LibraryController {
    private BookRepository bookRepository;
    private MemberRepository memberRepository;
    private LoanRepository loanRepository;
    private LoanService loanService;
    private Scanner scanner;

    public LibraryController(BookRepository bookRepository, MemberRepository memberRepository, LoanRepository loanRepository, LoanService loanService) {
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
        this.loanRepository = loanRepository;
        this.loanService = loanService;
        this.scanner = new Scanner(System.in);
        FineCalculator fineCalculator = new FineCalculator();
        this.loanService = new LoanService(loanRepository, bookRepository, memberRepository, fineCalculator);
    }
    public void run(){
        while(true){
            System.out.print("\nLibrary Menu\n");
            System.out.println("1. List available books");
            System.out.println("2. Borrow a book");
            System.out.println("3. Return a book");
            System.out.println("4. View member loans");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }
            switch (choice) {
                case 1:
                    listAvailableBooks();
                    break;
                case 2:
                    borrowBook();
                    break;
                case 3:
                    returnBook();
                    break;
                case 4:
                    viewMemberLoans();
                    break;
                case 5:
                    System.out.println("Exiting the system. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
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
            for (Book book : books) {
                System.out.println(book.getId() + ": " + book.getTitle());
            }
        } catch (Exception e) {
            System.out.println("Error retrieving available books: " + e.getMessage());
        }
    }
    private  void borrowBook() {
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
    private void viewMemberLoans()  {
        try {
            System.out.print("Enter Member ID: ");
            int memberId = Integer.parseInt(scanner.nextLine());
            Member member = memberRepository.findMemberById(memberId);
            if (member == null) {
                System.out.println("Member with ID " + memberId + " not found.");
                return;
            }
            List<String> loans = loanRepository.getLoansOfMember(memberId);
            if (loans.isEmpty()) {
                System.out.println("No loans found for member ID " + memberId + ".");
            }else  {
                System.out.println("Loans for member " + member.getName() + ":");
                for (String loan : loans) {
                    System.out.println(loan);
                }
            }
        }catch (Exception e) {
            System.out.println("Error retrieving member loans: " + e.getMessage());}
    }
}