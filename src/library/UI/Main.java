package library.UI;
import library.CatalogComponent.repository.BookRepository;
import library.LoanManagementComponent.repository.LoanRepository;
import library.MemberManagementComponent.repository.MemberRepository;
import library.LoanManagementComponent.service.LoanService;
import library.LoanManagementComponent.service.FineCalculator;
import library.UI.controller.LibraryController;
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