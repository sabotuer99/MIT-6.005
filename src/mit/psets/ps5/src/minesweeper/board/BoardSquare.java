package minesweeper.board;

import minesweeper.board.events.SquareEvent;
import minesweeper.board.events.SquareEventHandler;

public interface BoardSquare {

	BoardSquare flag();
	BoardSquare deflag();
	BoardSquare dig();
	boolean isBomb();
	void addListener(Class<? extends SquareEvent> eventType, SquareEventHandler handler);
	void removeListener(Class<? extends SquareEvent> eventType, SquareEventHandler handler);
	public SquareEventHandler getBoomHandler();
	public SquareEventHandler getRevealHandler();
	void removeAllListeners();
	void removeAllListenersForEventType(Class<? extends SquareEvent> eventType);
}
