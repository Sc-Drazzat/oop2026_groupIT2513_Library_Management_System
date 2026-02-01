package library.entities;

public class ReferenceBook extends  Book {
    private String subjectArea;

    public ReferenceBook() {}

    public ReferenceBook(int id, String title, String author, boolean available, String subjectArea) {
        super(id, title, author, available);
        setSubjectArea(subjectArea);
    }

    public String getSubjectArea() {
        return subjectArea;
    }

    public void setSubjectArea(String subjectArea) {
        this.subjectArea = subjectArea;
    }

    @Override
    public String getBookType() {
        return "Reference";
    }

}
