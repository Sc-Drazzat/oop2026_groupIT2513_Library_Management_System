package library.entities;

public class ReferenceBook extends  Book {
    private String subjectArea;

    public ReferenceBook(int id, String title, String author, boolean available, String SubjectArea) {
        super(id, title, author, available, "reference");
        this.subjectArea = SubjectArea;
    }

    public String getSubjectArea() {
        return subjectArea;
    }

    public void setSubjectArea(String subjectArea) {
        this.subjectArea = subjectArea;
    }
}
