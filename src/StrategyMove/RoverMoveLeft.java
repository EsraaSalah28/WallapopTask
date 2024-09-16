package StrategyMove;

import Enums.Directions;
import Models.MapSpace;
import Models.Rover;
import utils.RoverHelper;


public class RoverMoveLeft implements RoverMove {
    private final Rover rover;

    public RoverMoveLeft(Rover rover) {
        this.rover = rover;

    }

    @Override
    public void move() {
        Directions newDirection = switch (rover.getDirection()) {
            case NORTH -> Directions.WEST;
            case SOUTH -> Directions.EAST;
            case EAST -> Directions.NORTH;
            case WEST -> Directions.SOUTH;
        };

        // Update the rover's direction
        rover.setDirection(newDirection);
    }
}
