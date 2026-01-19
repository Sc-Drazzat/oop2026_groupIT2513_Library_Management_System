package edu.aitu.oop3.exceptions;

public class BookAlreadyOnLoanException extends RuntimeException {
    public BookAlreadyOnLoanException(String message) {
        super(message);
    }
}
