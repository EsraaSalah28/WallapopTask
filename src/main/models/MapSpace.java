package main.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Builder
public class MapSpace {
    private int diagonalX;
    private int diagonalY;
    private final List<Rover> roverList = new ArrayList<>();

    public MapSpace(int diagonalX, int diagonalY) {
        this.diagonalX = diagonalX;
        this.diagonalY = diagonalY;
    }

    public void addRover(Rover rover) {
        roverList.add(rover);
    }

    public boolean isOccupied(RoverPosition roverPosition) {
        return roverList.stream().anyMatch(r -> r.hasPosition(roverPosition));
    }

}
