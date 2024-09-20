package main.utils;

import main.enums.Directions;
import main.exceptions.RoverPositionException;
import main.models.MapSpace;
import main.models.Rover;
import main.models.RoverPosition;

import java.util.Scanner;

import static main.utils.InputHandler.getInput;

public class RoverHelper {
    private static final Scanner scanner = new Scanner(System.in);


    public MapSpace createMapSpace() {
        Integer sizeX = getInput("Insert horizontal map size:", Integer.class);
        Integer sizeY = getInput("Insert vertical map size:", Integer.class);
        MapSpace mapSpace = new MapSpace(sizeX, sizeY);
        createObstacles(mapSpace);
        return mapSpace;
    }

    public void createObstacles(MapSpace mapSpace) {
        // Ask if the user wants to add obstacles
        if (shouldAddObstacles()) {
            boolean addMoreObstacles = true;
            while (addMoreObstacles) {
                int obstacleX = getInput("Insert obstacle X position:", Integer.class);
                int obstacleY = getInput("Insert obstacle Y position:", Integer.class);
                RoverPosition obstaclePosition = new RoverPosition(obstacleX, obstacleY);
                if (!obstaclePosition.isOnMap(mapSpace)) {
                    throw new RoverPositionException("Obstacle is out of bounds!");
                }
                mapSpace.addRover(Rover.builder().roverPosition(obstaclePosition).build());

                addMoreObstacles = shouldContinueAddingObstacles();
            }
        }
    }

    private boolean shouldAddObstacles() {
        System.out.println("Would you like to add obstacles to the map? (y) if no enter any key:");
        String answer = scanner.next();
        return answer.equalsIgnoreCase("y");
    }

    private boolean shouldContinueAddingObstacles() {
        System.out.println("Do you want to add another obstacle? (y) if no enter any key:");
        String answer = scanner.next();
        return answer.equalsIgnoreCase("y");
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

    public void validatePosition(RoverPosition newPosition, MapSpace mapSpace) {
        int newX = newPosition.getRoverX();
        int newY = newPosition.getRoverY();
        int spaceX = mapSpace.getDiagonalX();
        int spaceY = mapSpace.getDiagonalY();

        if (newX < 0) {
            newX = spaceX;
        } else if (newX > spaceX) {
            newX = 0;
        }
        if (newY < 0) {
            newY = spaceY;
        } else if (newY > spaceY) {
            newY = 0;
        }
        newPosition.setRoverX(newX);
        newPosition.setRoverY(newY);
    }


}
