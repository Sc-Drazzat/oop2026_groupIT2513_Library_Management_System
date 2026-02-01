package librarysys.reports;

import java.time.LocalDate;

public class LoanReport {
    private final int loanId;
    private final int memberId;
    private final int bookId;
    private final LocalDate loanDate;
    private final LocalDate dueDate;
    private final LocalDate returnDate;
    private final boolean overdue;
    private final double fine;

    private LoanReport(Builder builder) {
        this.loanId = builder.loanId;
        this.memberId = builder.memberId;
        this.bookId = builder.bookId;
        this.loanDate = builder.loanDate;
        this.dueDate = builder.dueDate;
        this.returnDate = builder.returnDate;
        this.overdue = builder.overdue;
        this.fine = builder.fine;
    }
    public int getLoanId() {
        return loanId;
    }
    public int getMemberId() {
        return memberId;
    }
    public int getBookId() {
        return bookId;
    }
    public LocalDate getLoanDate() {
        return loanDate;
    }
    public LocalDate getDueDate() {
        return dueDate;
    }
    public LocalDate getReturnDate() {
        return returnDate;
    }
    public boolean isOverdue() {
        return overdue;
    }
    public double getFine() {
        return fine;
    }
    @Override
    public String toString() {
        return "LoanReport{" +
                "loanId=" + loanId +
                ", memberId=" + memberId +
                ", bookId=" + bookId +
                ", loanDate=" + loanDate +
                ", dueDate=" + dueDate +
                ", returnDate=" + returnDate +
                ", overdue=" + overdue +
                ", fine=" + fine +
                '}';
    }

    public static class Builder {
        private int loanId;
        private int memberId;
        private int bookId;
        private LocalDate loanDate;
        private LocalDate dueDate;
        private LocalDate returnDate;
        private boolean overdue;
        private double fine;

        public Builder setLoanId(int loanId) {
            this.loanId = loanId;
            return this;
        }
        public Builder setMemberId(int memberId) {
            this.memberId = memberId;
            return this;
        }
        public Builder setBookId(int bookId) {
            this.bookId = bookId;
            return this;
        }
        public Builder setLoanDate(LocalDate loanDate) {
            this.loanDate = loanDate;
            return this;
        }
        public Builder setDueDate(LocalDate dueDate) {
            this.dueDate = dueDate;
            return this;
        }
        public Builder setReturnDate(LocalDate returnDate) {
            this.returnDate = returnDate;
            return this;
        }
        public Builder setOverdue(boolean overdue) {
            this.overdue = overdue;
            return this;
        }
        public Builder setFine(double fine) {
            this.fine = fine;
            return this;
        }
        public LoanReport build() {
            return new LoanReport(this);
        }
    }
}
