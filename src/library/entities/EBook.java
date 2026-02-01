package library.entities;

public class EBook extends Book {
    private String fileFormat;
    private double fileSizeMB;

    public EBook() {}

    public EBook(String fileFormat, double fileSizeMB) {
        this.fileFormat = fileFormat;
        this.fileSizeMB = fileSizeMB;
    }

    public String getFileFormat() {
        return fileFormat;
    }

    public void setFileFormat(String fileFormat) {
        this.fileFormat = fileFormat;
    }

    public double getFileSizeMB() {
        return fileSizeMB;
    }

    public void setFileSizeMB(double fileSizeMB) {
        this.fileSizeMB = fileSizeMB;
    }

    @Override
    public String getBookType() {
        return "E-Book";
    }
}
