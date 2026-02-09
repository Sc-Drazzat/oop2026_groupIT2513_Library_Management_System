package library.LoanManagementComponent.service;
import library.LoanManagementComponent.entities.Loan;

public class FineCalculator {
    public double calculateFine(Loan loan) {
        if (!loan.isOverdue()){
            return 0;
        }
        long overdueDays = loan.daysOverdue();
        double fine = overdueDays* FinePolicy.getInstance().getFinePerDay();
        return fine;
    }
}