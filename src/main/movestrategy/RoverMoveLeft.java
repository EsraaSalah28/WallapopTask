package main.movestrategy;

import main.enums.Directions;
import main.exceptions.RoverPositionException;
import main.models.MapSpace;
import main.models.Rover;
import main.models.RoverPosition;


public class RoverMoveLeft implements RoverMove {
    private final Rover rover;
    private final MapSpace mapSpace;

    public RoverMoveLeft(Rover rover, MapSpace mapSpace) {
        this.rover = rover;
        this.mapSpace = mapSpace;
    }



    @Override
    public void move() {
        RoverPosition currentPosition = rover.getRoverPosition();
        if (mapSpace.isOccupied(currentPosition)) {
            System.out.println("Encountered an obstacle at position: " + currentPosition.getRoverX() + ", " + currentPosition.getRoverY());
            return;
        }
        if (!currentPosition.isOnMap(rover.getMapSpace())) {
            throw new RoverPositionException("Rover is out of bounds!");
        }
        Directions newDirection = switch (rover.getDirection()) {
            case NORTH -> Directions.WEST;
            case SOUTH -> Directions.EAST;
            case EAST -> Directions.NORTH;
            case WEST -> Directions.SOUTH;
        };
        rover.setDirection(newDirection);
    }
}
