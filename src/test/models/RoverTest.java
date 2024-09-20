package test.models;

import main.enums.Directions;
import main.enums.Instructions;
import main.exceptions.RoverPositionException;
import main.models.MapSpace;
import main.models.Rover;
import main.models.RoverPosition;
import main.movestrategy.RoverMove;
import main.movestrategy.RoverMoveForward;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import main.utils.RoverHelper;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoverTest {
    private Rover rover;
    private MapSpace mapSpace;
    private RoverPosition roverPosition;
    private RoverHelper roverHelper;

    @BeforeEach
    void setUp() {
        // Mock dependencies
        mapSpace = mock(MapSpace.class);
        roverPosition = mock(RoverPosition.class);
        roverHelper = mock(RoverHelper.class);

        // Initialize a basic rover instance
        rover = new Rover(roverPosition, Directions.NORTH, mapSpace);
    }

    @Test
    void testLocateRover() {
        when(roverPosition.isOnMap(any(MapSpace.class))).thenReturn(true);
        when(mapSpace.isOccupied(any(RoverPosition.class))).thenReturn(false);

        Rover newRover = rover.locateRover(mapSpace, roverPosition, Directions.NORTH);

        assertNotNull(newRover);
        assertEquals(Directions.NORTH, newRover.getDirection());
        assertTrue(newRover.hasPosition(roverPosition));
    }

    @Test
    void testLocateRover_InvalidPosition() {
        when(roverPosition.isOnMap(any(MapSpace.class))).thenReturn(false);

        assertThrows(RoverPositionException.class, () -> rover.locateRover(mapSpace, roverPosition, Directions.NORTH));
    }

    @Test
    void testDoInstructions_ValidMove() {

        RoverMove mockMove = mock(RoverMoveForward.class);
        Map<Instructions, RoverMove> moveStrategies = new HashMap<>();
        moveStrategies.put(Instructions.FORWARD, mockMove);
        rover.setMoveStrategies(moveStrategies);


        rover.doInstructions(Instructions.FORWARD);


        verify(mockMove, times(1)).move();
    }

    @Test
    void testDoInstructions_NullMoveStrategies() {

        rover.setMoveStrategies(null);


        assertThrows(RuntimeException.class, () -> rover.doInstructions(Instructions.FORWARD));
    }

    @Test
    void testHasPosition() {
        when(roverPosition.isEqualsTo(any(RoverPosition.class))).thenReturn(true);

        assertTrue(rover.hasPosition(roverPosition));
    }

    @Test
    void testValidateRoverPosition_NullPosition() {
        RoverPosition nullPosition = null;
        assertThrows(RoverPositionException.class, () -> rover.locateRover(mapSpace, nullPosition, Directions.NORTH));
    }
}
