package main.movestrategy;

import main.enums.Directions;
import main.exceptions.RoverPositionException;
import main.models.MapSpace;
import main.models.Rover;
import main.models.RoverPosition;


public class RoverMoveRight implements RoverMove{
    private final Rover rover;
    private final MapSpace mapSpace;

    public RoverMoveRight(Rover rover, MapSpace mapSpace) {
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
            case NORTH -> Directions.EAST;
            case SOUTH -> Directions.WEST;
            case EAST -> Directions.SOUTH;
            case WEST -> Directions.NORTH;
        };

        // Update the rover's direction
        rover.setDirection(newDirection);
    }
}
