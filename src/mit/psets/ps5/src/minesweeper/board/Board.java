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
		
		squares = new BoardSquare[rows + 2][cols + 2];
		
		for(int row = 1; row < rows + 2; row++){
			for(int col = 1; col < cols + 2; col++){
				
				BoardSquare base = initialState[row][col] == 0 ? 
						new NoBombSquare() :
						new BombSquare();

				squares[row][col] = new UnknownSquare(base);
			}
		}
		
		//for each square on the active game board, add the reveal event handlers
		for(int row = 1; row < rows + 2; row++){
			for(int col = 1; col < cols + 2; col++){
				
				BoardSquare square = squares[row][col];
				final int r = row;
				final int c = col;
				
				square.addListener(RevealEvent.class, new SquareEventHandler(){
					@Override
					public void handle(SquareEvent event) {
						if(event instanceof RevealEvent){							
							//propogate event by calling dig on NoBomb squares
							BoardSquare[] n = new BoardSquare[4];
							n[0] = squares[r-1][c];
							n[1] = squares[r+1][c];
							n[2] = squares[r][c+1];
							n[3] = squares[r][c-1];
							for(BoardSquare neighbor : n){
								if(!neighbor.isBomb()){
									neighbor.removeListener(RevealEvent.class, square.getRevealHandler());
									neighbor.dig();
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
	
	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		
		for(int row = 1; row < rows + 2; row++){
			for(int col = 1; col < cols + 2; col++){
				
				sb.append(squares[row][col]).append(" ");
				
			}
			sb.append(String.format("%n"));
		}
		
		return sb.toString();
	}
	
	public void dig(int x, int y){
		int row = y;
		int col = x;
		squares[row][col] = squares[row][col].dig();
	}
	
	public void flag(int x, int y){
		int row = y;
		int col = x;
		squares[row][col] = squares[row][col].flag();
	}
	
	public void deflag(int x, int y){
		int row = y;
		int col = x;
		squares[row][col] = squares[row][col].deflag();
	}
	
}
