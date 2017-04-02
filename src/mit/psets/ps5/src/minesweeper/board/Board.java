/* Copyright (c) 2007-2017 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package minesweeper.board;

import java.util.ArrayList;
import java.util.List;

import minesweeper.board.events.BoomEvent;
import minesweeper.board.events.RevealEvent;
import minesweeper.board.events.SquareEvent;
import minesweeper.board.events.SquareEventHandler;

/**
 * TODO: Specification
 */
public class Board {
    
    // TODO: Abstraction function, rep invariant, rep exposure, thread safety
    
    // TODO: Specify, test, and implement in problem 2
    
	private BoardSquare[][] squares;
	private int rows;
	private int cols; 
	
	public Board(int[][] initialState){
		
		rows = initialState.length;
		cols = rows > 0 ? initialState[0].length : 0;
		
		squares = init(rows, cols);
		addUnknownBombs(initialState, squares);
		addInitialEventListeners(squares);
	}

	public int getX() {
		return cols;
	}

	public int getY() {
		return rows;
	}
	
	public synchronized void flag(int x, int y){
		int row = y + 1;
		int col = x + 1;
		squares[row][col] = squares[row][col].flag();
	}
	
	public synchronized void deflag(int x, int y){
		int row = y + 1;
		int col = x + 1;
		squares[row][col] = squares[row][col].deflag();
	}
	
	public synchronized boolean dig(int x, int y){
		
		int row = y + 1;
		int col = x + 1;
		
		boolean isPreBomb = squares[row][col].isBomb();
		squares[row][col] = squares[row][col].dig();
		boolean isPostBomb = squares[row][col].isBomb();
		
		//if square was a bomb before, and isn't one now, it exploded
		if(isPreBomb && !isPostBomb){
			
			for(SquareEventHandler handler : revealedSquareBoomHandlers){
				handler.handle(new BoomEvent());
			}
			
			return false;
		} else {
			return true;
		}
	}
	

	
	private final List<SquareEventHandler> revealedSquareBoomHandlers = new ArrayList<>();
	private SquareEventHandler getRevealedBoomPropagationHandler(BoardSquare[][] board, 
			BoardSquare square, final int r, final int c) {
		return new SquareEventHandler(){
			@Override
			public void handle(SquareEvent event) {
				if(event instanceof BoomEvent){	
					
					//System.out.println("Called boom propagation handler");
					
					//only propagate if bomb count is zero
					if(countNeighboringBombs(r, c, board) == 0){
						for(int i = -1; i <= 1; i++){
							for(int j = -1; j <= 1; j++){
								if(!(i == 0 && j == 0)){
									board[r+i][c+j] = board[r+i][c+j].dig();
								}
							}
						}
					}
				}
			}

			
		};
	}
	
	private void addInitialEventListeners(BoardSquare[][] board) {
		//for each square on the active game board, add the reveal event handlers
		for(int row = 1; row < rows + 1; row++){
			for(int col = 1; col < cols + 1; col++){
				
				BoardSquare square = board[row][col];
				final int r = row;
				final int c = col;
				
				square.addListener(RevealEvent.class, getDigPropagationRevealHandler(board, square, r, c));
				
				//add default bomb counting handlers for all neighbors
				addBombCountingHandlersToNeighbors(square, row, col, board);
			}
		}//end big square loop... gross!!!!	
	}

	private SquareEventHandler getDigPropagationRevealHandler(BoardSquare[][] board, BoardSquare square, final int r,
			final int c) {
		return new SquareEventHandler(){
			@Override
			public void handle(SquareEvent event) {
				if(event instanceof RevealEvent){	

					//square will be replaced, so kill all the listeners
					square.removeAllListeners();
					
					//only propagate if bomb count is zero
					if(countNeighboringBombs(r, c, board) == 0){
						digNeighbors(square, r, c, board);
					}
					
					//add boom listeners to neighboring bombs
					addBoomListenerToBombNeighbors(r, c, event, board);
				}
			}

			
		};
	}

	private int countNeighboringBombs(final int r, final int c, BoardSquare[][] board) {
		int bombCount = 0;
		for(int i = -1; i <= 1; i++){
			for(int j = -1; j <= 1; j++){
				if(!(i == 0 && j == 0)){
					bombCount += board[r + i][c + j].isBomb() ? 1 : 0;
				}
			}
		}
		return bombCount;
	}
	
	private void addBoomListenerToBombNeighbors(final int r, final int c, SquareEvent event, BoardSquare[][] board) {
		for(int i = -1; i <= 1; i++){
			for(int j = -1; j <= 1; j++){
				if(!(i == 0 && j == 0) && board[r + i][c + j].isBomb()){
					board[r + i][c + j].addListener(BoomEvent.class, ((RevealEvent)event).getSquare().getBoomHandler());
				}
			}
		}
	}
	
	private void digNeighbors(BoardSquare square, final int r, final int c, BoardSquare[][] board) {
		
		for(int i = -1; i <= 1; i++){
			for(int j = -1; j <= 1; j++){
				if(!(i == 0 && j == 0)){
					board[r+i][c+j].removeListener(RevealEvent.class, square.getRevealHandler());
					
					BoardSquare before = board[r+i][c+j];
					
					board[r+i][c+j] = board[r+i][c+j].dig();
					
					BoardSquare after = board[r+i][c+j];
					
					//if digging the square change it, it was "revealed" and we
					//need to add the boom listener so it will update any time 
					//a bomb explodes
					if(before != after){
						revealedSquareBoomHandlers.add(
								getRevealedBoomPropagationHandler(board, board[r+i][c+j], r+i, c+j));
					}
				}
			}
		}
	}
	
	@Override
	public synchronized String toString() {
		
		StringBuilder sb = new StringBuilder();
		
		for(int row = 1; row < rows + 1; row++){
			for(int col = 1; col < cols + 1; col++){
				
				sb.append(squares[row][col]).append(" ");
				
			}
			sb.setLength(sb.length() - 1); //trim trailing space
			sb.append(String.format("%n"));
		}
		
		return sb.toString();
	}
	
	
	public synchronized String toDebugString() {
		
		StringBuilder sb = new StringBuilder();
		
		String header1 = "      ";
		String header2 = "      ";
		String header3 = "      ";
		String underline = "     ";
		
		for(int col = 1; col < cols + 1; col++){
			int huns = col / 100;
			int tens = (col - (huns*100))/10;
			int ones = col % 10;
			
			
			header1 += (huns == 0 ? " " : huns) + " ";
			header2 += (tens == 0 ? " " : tens)  + " ";
			header3 += ones  + " ";
			underline += "--";
		}
		
		sb.append(header1).append(String.format("%n"));
		sb.append(header2).append(String.format("%n"));
		sb.append(header3).append(String.format("%n"));
		sb.append(underline).append(String.format("%n"));
		
		for(int row = 1; row < rows + 1; row++){
			for(int col = 0; col < cols + 1; col++){
				if(col == 0){
					sb.append(String.format("% 4d", row)).append("| ");
				} else {
					sb.append(squares[row][col].isBomb() ? 1 : 0).append(" ");
				}
			}
			sb.setLength(sb.length() - 1); //trim trailing space
			sb.append(String.format("%n"));
		}
		
		return sb.toString();
	}
	
	private void addBombCountingHandlersToNeighbors(BoardSquare square, final int r, final int c, BoardSquare[][] board) {
		for(int i = -1; i <= 1; i++){
			for(int j = -1; j <= 1; j++){
				if(!(i == 0 && j == 0)){
					square.addListener(RevealEvent.class, board[r + i][c + j].getRevealHandler());
				}
			}
		}
	}

	private void addUnknownBombs(int[][] initialState, BoardSquare[][] board) {
		for(int row = 0; row < rows; row++){
			for(int col = 0; col < cols; col++){
				
				BoardSquare base = initialState[row][col] == 0 ? 
						new NoBombSquare() :
						new BombSquare();

				board[row+1][col+1] = new UnknownSquare(base);
			}
		}
	}

	private BoardSquare[][] init(final int rows, final int cols) {
		BoardSquare[][] board = new BoardSquare[rows + 2][cols + 2];
		for(int row = 0; row < rows + 2; row++){
			for(int col = 0; col < cols + 2; col++){
				board[row][col] = new NullBoardSquare();
			}
		}
		return board;
	}

	
}
