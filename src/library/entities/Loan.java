package library.entities;
import java.time.LocalDate;

public class Loan {

    private int id;
    private int bookId;
    private int memberId;
    private LocalDate loanDate;
    private LocalDate returnDate;
    private LocalDate dueDate;

    public Loan(){}

    public Loan(int id, int bookId, int memberId, LocalDate loanDate, LocalDate returnDate, LocalDate dueDate) {
        setId(id);
        setBookId(bookId);
        setMemberId(memberId);
        setLoanDate(loanDate);
        setReturnDate(returnDate);
        setDueDate(dueDate);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isOverdue() {
        LocalDate currentReturnDate = (returnDate != null) ? returnDate : LocalDate.now();
        return currentReturnDate.isAfter(dueDate);
    }

    public long daysOverdue() {
        if (!isOverdue()) {
            return 0;
        }
        LocalDate currentReturnDate = (returnDate != null) ? returnDate : LocalDate.now();
        return java.time.temporal.ChronoUnit.DAYS.between(dueDate, currentReturnDate);
    }
}
