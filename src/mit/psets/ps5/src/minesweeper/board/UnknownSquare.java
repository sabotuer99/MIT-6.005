package minesweeper.board;

import minesweeper.board.events.SquareEvent;
import minesweeper.board.events.SquareEventHandler;

public class UnknownSquare extends AbstractBoardSquare {

	private BoardSquare base;

	public UnknownSquare(BoardSquare base){
		this.base = base;
	}
	
	@Override
	public BoardSquare flag() {
		return new FlaggedSquare(this);
	}

	@Override
	public BoardSquare deflag() {
		return this;
	}

	@Override
	public BoardSquare dig() {
		return base.dig();
	}

	@Override
	public String toString(){
		return "-";
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
	
	@Override
	public void removeAllListeners() {
		base.removeAllListeners();
	}
}
