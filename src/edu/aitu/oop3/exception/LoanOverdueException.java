package edu.aitu.oop3.exception;

public class LoanOverdueException extends RuntimeException {
    public LoanOverdueException(String message) {
        super(message);
    }
}
