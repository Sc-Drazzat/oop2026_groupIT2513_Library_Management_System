package edu.aitu.oop3;
import edu.aitu.oop3.repositories.BookRepository;
import edu.aitu.oop3.repositories.LoanRepository;
import edu.aitu.oop3.repositories.MemberRepository;
import edu.aitu.oop3.service.LoanService;
import edu.aitu.oop3.service.FineCalculator;
import edu.aitu.oop3.controller.LibraryController;
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