package library.repositories;
import library.entities.*;
import library.db.DatabaseConnection;
import library.factory.BookFactory;

import java.util.List;
import java.util.ArrayList;
import java.sql.*;

public class BookRepository implements CrudRepository<Book, Integer> {
    @Override
    public Book findById(Integer id) {
        String query = "SELECT * FROM books WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToBook(rs);
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public  List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM books";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                books.add(mapResultSetToBook(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    public List<Book> listAvailableBooks() {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM books WHERE available = TRUE";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                books.add(mapResultSetToBook(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    @Override
    public Book save(Book book) {
        String query = "INSERT INTO books (title, author, available, type, pages, format, subject_area) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            fillPreparedStatement(stmt, book);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return book;
    }
    @Override
    public Book update(Book book) {
        String query = "UPDATE books SET title = ?, author = ?, available = ?, type = ?, pages = ?, format = ?, subject_area = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            fillPreparedStatement(stmt, book);
            stmt.setInt(8, book.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return book;
    }
    @Override
    public void deleteById(Integer id) {
        String query = "DELETE FROM books WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private Book mapResultSetToBook(ResultSet rs) throws SQLException {
        return BookFactory.createBook(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("author"),
                rs.getBoolean("available"),
                rs.getString("type"),
                rs.getInt("pages"),
                rs.getString("format"),
                rs.getString("subject_area")

        );
    }

    private void fillPreparedStatement(PreparedStatement stmt, Book book) throws SQLException {
        stmt.setString(1, book.getTitle());
        stmt.setString(2, book.getAuthor());
        stmt.setBoolean(3, book.isAvailable());
        stmt.setString(4, book.getType());

        if (book instanceof PrintedBook) {
            stmt.setInt(5, ((PrintedBook) book).getPages());
            stmt.setNull(6, Types.VARCHAR);
            stmt.setNull(7, Types.VARCHAR);
        } else if (book instanceof EBook) {
            stmt.setNull(5, Types.INTEGER);
            stmt.setString(6, ((EBook) book).getFileFormat());
            stmt.setNull(7, Types.VARCHAR);
        } else if (book instanceof ReferenceBook) {
            stmt.setNull(5, Types.INTEGER);
            stmt.setNull(6, Types.VARCHAR);
            stmt.setString(7, ((ReferenceBook) book).getSubjectArea());
        } else {
            stmt.setNull(5, Types.INTEGER);
            stmt.setNull(6, Types.VARCHAR);
            stmt.setNull(7, Types.VARCHAR);
        }
    }
}