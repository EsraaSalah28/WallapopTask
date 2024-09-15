import Enums.Directions;
import Enums.Instructions;
import Models.MapSpace;
import Models.Rover;
import Models.RoverPosition;

import java.util.Arrays;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        System.out.println("Insert horizontal map size:");
        int sizex = reader.nextInt();
        System.out.println("Insert vertical map size:");
        int sizey = reader.nextInt();
        MapSpace mapSpace = new MapSpace(sizex, sizey);

        System.out.println("Insert horizontal initial rover position:");
        int roverx = reader.nextInt();
        System.out.println("Insert vertical initial rover position:");
        int rovery = reader.nextInt();
        RoverPosition roverPosition = new RoverPosition(roverx, rovery);
        System.out.println("Insert initial rover direction:");
        String roverz = reader.next(); //n = north, e = east, w = west, s = south

        Rover rover = new Rover(roverPosition, Directions.fromString(roverz.trim()), mapSpace);
        rover.locateRover(mapSpace, roverPosition, Directions.fromString(roverz.trim()));

        do {
            System.out.println("Insert command (f = forward, b = backward, l = turn left, r = turn right):");
            String command = reader.next();
            rover.doInstructions(Instructions.fromString(command.trim()));
            String s = Arrays.stream(Directions.values()).findFirst().toString();
            RoverPosition position = rover.getRoverPosition();
            System.out.println(String.format("Rover is at x:%d y:%d facing:%s",position.getRoverX() , position.getRoverY(), s));
        } while (true);

    }

}
