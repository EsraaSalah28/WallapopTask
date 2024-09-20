package main.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoverPosition {
    int roverX;
    int roverY;


    public RoverPosition(int roverX, int roverY) {
        this.roverX = roverX;
        this.roverY = roverY;
    }

    @Override
    public String toString() {
        return "RoverPosition{" +
                "roverX=" + roverX +
                ", roverY=" + roverY +
                '}';
    }

    public boolean isEqualsTo(RoverPosition position) {
        return roverX == position.roverX && roverY == position.roverY;
    }


    public boolean isOnMap(MapSpace mapSpace) {
        if (roverX < 0 || roverX > mapSpace.getDiagonalX())
            return false;
        return roverY >= 0 && roverY <= mapSpace.getDiagonalY();
    }


}
