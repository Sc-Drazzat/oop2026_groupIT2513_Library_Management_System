package library.entities;

public class PrintedBook extends Book {
    private int numberOfPages;

    public PrintedBook() {}

    public PrintedBook(int id, String title, String author, boolean available, int pages) {
        super(id, title, author, available, "printed");
        setNumberOfPages(pages);
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }
}
