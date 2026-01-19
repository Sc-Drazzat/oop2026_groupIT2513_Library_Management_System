package edu.aitu.oop3.repositories;
import edu.aitu.oop3.entities.Loan;
import java.util.List;

public interface LoanRepository {
    Loan findById(int id);
    List<Loan> findAll();
    void addLoan(Loan loan);
    void updateLoan(Loan loan);
    void deleteLoan(Loan loan);
}
