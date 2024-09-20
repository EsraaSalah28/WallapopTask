package test.movestrategy;

import main.enums.Directions;
import main.exceptions.RoverPositionException;
import main.models.MapSpace;
import main.models.Rover;
import main.models.RoverPosition;
import main.movestrategy.RoverMoveBackward;
import main.movestrategy.RoverMoveRight;
import main.utils.RoverHelper;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class RoverMoveRightTest {

    @Test
    void test_ValidMove_Right_PositionIsCorrect() {
        RoverPosition currentPosition = new RoverPosition(2, 2);
        Directions expectedDir = Directions.EAST;
        Directions currentDir = Directions.NORTH;
        Rover realRover = new Rover(currentPosition, currentDir, new HashMap<>(), new MapSpace(10, 10), null);
        Rover roverSpy = spy(realRover);

        RoverPosition roverPosition = roverSpy.getRoverPosition();

        MapSpace mapSpace = mock(MapSpace.class);

        when(mapSpace.isOccupied(roverPosition)).thenReturn(false);

        RoverMoveRight roverMoveRight = new RoverMoveRight(roverSpy, mapSpace);

        roverMoveRight.move();
        assertEquals(expectedDir,roverSpy.getDirection());
    }

    @Test
    void test_ShouldNotMove_WhenPositionIsOccupied() {
        RoverPosition currentPosition = new RoverPosition(2, 3);
        RoverPosition occupiedPosition = new RoverPosition(2, 4);

        Rover realRover = new Rover(currentPosition, Directions.NORTH, new MapSpace(10, 10));
        Rover roverSpy = spy(realRover);

        MapSpace mapSpace = spy(new MapSpace(10, 10));
        Rover occupiedRover = new Rover(occupiedPosition, Directions.NORTH, new HashMap<>(), new MapSpace(10, 10), null);
        mapSpace.addRover(occupiedRover);


        RoverMoveRight rover = new RoverMoveRight(roverSpy,  mapSpace);

        rover.move();

        assertEquals(currentPosition, roverSpy.getRoverPosition(), "The rover's position should not change when the target position is occupied.");
    }

    @Test
    void test_ShouldThrowException_WhenPositionIsInvalid() {
        RoverPosition invalidPosition = new RoverPosition(-1, -1);

        Rover realRover = new Rover(invalidPosition, Directions.NORTH, new HashMap<>(), new MapSpace(10, 10),null);
        Rover roverSpy = spy(realRover);  // Spy on the real Rover

        RoverHelper roverHelper = mock(RoverHelper.class);
        MapSpace mapSpace = mock(MapSpace.class);

        doNothing().when(roverHelper).validatePosition(any(RoverPosition.class), eq(mapSpace));

        RoverMoveBackward roverMoveBackward = new RoverMoveBackward(roverSpy, roverHelper, mapSpace);

        Exception exception = assertThrows(RoverPositionException.class, () -> {
            roverMoveBackward.move();
        });

        // Verify the exception message
        assertEquals("Rover is out of bounds!", exception.getMessage());
    }
}


