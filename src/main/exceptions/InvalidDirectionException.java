package main.exceptions;

public class InvalidDirectionException extends RuntimeException {
    public InvalidDirectionException(String message) {
        super(message);
    }
}
