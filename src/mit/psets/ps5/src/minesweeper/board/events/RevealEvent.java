package minesweeper.board.events;

import minesweeper.board.RevealedSquare;

public class RevealEvent implements SquareEvent{

	private RevealedSquare revealed;

	public RevealEvent(RevealedSquare revealed){
		this.revealed = revealed;
	}
	
	public void incBombCount(){
		revealed.incBombCount();
	}
	
	public RevealedSquare getSquare(){
		return revealed;
	}
	
}
