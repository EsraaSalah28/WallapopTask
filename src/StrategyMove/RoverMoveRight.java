package StrategyMove;

import Enums.Directions;
import Models.Rover;

public class RoverMoveRight implements RoverMove{
    private Rover rover;

    public RoverMoveRight(Rover rover) {
        this.rover = rover;
    }

    @Override
    public void move() {
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
