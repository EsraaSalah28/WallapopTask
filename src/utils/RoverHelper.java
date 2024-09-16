package utils;

import Enums.Directions;
import Enums.Instructions;
import Models.MapSpace;
import Models.Rover;
import Models.RoverPosition;

import java.util.Scanner;

public class RoverHelper {
    private static final Scanner scanner = new Scanner(System.in);

    public static <T> T getInput(String msg, Class<T> type) {
        System.out.println(msg);
        Object res = switch (type.getSimpleName()) {
            case "Integer" -> scanner.nextInt();
            case "String" -> scanner.next();
            case "Boolean" -> scanner.nextBoolean();
            default -> throw new IllegalArgumentException("Unsupported input type: " + type.getName());
        };
        return type.cast(res);
    }

    public MapSpace createMapSpace() {
        Integer sizeX = getInput("Insert horizontal map size:", Integer.class);
        Integer sizeY = getInput("Insert vertical map size:", Integer.class);
        return new MapSpace(sizeX, sizeY);
    }


    private RoverPosition getRoverPosition() {
        int roverX = getInput("Insert horizontal initial rover position:", Integer.class);
        int roverY = getInput("Insert vertical initial rover position:", Integer.class);
        return new RoverPosition(roverX, roverY);
    }

    private Directions getRoverDirection() {
        String roverZ = getInput("Insert initial rover direction (n = north, e = east, w = west, s = south):", String.class);
        return Directions.fromString(roverZ.trim());
    }

    public Rover createRover(MapSpace mapSpace) {
        RoverPosition roverPosition = getRoverPosition();
        Directions direction = getRoverDirection();
        Rover rover = new Rover(roverPosition, direction, mapSpace);
        rover.locateRover(mapSpace, roverPosition, direction);
        return rover;
    }

    public void processInstructions(Rover rover) {
        boolean continueInstructions = true;
        while (continueInstructions) {
            executeCommand(rover);
            displayRoverStatus(rover);
            continueInstructions = shouldContinue();
        }
    }

    private void executeCommand(Rover rover) {
        System.out.println("Insert command (f = forward, b = backward, l = turn left, r = turn right):");
        String command = scanner.next();
        rover.doInstructions(Instructions.fromString(command.trim()));
    }

    private void displayRoverStatus(Rover rover) {
        RoverPosition position = rover.getRoverPosition();
        Directions direction = rover.getDirection();
        System.out.printf("Rover is at x: %d, y: %d, facing: %s%n", position.getRoverX(), position.getRoverY(), direction);
    }

    private boolean shouldContinue() {
        System.out.println("Do you want to continue? Insert 'y' for Yes:");
        String answer = scanner.next();
        return answer.equalsIgnoreCase("y");
    }

    public void validatePosition( RoverPosition newPosition,MapSpace mapSpace) {
        int newX = newPosition.getRoverX();
        int newY = newPosition.getRoverY();
        int spaceX = mapSpace.getDiagonalX();
        int spaceY = mapSpace.getDiagonalY();

        // Wrap around horizontally
        if (newX < 0) {
            newX = spaceX; // Moving off the left edge, appear on the right
        } else if (newX > spaceX) {
            newX = 0; // Moving off the right edge, appear on the left
        }

        // Wrap around vertically
        if (newY < 0) {
            newY = mapSpace.getDiagonalY() ; // Moving off the top edge, appear at the bottom
        } else if (newY > mapSpace.getDiagonalY()) {
            newY = 0; // Moving off the bottom edge, appear at the top
        }

        newPosition.setRoverX(newX);
        newPosition.setRoverY(newY);
    }


}
