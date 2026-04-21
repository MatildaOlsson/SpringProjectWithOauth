package se.deved.SpringFileProjectFinal.exceptions;

public class NoSuchUserFoundException extends RuntimeException {
    public NoSuchUserFoundException(String message) {
        super(message);
    }
}
