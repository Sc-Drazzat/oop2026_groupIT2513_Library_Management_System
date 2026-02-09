package library.LoanManagementComponent.service;


public class FinePolicy {
    private static FinePolicy instance;
    private double finePerDay = 5.0;
    private FinePolicy() {}
    public static FinePolicy getInstance() {
        if (instance == null) {
            instance = new FinePolicy();
        }
        return instance;
    }

    public double getFinePerDay() {
        return finePerDay;
    }

    public void setFinePerDay(double finePerDay) {
        this.finePerDay = finePerDay;
    }
}