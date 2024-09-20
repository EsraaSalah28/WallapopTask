package main.enums;

import main.exceptions.InvalidInstructionException;

public enum Instructions {
    FORWARD('f'),
    BACKWARD('b'),
    LEFT('l'),
    RIGHT('r');

    private final char value;

    // Constructor to initialize the value
    Instructions(char value) {
        this.value = value;
    }


    // Getter to access the actual value
    public char getValue() {
        return value;
    }


    public static Instructions fromString(String instructionsStr) {
        if (instructionsStr == null || instructionsStr.length() != 1) {
            throw new InvalidInstructionException("Invalid input: " + instructionsStr);
        }

        char instructionChar = Character.toLowerCase(instructionsStr.charAt(0));

        for (Instructions instruction : Instructions.values()) {
            if (instruction.getValue() == instructionChar) {
                return instruction;
            }
        }

        // Throw an exception if the input does not match any enum values
        throw new InvalidInstructionException("Invalid direction: " + instructionsStr);
    }
}
