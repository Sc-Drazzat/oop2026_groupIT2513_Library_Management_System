package edu.aitu.oop3.repositories;
import edu.aitu.oop3.entities.Book;
import java.util.List;

public interface BookRepository {
    Book findById(int id);
    List<Book> ListAvailableBooks();
    void addBook(Book book);
    void updateBook(Book book);
    void deleteBook(int id);
}
