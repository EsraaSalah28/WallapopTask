package Enums;

public enum Directions {
    NORTH('n'),
    SOUTH('s'),
    EAST('e'),
    WEST('w');

    private final char value;

    // Constructor to initialize the value
    Directions(char value) {
        this.value = value;
    }


    // Getter to access the actual value
    public char getValue() {
        return value;
    }

    public static Directions fromString(String directionStr) {
        if (directionStr == null || directionStr.length() != 1) {
            throw new IllegalArgumentException("Invalid input: " + directionStr);
        }

        char directionChar = Character.toLowerCase(directionStr.charAt(0));

        for (Directions direction : Directions.values()) {
            if (direction.getValue() == directionChar) {
                return direction;
            }
        }

        // Throw an exception if the input does not match any enum values
        throw new IllegalArgumentException("Invalid direction: " + directionStr);
    }}
