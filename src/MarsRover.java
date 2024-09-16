import Models.MapSpace;
import Models.Rover;
import utils.RoverHelper;


public class MarsRover {
    public static void main(String[] args) {
        RoverHelper roverHelper = new RoverHelper();
        MapSpace mapSpace = roverHelper.createMapSpace();
        Rover rover = roverHelper.createRover(mapSpace);
        roverHelper.processInstructions(rover);
    }
}
