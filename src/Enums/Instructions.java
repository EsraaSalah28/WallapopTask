package Enums;

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


    public static Instructions fromString(String directionStr) {
        if (directionStr == null || directionStr.length() != 1) {
            throw new IllegalArgumentException("Invalid input: " + directionStr);
        }

        char directionChar = Character.toLowerCase(directionStr.charAt(0));

        for (Instructions direction : Instructions.values()) {
            if (direction.getValue() == directionChar) {
                return direction;
            }
        }

        // Throw an exception if the input does not match any enum values
        throw new IllegalArgumentException("Invalid direction: " + directionStr);
    }
}
