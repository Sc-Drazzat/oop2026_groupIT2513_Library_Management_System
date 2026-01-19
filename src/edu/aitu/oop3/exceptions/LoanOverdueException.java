package edu.aitu.oop3.exceptions;

public class LoanOverdueException extends RuntimeException {
    public LoanOverdueException(String message) {
        super(message);
    }
}
