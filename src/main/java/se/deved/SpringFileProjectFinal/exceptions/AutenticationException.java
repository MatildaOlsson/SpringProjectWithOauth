package se.deved.SpringFileProjectFinal.exceptions;

public class AutenticationException extends RuntimeException {
    public AutenticationException(String message) {
        super(message);
    }
}
