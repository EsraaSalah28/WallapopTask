package main.movestrategy;


import main.enums.Directions;

import main.exceptions.RoverPositionException;
import main.models.MapSpace;
import main.models.Rover;
import main.models.RoverPosition;
import main.utils.RoverHelper;

public class RoverMoveForward implements RoverMove {
   private final Rover rover;
    private final RoverHelper roverHelper;
    private final MapSpace mapSpace;

    public RoverMoveForward(Rover rover, RoverHelper roverHelper, MapSpace mapSpace) {
        this.rover = rover;
        this.roverHelper = (roverHelper != null) ? roverHelper : new RoverHelper();
        this.mapSpace=mapSpace;
    }

    @Override
    public void move() {
        RoverPosition newPosition = getRoverPosition();
        roverHelper.validatePosition(newPosition,mapSpace);
        if (mapSpace.isOccupied(newPosition)) {
            System.out.println("Encountered an obstacle at position: " + newPosition.getRoverX() + ", " + newPosition.getRoverY());
            return;
        }
        if (!newPosition.isOnMap(rover.getMapSpace())) {
            throw new RoverPositionException("Rover is out of bounds!");
        }

        rover.setRoverPosition(newPosition);
    }

    private RoverPosition getRoverPosition() {
        RoverPosition currentPosition = rover.getRoverPosition();
        Directions direction = rover.getDirection();
        int roverY = currentPosition.getRoverY();
        int roverX = currentPosition.getRoverX();

        return switch (direction) {
            case NORTH -> new RoverPosition(roverX, roverY + 1);
            case SOUTH -> new RoverPosition(roverX, roverY - 1);
            case EAST -> new RoverPosition(roverX + 1, roverY);
            case WEST -> new RoverPosition(roverX - 1, roverY);
        };
    }
}
