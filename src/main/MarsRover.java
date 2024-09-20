package main;
import main.models.MapSpace;
import main.models.Rover;
import main.utils.InputHandler;
import main.utils.RoverHelper;


public class MarsRover {
    public static void main(String[] args) {
        RoverHelper roverHelper = new RoverHelper();
        InputHandler inputHandler = new InputHandler();
        MapSpace mapSpace = roverHelper.createMapSpace();
        Rover rover = roverHelper.createRover(mapSpace);
          inputHandler.processInstructions(rover);
    }
}
