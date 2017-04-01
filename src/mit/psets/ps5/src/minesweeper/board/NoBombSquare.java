package minesweeper.board;

import minesweeper.board.events.RevealEvent;
import minesweeper.board.events.SquareEventHandler;

public class NoBombSquare extends EventfulAbstractBoardSquare{


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
		RevealedSquare rev = new RevealedSquare();

		for(SquareEventHandler handler : handlerMap.get(RevealEvent.class)){
			handler.handle(new RevealEvent(rev));
		}
		
		return rev;
	}

	@Override
	public boolean isBomb() {
		return false;
	}


}
