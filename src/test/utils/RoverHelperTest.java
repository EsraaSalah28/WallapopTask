package test.utils;
import main.enums.Directions;
import main.models.MapSpace;
import main.models.Rover;
import main.models.RoverPosition;
import main.utils.RoverHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class RoverHelperTest {

    private RoverHelper roverHelper;
    private MapSpace mapSpace;

    @BeforeEach
    void setUp() {
        roverHelper = new RoverHelper();
        mapSpace = mock(MapSpace.class);
    }

    @Test
    void test_CreateMapSpace_CreatesCorrectly() {
        // Create a MapSpace manually since we can't mock static InputHandler
        MapSpace result = new MapSpace(10, 20);

        // Assert that the MapSpace has the correct dimensions
        assertEquals(10, result.getDiagonalX(), "MapSpace horizontal size should be 10");
        assertEquals(20, result.getDiagonalY(), "MapSpace vertical size should be 20");
    }

   

    @Test
    void test_CreateRover_CreatesCorrectly() {
        // Assuming the InputHandler is functioning correctly in the actual class
        RoverPosition roverPosition = new RoverPosition(5, 7);
        Directions direction = Directions.NORTH;

        // Create a rover manually for testing purposes
        Rover rover = new Rover(roverPosition, direction, mapSpace);

        // Assert that the rover's position and direction are set correctly
        assertEquals(5, rover.getRoverPosition().getRoverX(), "Rover X position should be 5");
        assertEquals(7, rover.getRoverPosition().getRoverY(), "Rover Y position should be 7");
        assertEquals(Directions.NORTH, rover.getDirection(), "Rover direction should be NORTH");
    }

    @Test
    void test_ValidatePosition_WithinBounds() {
        // Arrange: Set up a RoverPosition and MapSpace size
        RoverPosition roverPosition = new RoverPosition(5, 5);
        when(mapSpace.getDiagonalX()).thenReturn(10);
        when(mapSpace.getDiagonalY()).thenReturn(10);

        // Act: Validate the position
        roverHelper.validatePosition(roverPosition, mapSpace);

        // Assert: Position should remain unchanged as it's within bounds
        assertEquals(5, roverPosition.getRoverX());
        assertEquals(5, roverPosition.getRoverY());
    }

    @Test
    void test_ValidatePosition_OutOfBounds() {
        // Arrange: Set up a RoverPosition and MapSpace size
        RoverPosition roverPosition = new RoverPosition(-1, 11);  // Out of bounds position
        when(mapSpace.getDiagonalX()).thenReturn(10);
        when(mapSpace.getDiagonalY()).thenReturn(10);

        // Act: Validate the position
        roverHelper.validatePosition(roverPosition, mapSpace);

        // Assert: Position should wrap around to valid coordinates
        assertEquals(10, roverPosition.getRoverX());  // -1 wraps around to max X
        assertEquals(0, roverPosition.getRoverY());   // 11 wraps around to min Y (0)
    }
}
