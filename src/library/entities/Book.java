package library.entities;

public abstract class Book {

    protected  int id;
    protected String title;
    protected String author;
    protected boolean available;

    public Book() {}

    public Book(int id, String title, String author, boolean available) {
        setId(id);
        setTitle(title);
        setAuthor(author);
        setAvailable(available);
    }
    public abstract String getBookType();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
