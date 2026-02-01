package library.entities;

public class EBook extends Book {
    private String fileFormat;

    public EBook(int id, String title, String author, boolean available, String fileFormat) {
        super(id, title, author, available, "ebook");
        this.fileFormat = fileFormat;
    }

    public String getFileFormat() {
        return fileFormat;
    }

    public void setFileFormat(String fileFormat) {
        this.fileFormat = fileFormat;
    }


}
