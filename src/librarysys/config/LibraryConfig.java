package librarysys.config;

public class LibraryConfig {
    private static LibraryConfig instance;
    private int loanPeriodDays;
    private int maxBooksPerMember;
    private String libraryName;

    private  LibraryConfig() {
        setLoanPeriodDays(14);
        setMaxBooksPerMember(5);
        setLibraryName("Best Library");
    }
    public static LibraryConfig getInstance() {
        if (instance == null) {
            instance = new LibraryConfig();
        }
        return instance;
    }
    public int getLoanPeriodDays() {
        return loanPeriodDays;
    }
    public void setLoanPeriodDays(int loanPeriodDays) {
        this.loanPeriodDays = loanPeriodDays;
    }
    public int getMaxBooksPerMember() {
        return maxBooksPerMember;
    }
    public void setMaxBooksPerMember(int maxBooksPerMember) {
        this.maxBooksPerMember = maxBooksPerMember;
    }
    public String getLibraryName() {
        return libraryName;
    }
    public void setLibraryName(String libraryName) {
        this.libraryName = libraryName;
    }
}