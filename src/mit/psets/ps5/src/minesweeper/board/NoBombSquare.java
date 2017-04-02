package minesweeper.board;

import java.util.List;

import minesweeper.board.events.RevealEvent;
import minesweeper.board.events.SquareEventHandler;

public class NoBombSquare extends AbstractBoardSquare{

	
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

		List<SquareEventHandler> h = handlerMap.get(RevealEvent.class);
		for(int i = h.size()-1; i >=0; i--){
			h.get(i).handle(new RevealEvent(rev));
		}
		
		return rev;
	}

	@Override
	public boolean isBomb() {
		return false;
	}

}
