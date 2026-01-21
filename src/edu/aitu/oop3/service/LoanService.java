package edu.aitu.oop3.service;
import edu.aitu.oop3.entities.Loan;
import edu.aitu.oop3.entities.Book;
import edu.aitu.oop3.entities.Member;
import edu.aitu.oop3.exceptions.BookAlreadyOnLoanException;
import edu.aitu.oop3.exceptions.LoanOverdueException;
import edu.aitu.oop3.exceptions.MemberNotFoundException;
import edu.aitu.oop3.repositories.LoanRepository;
import edu.aitu.oop3.repositories.BookRepository;
import edu.aitu.oop3.repositories.MemberRepository;
import java.time.LocalDate;

public class LoanService {
    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final FineCalculator fineCalculator;

    public LoanService(LoanRepository loanRepository, BookRepository bookRepository, MemberRepository memberRepository, FineCalculator fineCalculator) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
        this.fineCalculator = fineCalculator;
    }

    public void borrowBook(int memberId, int bookId) throws MemberNotFoundException, BookAlreadyOnLoanException {
        Member member = memberRepository.findMemberById(memberId);
        if (member == null) {
            throw new MemberNotFoundException("Member with ID " + memberId + " not found.");
        }
        Book book = bookRepository.findBookById(bookId);
        if (book == null){
            throw new IllegalArgumentException("Book with ID " + bookId + " not found.");
        }
        if (!book.isAvailable()) {
            throw new BookAlreadyOnLoanException("Book with ID " + bookId + " is already on loan.");
        }
        Loan loan = new Loan(0, bookId, memberId, LocalDate.now(), null, LocalDate.now().plusWeeks(2));
        loanRepository.addLoan(loan);

        book.setAvailable(false);
        bookRepository.updateBook(book);
    }
    public  void returnBook(int loanId) throws MemberNotFoundException, BookAlreadyOnLoanException {
        Loan loan = loanRepository.findLoanById(loanId);
        if (loan == null) {
            throw new IllegalArgumentException("No active loan found for book ID " + loanId + ".");
        }
        LocalDate returnDate = LocalDate.now();
        loan.setReturnDate(returnDate);
        double fine = fineCalculator.calculateFine(loan.getDueDate(), loan.getReturnDate());
        if (fine > 0) {
            System.out.println("Loan is overdue. Fine amount: $" + fine);
        }
        loanRepository.updateLoan(loan);
        Book book = bookRepository.findBookById(loanId);
        book.setAvailable(true);
        bookRepository.updateBook(book);
    }
}