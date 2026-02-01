package library.entities;

public class PrintedBook extends Book {
    private int numberOfPages;

    public PrintedBook() {}

    public PrintedBook(int id, String title, String author, boolean available, int numberOfPages) {
        super(id, title, author, available);
        setNumberOfPages(numberOfPages);
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }
    @Override
    public String getBookType() {
        return "Printed";
    }
}
