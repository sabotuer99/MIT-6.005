package minesweeper.board;

import minesweeper.board.events.SquareEvent;
import minesweeper.board.events.SquareEventHandler;

public class FlaggedSquare extends AbstractBoardSquare {

	private BoardSquare base;

	public FlaggedSquare(BoardSquare base){
		this.base = base;
	}

	@Override
	public BoardSquare flag() {
		return this;
	}

	@Override
	public BoardSquare deflag() {
		return base;
	}

	@Override
	public BoardSquare dig() {
		return this;
	}
	
	@Override
	public String toString(){
		return "F";
	}
	
	@Override
	public boolean isBomb() {
		return base.isBomb();
	}

	@Override
	public void addListener(Class<? extends SquareEvent> eventType, SquareEventHandler handler) {
		base.addListener(eventType, handler);
	}

	@Override
	public void removeListener(Class<? extends SquareEvent> eventType, SquareEventHandler handler) {
		base.removeListener(eventType, handler);
	}
}
