package main.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import main.enums.Directions;
import main.enums.Instructions;
import main.exceptions.RoverPositionException;
import main.movestrategy.*;
import main.utils.RoverHelper;

import java.util.HashMap;
import java.util.Map;

@Builder
@Setter
@Getter
public class Rover {
    private RoverPosition roverPosition;
    private Directions direction;
    private Map<Instructions, RoverMove> moveStrategies ;
    private MapSpace mapSpace;
    private RoverHelper roverHelper;

    public Rover(RoverPosition roverPosition, Directions direction, Map<Instructions, RoverMove> moveStrategies, MapSpace mapSpace, RoverHelper roverHelper) {
        this.roverPosition = roverPosition;
        this.direction = direction;
        this.moveStrategies = moveStrategies;
        this.mapSpace = mapSpace;
        this.roverHelper = roverHelper;
    }

    public Rover(RoverPosition roverPosition, Directions direction, MapSpace mapSpace) {
        this(roverPosition, direction, new HashMap<>(), mapSpace, new RoverHelper()); // Chaining the main constructor
        // Initialize moveStrategies
        this.moveStrategies.put(Instructions.FORWARD, new RoverMoveForward(this, roverHelper, mapSpace));
        this.moveStrategies.put(Instructions.BACKWARD, new RoverMoveBackward(this, roverHelper, mapSpace));
        this.moveStrategies.put(Instructions.LEFT, new RoverMoveLeft(this, mapSpace));
        this.moveStrategies.put(Instructions.RIGHT, new RoverMoveRight(this, mapSpace));
    }

    private Rover createRover(MapSpace map, RoverPosition roverPosition, Directions direction) {
        return Rover.builder()
                .roverPosition(new RoverPosition(roverPosition.getRoverX(), roverPosition.getRoverY()))
                .direction(direction)
                .mapSpace(new MapSpace(map.getDiagonalX(), map.getDiagonalY()))
                .build();
    }


    public boolean hasPosition(RoverPosition roverPos) {
        return roverPosition.isEqualsTo(roverPos);
    }

    public Rover locateRover(MapSpace map, RoverPosition roverPosition, Directions direction) {
        validateRoverPosition(map, roverPosition);
        return createRover(map, roverPosition, direction);
    }

    private void validateRoverPosition(MapSpace map, RoverPosition roverPosition) {
        if (roverPosition == null) {
            throw new RoverPositionException("Rover position cannot be null");
        }
        if (!roverPosition.isOnMap(map)) {
            throw new RoverPositionException("Rover position is not on map");
        }
        if (map.isOccupied(roverPosition)) {
            throw new RoverPositionException("Rover position is not occupied");
        }
    }


    public void doInstructions(Instructions instruction) {
        if (moveStrategies == null)
            throw new RuntimeException();
        RoverMove strategy = moveStrategies.get(instruction);
        strategy.move();
    }

}
