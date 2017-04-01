package minesweeper.board;

import minesweeper.board.events.BoomEvent;
import minesweeper.board.events.RevealEvent;
import minesweeper.board.events.SquareEventHandler;

public class BombSquare extends EventfulAbstractBoardSquare {

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
		
		//fire boom event on all handlers
		for(SquareEventHandler handler : handlerMap.get(BoomEvent.class)){
			handler.handle(new BoomEvent());
		}

		RevealedSquare rev = new RevealedSquare();

		for(SquareEventHandler handler : handlerMap.get(RevealEvent.class)){
			handler.handle(new RevealEvent(rev));
		}
		
		return rev;
	}

	@Override
	public boolean isBomb() {
		return true;
	}
	
}
