package minesweeper.board;

import minesweeper.board.events.BoomEvent;
import minesweeper.board.events.SquareEvent;
import minesweeper.board.events.SquareEventHandler;

public class RevealedSquare extends EventfulAbstractBoardSquare{

	private int count = 0;
	
	private SquareEventHandler boomHandler = new SquareEventHandler(){
		@Override
		public void handle(SquareEvent event) {
			if(event instanceof BoomEvent){
				count--;
			}
		}};

	public RevealedSquare(){
	}
	
	@Override
	public BoardSquare flag() {
		return this;
	}

	@Override
	public BoardSquare deflag() {
		return this;
	}

	@Override
	public BoardSquare dig() {
		return this;
	}
	
	@Override
	public String toString(){
		return count == 0 ? " " : Integer.toString(count);
	}

	@Override
	public boolean isBomb() {
		return false;
	}

	public SquareEventHandler getBoomHandler() {
		return boomHandler;
	}

	public void incBombCount() {
		count++;
	}
}
