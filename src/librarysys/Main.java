package librarysys;
import librarysys.repositories.BookRepository;
import librarysys.repositories.LoanRepository;
import librarysys.repositories.MemberRepository;
import librarysys.service.LoanService;
import librarysys.service.FineCalculator;
import librarysys.controller.LibraryController;
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