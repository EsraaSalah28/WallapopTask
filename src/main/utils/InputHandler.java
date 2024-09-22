package main.utils;

import main.enums.Directions;
import main.enums.Instructions;
import main.exceptions.UnsupportedInputTypeException;
import main.models.Rover;
import main.models.RoverPosition;
import java.util.Scanner;



public class InputHandler {
    private static final Scanner scanner = new Scanner(System.in);
    public static <T> T getInput(String msg, Class<T> type) {
      System.out.print(msg);
        Object res = switch (type.getSimpleName()) {
            case "Integer" -> scanner.nextInt();
            case "String" -> scanner.next();
            default -> throw new UnsupportedInputTypeException("Unsupported input type: " + type.getName());
        };
        return type.cast(res);
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
        System.out.println("Do you want to continue? Insert 'y' for Yes if no enter any key:");
        String answer = scanner.next();
        return answer.equalsIgnoreCase("y");
    }


}
