package edu.aitu.oop3.service;
import java.time.LocalDate;

public class FineCalculator {
    private double finePerDay;

    public  FineCalculator() {
        this.finePerDay = 5.0;
    }
    public FineCalculator(double finePerDay) {
        this.finePerDay = finePerDay;
    }

    public double calculateFine(LocalDate dueDate, LocalDate returnDate) {
        if (returnDate == null || dueDate == null) {
            return 0.0;
        }
        if (returnDate.isAfter(dueDate)) {
            long daysOverdue = java.time.temporal.ChronoUnit.DAYS.between(dueDate, returnDate);
            return daysOverdue * finePerDay;
        }
        return 0.0;
    }
}