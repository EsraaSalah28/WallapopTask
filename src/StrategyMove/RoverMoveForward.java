package StrategyMove;


import Enums.Directions;
import Models.MapSpace;
import Models.Rover;
import Models.RoverPosition;
import utils.RoverHelper;

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
        RoverPosition currentPosition = rover.getRoverPosition();
        Directions direction = rover.getDirection();
        int roverY = currentPosition.getRoverY();
        int roverX = currentPosition.getRoverX();

        RoverPosition newPosition = switch (direction) {
            case NORTH -> new RoverPosition(roverX, roverY + 1);
            case SOUTH -> new RoverPosition(roverX, roverY - 1);
            case EAST -> new RoverPosition(roverX + 1, roverY);
            case WEST -> new RoverPosition(roverX - 1, roverY);
        };
        roverHelper.validatePosition(newPosition,mapSpace);

        if (!newPosition.isOnMap(rover.getMapSpace())) {
            throw new RuntimeException("Rover is out of bounds!");
        }

        rover.setRoverPosition(newPosition);
    }
}
