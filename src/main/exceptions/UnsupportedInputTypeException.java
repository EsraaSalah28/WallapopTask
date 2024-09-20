package main.exceptions;

public class UnsupportedInputTypeException extends RuntimeException {
    public UnsupportedInputTypeException(String message) {
        super(message);
    }
}
