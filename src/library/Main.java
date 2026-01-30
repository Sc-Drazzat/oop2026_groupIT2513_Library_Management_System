package library;
import library.repositories.BookRepository;
import library.repositories.LoanRepository;
import library.repositories.MemberRepository;
import library.service.LoanService;
import library.service.FineCalculator;
import library.controller.LibraryController;
public class Main {
    public static void main(String[] args) {
        BookRepository bookRepository = new BookRepository();
        MemberRepository memberRepository = new MemberRepository();
        LoanRepository loanRepository = new LoanRepository();
        FineCalculator fineCalculator = new FineCalculator();
        LoanService loanService = new LoanService(loanRepository, bookRepository, memberRepository, fineCalculator);
        LibraryController libraryController = new LibraryController(bookRepository, memberRepository, loanRepository, loanService);
        libraryController.run();
    }
}