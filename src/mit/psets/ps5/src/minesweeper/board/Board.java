/* Copyright (c) 2007-2017 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package minesweeper.board;

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
		
		for(int row = 0; row < rows; row++){
			for(int col = 0; col < cols; col++){
				
				BoardSquare base = initialState[row][col] == 0 ? 
						new NoBombSquare() :
						new BombSquare();

				squares[row+1][col+1] = new UnknownSquare(base);
			}
		}
		
		//for each square on the active game board, add the reveal event handlers
		for(int row = 1; row < rows + 1; row++){
			for(int col = 1; col < cols + 1; col++){
				
				BoardSquare square = squares[row][col];
				final int r = row;
				final int c = col;
				
				square.addListener(RevealEvent.class, new SquareEventHandler(){
					@Override
					public void handle(SquareEvent event) {
						if(event instanceof RevealEvent){	
							
							//only propogate if bombcount is zero
							int bombCount = 0;
							for(int i = -1; i <= 1; i++){
								for(int j = -1; j <= 1; j++){
									if(!(i == 0 && j == 0)){
										bombCount += squares[r + i][c + j].isBomb() ? 1 : 0;
									}
								}
							}
							
							square.removeAllListeners();
							
							if(bombCount == 0){
								if(!squares[r-1][c].isBomb()){
									squares[r-1][c].removeListener(RevealEvent.class, square.getRevealHandler());
									squares[r-1][c] = squares[r-1][c].dig();
								}
								if(!squares[r+1][c].isBomb()){
									squares[r+1][c].removeListener(RevealEvent.class, square.getRevealHandler());
									squares[r+1][c] = squares[r+1][c].dig();
								}
								if(!squares[r][c-1].isBomb()){
									squares[r][c-1].removeListener(RevealEvent.class, square.getRevealHandler());
									squares[r][c-1] = squares[r][c-1].dig();
								}
								if(!squares[r][c+1].isBomb()){
									squares[r][c+1].removeListener(RevealEvent.class, square.getRevealHandler());
									squares[r][c+1] = squares[r][c+1].dig();
								}
							}
						}
					}
				});
				
				//add default bomb counting handlers for all neighbors
				for(int i = -1; i <= 1; i++){
					for(int j = -1; j <= 1; j++){
						if(!(i == 0 && j == 0)){
							square.addListener(RevealEvent.class, squares[r + i][c + j].getRevealHandler());
						}
					}
				}
			}
		}//end big square loop... gross!!!!	
	}

	private BoardSquare[][] init(int rows, int cols) {
		BoardSquare[][] board = new BoardSquare[rows + 2][cols + 2];
		for(int row = 0; row < rows + 2; row++){
			for(int col = 0; col < cols + 2; col++){
				board[row][col] = new NullBoardSquare();
			}
		}
		return board;
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
	
	public synchronized void dig(int x, int y){
		int row = y + 1;
		int col = x + 1;
		squares[row][col] = squares[row][col].dig();
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
	
}
