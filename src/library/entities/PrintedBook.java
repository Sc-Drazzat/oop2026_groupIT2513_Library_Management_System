package library.entities;

public class PrintedBook extends Book {
    private int pages;

    public PrintedBook() {}

    public PrintedBook(int id, String title, String author, boolean available, int pages) {
        super(id, title, author, available, "printed");
        setNumberOfPages(pages);
    }

    public int getPages() {
        return pages;
    }

    public void setNumberOfPages(int pages) {
        this.pages = pages;
    }
}
