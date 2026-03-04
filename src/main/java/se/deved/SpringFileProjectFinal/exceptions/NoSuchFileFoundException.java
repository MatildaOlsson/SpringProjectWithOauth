package se.deved.SpringFileProjectFinal.exceptions;

public class NoSuchFileFoundException extends RuntimeException {
    public NoSuchFileFoundException(String message) {
        super(message);
    }
}
