package tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import model.ClearCellGame;
import model.BoardCell;
import model.Game;

import java.util.Random;

/* Execute tests in sorted order */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class StudentTests {
    private ClearCellGame game;
    private Random random;

    @Before
    public void setUp() {
        random = new Random(42); // Fixed seed for predictable results
        game = new ClearCellGame(8, 8, random, 1);
    }

    @Test
    public void test1_BoardInitialization() {
        // Ensure the board starts completely empty
        for (int i = 0; i < game.getMaxRows(); i++) {
            for (int j = 0; j < game.getMaxCols(); j++) {
                assertEquals(BoardCell.EMPTY, game.getBoardCell(i, j));
            }
        }
    }

    @Test
    public void test2_IsGameOver() {
        // Initially, game should NOT be over
        assertFalse(game.isGameOver());

        // Fill the last row with non-empty cells
        for (int j = 0; j < game.getMaxCols(); j++) {
            game.setBoardCell(game.getMaxRows() - 1, j, BoardCell.RED);
        }

        // Now game should be over
        assertTrue(game.isGameOver());
    }

    @Test
    public void test3_NextAnimationStep() {
        // Ensure a new row is added when last row is empty
        game.nextAnimationStep();

        // First row should now contain random colors
        for (int j = 0; j < game.getMaxCols(); j++) {
            assertNotEquals(BoardCell.EMPTY, game.getBoardCell(0, j));
        }
    }

    @Test
    public void test4_ProcessCell_ClearsCorrectly() {
        // Set up a row of yellow cells
        game.setRowWithColor(1, BoardCell.YELLOW);
        game.setBoardCell(1, 7, BoardCell.RED); // Keep last cell different

        game.processCell(1, 0); // Click on yellow cell

        // Entire yellow row should be cleared
        for (int j = 0; j < 7; j++) {
            assertEquals(BoardCell.EMPTY, game.getBoardCell(1, j));
        }

        // Red cell should remain
        assertEquals(BoardCell.RED, game.getBoardCell(1, 7));
    }

    @Test
    public void test5_ProcessCell_CollapsesRows() {
        // Set up a row that will be cleared
        game.setRowWithColor(1, BoardCell.YELLOW);
        game.setRowWithColor(3, BoardCell.GREEN);

        game.processCell(1, 0); // Remove yellow cells

        // The green row should move up to row 1
        for (int j = 0; j < game.getMaxCols(); j++) {
        	System.out.println( game.getBoardCell(1, j));
            assertEquals(BoardCell.GREEN, game.getBoardCell(1, j));
        }
    }

    @Test
    public void test6_ProcessCell_ClearsDiagonals() {
        game.setBoardCell(3, 3, BoardCell.BLUE);
        game.setBoardCell(2, 2, BoardCell.BLUE);
        game.setBoardCell(4, 4, BoardCell.BLUE);

        game.processCell(3, 3); // Click on center blue cell

        // Check diagonals cleared
        assertEquals(BoardCell.EMPTY, game.getBoardCell(3, 3));
        assertEquals(BoardCell.EMPTY, game.getBoardCell(2, 2));
        assertEquals(BoardCell.EMPTY, game.getBoardCell(4, 4));
    }

    @Test
    public void test7_ProcessCell_NoActionOnEmptyCell() {
        game.processCell(5, 5); // Click on an empty cell

        // Ensure board remains unchanged
        for (int i = 0; i < game.getMaxRows(); i++) {
            for (int j = 0; j < game.getMaxCols(); j++) {
                assertEquals(BoardCell.EMPTY, game.getBoardCell(i, j));
            }
        }
    }

    @Test
    public void test8_ProcessCell_DoesNotModifyGameOverBoard() {
        // Fill last row to trigger game over
        for (int j = 0; j < game.getMaxCols(); j++) {
            game.setBoardCell(game.getMaxRows() - 1, j, BoardCell.RED);
        }

        game.processCell(3, 3); // Try processing when game is over

        // Board should remain unchanged
        for (int j = 0; j < game.getMaxCols(); j++) {
            assertEquals(BoardCell.RED, game.getBoardCell(game.getMaxRows() - 1, j));
        }
    }

    @Test
    public void test9_SetRowWithColor() {
        game.setRowWithColor(2, BoardCell.BLUE);
        
        for (int j = 0; j < game.getMaxCols(); j++) {
            assertEquals(BoardCell.BLUE, game.getBoardCell(2, j));
        }
    }

    @Test
    public void test10_SetColWithColor() {
        game.setColWithColor(4, BoardCell.GREEN);
        
        for (int i = 0; i < game.getMaxRows(); i++) {
            assertEquals(BoardCell.GREEN, game.getBoardCell(i, 4));
        }
    }

    @Test
    public void test11_SetBoardWithColor() {
        game.setBoardWithColor(BoardCell.RED);
        
        for (int i = 0; i < game.getMaxRows(); i++) {
            for (int j = 0; j < game.getMaxCols(); j++) {
                assertEquals(BoardCell.RED, game.getBoardCell(i, j));
            }
        }
    }
}
