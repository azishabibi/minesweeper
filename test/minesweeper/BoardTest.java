/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package minesweeper;

import org.junit.Test;

import static org.junit.Assert.*;
import org.junit.Before;
import minesweeper.Board;

public class BoardTest {

    private Board board;
    private final int width = 10;
    private final int height = 10;
    //private final int numBombs = 15;

    @Before
    public void setUp() {
        board = new Board(width, height);
    }

    @Test
    public void testBoardInitialization() {
        assertNotNull("Board should be initialized", board);
        // Assuming toString method correctly reflects the board's state,
        // this can be used for a basic sanity check
        System.out.println(board);
    }

    @Test
    public void testDigOnEmptySquare() {
        board.dig(0, 0);
        // Check the toString output or use a specific method to verify the square is
        // dug
        // This test might need to be adjusted based on the actual implementation of dig
        System.out.println(board);
        assertTrue("Square should be dug", true); // Replace this with actual verification
    }

    @Test
    public void testDigOnBombSquare() {
        // This requires setting a bomb at a known location, which might not be directly
        // supported
        // Might need to adjust the test strategy or access internal state in a way
        // that's not shown
    }

    @Test
    public void testFlagSquare() {
        board.flag(1, 1);
        // Verify the square is flagged, which might require accessing the square's
        // state
        System.out.println(board);
        assertTrue("Square should be flagged", true); // Replace this with actual verification
    }

    @Test
    public void testUnflagSquare() {
        board.flag(1, 1); // First flag
        board.flag(1, 1); // Then unflag
        // Verify the square is not flagged anymore
        System.out.println(board);
        assertTrue("Square should be unflagged", true); // Replace this with actual verification
    }

    // Additional tests might include:
    // - Verifying the correct number of bombs are placed
    // - Verifying adjacency counts are correct
    // - Testing edge cases for dig and flag methods
    // - Testing behavior when digging a square adjacent to several bombs
    // - Testing the game's end conditions (though this might require more methods)

    // Note: The assertTrue calls with true are placeholders and should be replaced
    // with actual checks against the Board's state or the expected outcomes.
}
