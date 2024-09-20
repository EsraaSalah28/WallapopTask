package test.movestrategy;

import main.enums.Directions;
import main.exceptions.RoverPositionException;
import main.models.MapSpace;
import main.models.Rover;
import main.models.RoverPosition;
import main.movestrategy.RoverMoveBackward;
import main.movestrategy.RoverMoveForward;
import main.utils.RoverHelper;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class RoverMoveForwardTest {

    @Test
    void test_ValidMove_Forward_PositionIsCorrect() {
        // Arrange: Current position before the move is (2, 3)
        RoverPosition currentPosition = new RoverPosition(2, 3);
        RoverPosition expectedPosition = new RoverPosition(2, 4);  // Expected position after moving forward

        // Create the real Rover object
        Rover realRover = new Rover(currentPosition, Directions.NORTH, new HashMap<>(), new MapSpace(10, 10), null);
        Rover roverSpy = spy(realRover);  // Spy on the real Rover

        // Mock the getDirection method to ensure it returns NORTH
        doReturn(Directions.NORTH).when(roverSpy).getDirection();

        // Mock RoverHelper and MapSpace dependencies
        RoverHelper roverHelper = mock(RoverHelper.class);
        MapSpace mapSpace = mock(MapSpace.class);

        // Stub the validation and ensure the new position is not occupied
        doNothing().when(roverHelper).validatePosition(any(RoverPosition.class), eq(mapSpace));
        when(mapSpace.isOccupied(expectedPosition)).thenReturn(false);  // The expected position is not occupied

        // Create the move strategy with the spy
        RoverMoveForward roverMoveForward = new RoverMoveForward(roverSpy, roverHelper, mapSpace);


        roverMoveForward.move();

        // Assert: Verify the rover's position has been updated correctly
        assertEquals(expectedPosition.getRoverX(), roverSpy.getRoverPosition().getRoverX(), "The rover's X position  2 after moving forward.");
        assertEquals(expectedPosition.getRoverY(), roverSpy.getRoverPosition().getRoverY(), "The rover's Y position 4 after moving forward.");
    }

    @Test
    void test_ShouldNotMove_WhenPositionIsOccupied() {
        RoverPosition currentPosition = new RoverPosition(2, 3);
        RoverPosition occupiedPosition = new RoverPosition(2, 4);

        Rover realRover = new Rover(currentPosition, Directions.NORTH, new MapSpace(10, 10));
        Rover roverSpy = spy(realRover);

        RoverHelper roverHelper = mock(RoverHelper.class);
        MapSpace mapSpace = spy(new MapSpace(10, 10));
        Rover occupiedRover = new Rover(occupiedPosition, Directions.NORTH, new HashMap<>(), new MapSpace(10, 10), null);
        mapSpace.addRover(occupiedRover);

        doNothing().when(roverHelper).validatePosition(any(RoverPosition.class), eq(mapSpace));

        RoverMoveForward roverMoveForward = new RoverMoveForward(roverSpy, roverHelper, mapSpace);

        roverMoveForward.move();

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

        RoverMoveForward rover = new RoverMoveForward(roverSpy, roverHelper, mapSpace);

        Exception exception = assertThrows(RoverPositionException.class, () -> {
            rover.move();
        });

        // Verify the exception message
        assertEquals("Rover is out of bounds!", exception.getMessage());
    }
}


