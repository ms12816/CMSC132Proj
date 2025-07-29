package model;

import java.util.Random;

public class ClearCellGame extends Game {
	private int strategy;
	Random random;
	private int score = 0;

	/**
	 * Defines a board with empty cells. It relies on the super class constructor to
	 * define the board. The random parameter is used for the generation of random
	 * cells. The strategy parameter defines which clearing cell strategy to use
	 * (for this project it will be 1). For fun, you can add your own strategy by
	 * using a value different that one.
	 * 
	 * @param maxRows
	 * @param maxCols
	 * @param random
	 * @param strategy
	 */
	public ClearCellGame(int maxRows, int maxCols, Random random, int strategy) {
		super(maxRows, maxCols);
		this.strategy = strategy;
		this.random = random;
		
	}

	
	 //The game is over when the last board row is different from empty row.
	 
	public boolean isGameOver() {
		for(int i = 0; i < maxCols; i++) {
			if(board[maxRows - 1][i] != BoardCell.EMPTY) {
				return true;
			}
		}
		return false;
	}

	public int getScore() {
		return score;
	}

	/**
	 * This method will attempt to insert a row of random BoardCell objects if the
	 * last board row (row with index board.length -1) corresponds to the empty row;
	 * otherwise no operation will take place.
	 */
	public void nextAnimationStep() {
		boolean empty = true;
		for(int i = 0; i < maxCols; i++) {
			if(board[maxRows - 1][i] != BoardCell.EMPTY) {
				empty = false;
			}
		}
		
		if(!empty) return;
		
	    for (int i = maxRows - 1; i > 0; i--) {
	        for (int j = 0; j < maxCols; j++) {
	            board[i][j] = board[i - 1][j]; // ✅ Now i-1 is always valid
	        }
	    }

	    // Insert a new random row at the top
	    for (int i = 0; i < maxCols; i++) {
	        board[0][i] = BoardCell.getNonEmptyRandomBoardCell(random);
	    }
	}

	/**
	 * This method will turn to BoardCell.EMPTY the cell selected and any adjacent
	 * surrounding cells in the vertical, horizontal, and diagonal directions that
	 * have the same color. The clearing of adjacent cells will continue as long as
	 * cells have a color that corresponds to the selected cell. Notice that the
	 * clearing process does not clear every single cell that surrounds a cell
	 * selected (only those found in the vertical, horizontal or diagonal
	 * directions).
	 * 
	 * IMPORTANT: Clearing a cell adds one point to the game's score.<br />
	 * <br />
	 * 
	 * If after processing cells, any rows in the board are empty,those rows will
	 * collapse, moving non-empty rows upward. For example, if we have the following
	 * board (an * represents an empty cell):<br />
	 * <br />
	 * RRR<br />
	 * GGG<br />
	 * YYY<br />
	 * * * *<br/>
	 * <br />
	 * then processing each cell of the second row will generate the following
	 * board<br />
	 * <br />
	 * RRR<br />
	 * YYY<br />
	 * * * *<br/>
	 * * * *<br/>
	 * <br />
	 * IMPORTANT: If the game has ended no action will take place.
	 * 
	 * 
	 */
	public void processCell(int rowIndex, int colIndex) {
	    // If game over (and indices valid and using strategy 1), do nothing.
	    if (isGameOver() && rowIndex >= 0 && colIndex >= 0 && strategy == 1) return;
	    
	    BoardCell target = board[rowIndex][colIndex];
	    if (target == BoardCell.EMPTY) return;
	    
	    // Define the eight possible directions:
	    // Up, Down, Left, Right, and the four Diagonals.
	    //Array to easily traverse options
	    int[][] directions = {
	        {-1,  0},  // Up
	        { 1,  0},  // Down
	        { 0, -1},  // Left
	        { 0,  1},  // Right
	        {-1, -1},  // Up-Left
	        {-1,  1},  // Up-Right
	        { 1, -1},  // Down-Left
	        { 1,  1}   // Down-Right
	    };
	    
	    // For each direction, clear contiguous cells matching target.
	    for (int[] d : directions) {
	        clearInDirection(rowIndex, colIndex, d[0], d[1], target);
	    }
	    
	    // Finally, clear the originally selected cell (if it hasn't already been cleared)
	    if (board[rowIndex][colIndex] != BoardCell.EMPTY) {
	        board[rowIndex][colIndex] = BoardCell.EMPTY;
	        score++;
	    }
	    
	    // Collapse rows after clearing.
	    collapseRows();
	}

	//Clears cells recursively 
	private void clearInDirection(int row, int col, int dr, int dc, BoardCell target) {
	    int newRow = row + dr;
	    int newCol = col + dc;
	    // Check bounds.
	    if (newRow < 0 || newRow >= maxRows || newCol < 0 || newCol >= maxCols) return;
	    // Stop if the cell does not match the target.
	    if (board[newRow][newCol] != target) return;
	    
	    // Clear the matching cell and increment score.
	    board[newRow][newCol] = BoardCell.EMPTY;
	    score++;
	    
	    // Continue clearing in the same direction.
	    clearInDirection(newRow, newCol, dr, dc, target);
	}

	private void collapseRows() {
		for(int row = maxRows - 1; row > 0; row--) {
			boolean isRowEmpty = true;
			for(int col = 0; col < maxCols; col++) {
				if(board[row][col] != BoardCell.EMPTY) {
					isRowEmpty = false;
					break;
				}
			}
			//Move all rows up if the row is empty 
			if(isRowEmpty) {
				for(int r = row; r < maxRows - 1; r++) {
					for(int c = 0; c < maxCols; c++) {
						board[r][c] = board[r+1][c];
					}
				}
			}
		}
	}

}