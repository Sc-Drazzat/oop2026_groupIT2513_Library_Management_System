package edu.aitu.oop3.repositories;
import edu.aitu.oop3.entities.Loan;
import  edu.aitu.oop3.db.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class LoanRepository {
    public Loan findLoanById(int id) {
        String query = "SELECT * FROM loans WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToLoan(rs);
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }public List<String> getLoansOfMember(int memberId) {
    List<String> loans = new ArrayList<>();
    String query = "SELECT b.title, l.loan_date, l.due_date, l.return_date " +
            "FROM loans l JOIN books b ON l.book_id = b.id " +
            "WHERE l.member_id = ?";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setInt(1, memberId);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            String title = rs.getString("title");
            LocalDate loanDate = rs.getDate("loan_date").toLocalDate();
            LocalDate dueDate = rs.getDate("due_date").toLocalDate();
            Date returnDateSql = rs.getDate("return_date");
            String returnDate = (returnDateSql != null) ? returnDateSql.toLocalDate().toString() : "Not returned";
            loans.add("Title: " + title + ", Loan Date: " + loanDate + ", Due Date: " + dueDate + ", Return Date: " + returnDate);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return loans;
}
public void addLoan(Loan loan) {
    String query = "INSERT INTO loans (book_id, member_id, loan_date, return_date, due_date) VALUES (?, ?, ?, ?, ?)";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setInt(1, loan.getBookId());
        stmt.setInt(2, loan.getMemberId());
        stmt.setDate(3, Date.valueOf(loan.getLoanDate()));
        stmt.setDate(4, loan.getReturnDate() != null ? Date.valueOf(loan.getReturnDate()) : null);
        stmt.setDate(5, Date.valueOf(loan.getDueDate()));
        stmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
public void updateLoan(Loan loan) {
    String query = "UPDATE loans SET book_id = ?, member_id = ?, loan_date = ?, return_date = ?, due_date = ? WHERE id = ?";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setInt(1, loan.getBookId());
        stmt.setInt(2, loan.getMemberId());
        stmt.setDate(3, Date.valueOf(loan.getLoanDate()));
        stmt.setDate(4, loan.getReturnDate() != null ? Date.valueOf(loan.getReturnDate()) : null);
        stmt.setDate(5, Date.valueOf(loan.getDueDate()));
        stmt.setInt(6, loan.getId());
        stmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
public void deleteLoan(int id) {
    String query = "DELETE FROM loans WHERE id = ?";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setInt(1, id);
        stmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
private Loan mapResultSetToLoan(ResultSet rs) throws SQLException {
    Loan loan = new Loan();
    loan.setId(rs.getInt("id"));
    loan.setBookId(rs.getInt("book_id"));
    loan.setMemberId(rs.getInt("member_id"));
    loan.setLoanDate(rs.getDate("loan_date").toLocalDate());
    Date returnDate = rs.getDate("return_date");
    if (returnDate != null) {
        loan.setReturnDate(returnDate.toLocalDate());
    }
    loan.setDueDate(rs.getDate("due_date").toLocalDate());
    return loan;
}
}