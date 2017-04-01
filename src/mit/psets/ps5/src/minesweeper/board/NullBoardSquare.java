package minesweeper.board;

import minesweeper.board.events.SquareEvent;
import minesweeper.board.events.SquareEventHandler;

public class NullBoardSquare extends AbstractBoardSquare {

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
	public boolean isBomb() {
		return false;
	}

	@Override
	public void addListener(Class<? extends SquareEvent> eventType, SquareEventHandler handler) {
		
	}

	@Override
	public void removeListener(Class<? extends SquareEvent> eventType, SquareEventHandler handler) {
		
	}

	@Override
	public void removeAllListeners() {
	}

}
