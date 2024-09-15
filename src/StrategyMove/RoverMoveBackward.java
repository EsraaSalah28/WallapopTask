package StrategyMove;

import Enums.Directions;
import Models.Rover;
import Models.RoverPosition;

public class RoverMoveBackward implements RoverMove {
    private Rover rover;

    public RoverMoveBackward(Rover rover) {
        this.rover = rover;
    }

    @Override
    public void move() {
        RoverPosition currentPosition = rover.getRoverPosition();
        Directions direction = rover.getDirection();

        RoverPosition newPosition = switch (direction) {
            case NORTH -> new RoverPosition(currentPosition.getRoverX(), currentPosition.getRoverY() - 1);
            case SOUTH -> new RoverPosition(currentPosition.getRoverX(), currentPosition.getRoverY() + 1);
            case EAST -> new RoverPosition(currentPosition.getRoverX() - 1, currentPosition.getRoverY());
            case WEST -> new RoverPosition(currentPosition.getRoverX() + 1, currentPosition.getRoverY());
        };

        if (!newPosition.isOnMap(rover.getMapSpace())) {
            throw new RuntimeException("Rover is out of bounds!");
        }

        rover.setRoverPosition(newPosition);
    }
}

