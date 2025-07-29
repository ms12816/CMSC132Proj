package model;

import java.awt.Color;

/**
 * This class represents the logic of a game where a board is updated on each
 * step of the game animation. The board can also be updated by selecting a
 * board cell.
 * 
 * @author Department of Computer Science, UMCP
 */

public abstract class Game {
	protected BoardCell[][] board;
	
	int maxRows;
	int maxCols;

	/**
	 * Defines a board with BoardCell.EMPTY cells.
	 * 
	 * @param maxRows
	 * @param maxCols
	 */
	public Game(int maxRows, int maxCols) {
		this.maxRows = maxRows;
		this.maxCols = maxCols;
		this.board = new BoardCell[maxRows][maxCols];
		
		for(int i = 0; i < maxRows; i++) {
			for(int j = 0; j < maxCols; j++) {
				board[i][j] = BoardCell.EMPTY;
			}
		}
	}

	public int getMaxRows() {
		return maxRows;
	}

	public int getMaxCols() {
		return maxCols;
	}

	public void setBoardCell(int rowIndex, int colIndex, BoardCell boardCell) {
		if(rowIndex < 0 || colIndex < 0 || boardCell == null) {
			throw new IllegalArgumentException("Invalid parameters");
		}
		this.board[rowIndex][colIndex] = boardCell;
	}

	public BoardCell getBoardCell(int rowIndex, int colIndex) {
		return board[rowIndex][colIndex];
	}

	/**
	 * Initializes row with the specified color.
	 * 
	 * @param rowIndex
	 * @param cell
	 */
	public void setRowWithColor(int rowIndex, BoardCell cell) {
		if(rowIndex < 0 || rowIndex > maxRows) {
			throw new IllegalArgumentException("Invalid rowIndex");
		}
		
		for(int i = 0; i < maxCols; i++) {
			board[rowIndex][i] = cell;
		}
	}

	/**
	 * Initializes column with the specified color.
	 * 
	 * @param colIndex
	 * @param cell
	 */
	public void setColWithColor(int colIndex, BoardCell cell) {
		if(colIndex < 0 || colIndex > maxCols) {
			throw new IllegalArgumentException("Invalid colIndex");
		}
		
		for(int i = 0; i < maxRows; i++) {
			board[i][colIndex] = cell;
		}
	}

	/**
	 * Initializes the board with the specified color.
	 * 
	 * @param cell
	 */
	public void setBoardWithColor(BoardCell cell) {
		for(int i = 0; i < maxRows; i++) {
			for(int j = 0; j < maxCols; j++) {
				board[i][j] = cell;
			}
		}
	}

	public abstract boolean isGameOver();

	public abstract int getScore();

	/**
	 * Advances the animation one step.
	 */
	public abstract void nextAnimationStep();

	/**
	 * Adjust the board state according to the current board state and the selected
	 * cell.
	 * 
	 * @param rowIndex
	 * @param colIndex
	 */
	public abstract void processCell(int rowIndex, int colIndex);
}