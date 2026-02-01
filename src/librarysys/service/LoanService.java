package librarysys.service;
import librarysys.entities.Loan;
import librarysys.entities.Book;
import librarysys.entities.Member;
import librarysys.exceptions.BookAlreadyOnLoanException;
import librarysys.exceptions.LoanOverdueException;
import librarysys.exceptions.MemberNotFoundException;
import librarysys.reports.MemberSummary;
import  librarysys.reports.LoanReport;
import librarysys.repositories.LoanRepository;
import librarysys.repositories.BookRepository;
import librarysys.repositories.MemberRepository;
import librarysys.config.LibraryConfig;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


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
        Member member = memberRepository.findById(memberId);
        if (member == null) {
            throw new MemberNotFoundException("Member with ID " + memberId + " not found.");
        }
        LibraryConfig config = LibraryConfig.getInstance();
        int activeLoans = 0;
        for (Loan loan : loanRepository.findAll()) {
            if (loan.getMemberId() == memberId && loan.getReturnDate() == null) {
                activeLoans++;
            }
        }

        if (activeLoans >= config.getMaxBooksPerMember()) {
            throw new IllegalStateException("Max Books per Member: " + config.getMaxBooksPerMember());
        }

        Book book = bookRepository.findById(bookId);
        if (book == null) {
            throw new IllegalArgumentException("Book with ID " + bookId + " not found.");
        }
        if (!book.isAvailable()) {
            throw new BookAlreadyOnLoanException("Book with ID " + bookId + " is already on loan.");
        }
        Loan loan = new Loan(0, bookId, memberId, LocalDate.now(), null, LocalDate.now().plusDays(config.getLoanPeriodDays()));
        loanRepository.save(loan);

        book.setAvailable(false);
        bookRepository.update(book);
    }

    public void returnBook(int loanId) throws MemberNotFoundException, BookAlreadyOnLoanException, LoanOverdueException {
        Loan loan = loanRepository.findById(loanId);
        if (loan == null) {
            throw new IllegalArgumentException("No active loan found for book ID " + loanId + ".");
        }
        LocalDate returnDate = LocalDate.now();
        loan.setReturnDate(returnDate);
        double fine = fineCalculator.calculateFine(loan);
        if (fine > 0) {
            throw new LoanOverdueException("Loan is overdue. Fine amount: $" + fine);
        }
        loanRepository.update(loan);
        Book book = bookRepository.findById(loanId);
        book.setAvailable(true);
        bookRepository.update(book);
    }
    public void listOverdueLoans() {
        loanRepository.findAll().stream()
                .filter(Loan::isOverdue)
                .forEach(loan ->
                        System.out.println("Loan ID " + loan.getId() + " is overdue")
                );
    }

    public LoanReport generateLoanReport(int loanId) {
        Loan loan = loanRepository.findById(loanId);
        if (loan == null) {
            throw new IllegalArgumentException("No loan found with ID " + loanId + ".");
        }
        Member member = memberRepository.findById(loan.getMemberId());
        if (member == null) {
            throw new MemberNotFoundException("Member not found for loan ID " + loanId + ".");
        }
        return new LoanReport.Builder()
                .setLoanId(loan.getId())
                .setMemberId(member.getId())
                .setBookId(loan.getBookId())
                .setLoanDate(loan.getLoanDate())
                .setDueDate(loan.getDueDate())
                .setReturnDate(loan.getReturnDate())
                .build();
    }

    public MemberSummary generateMemberSummary(int memberId) {
        Member member = memberRepository.findById(memberId);
        if (member == null) {
            throw new MemberNotFoundException("Member not found");
        }
        List<Loan> loans = loanRepository.findAll();
        List<String> borrowedBookTitles = new ArrayList<>();
        int activeLoans = 0;
        int totalLoans = 0;
        double totalFines = 0;
        for (Loan loan : loans) {
            if (loan.getMemberId() == memberId) {
                totalLoans++;
                if (loan.getReturnDate() == null) activeLoans++;
                totalFines += fineCalculator.calculateFine(loan);
            }
        }
        return new MemberSummary.Builder()
                .setMemberId(member.getId())
                .setMemberName(member.getName())
                .setTotalLoans(totalLoans)
                .setActiveLoans(activeLoans)
                .setTotalFines(totalFines)
                .build();
    }
}