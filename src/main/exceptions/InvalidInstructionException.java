package main.exceptions;

public class InvalidInstructionException extends RuntimeException{
    public InvalidInstructionException(String msg){
        super(msg);
    }
}
