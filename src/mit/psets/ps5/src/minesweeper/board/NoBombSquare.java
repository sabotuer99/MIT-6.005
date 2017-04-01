package minesweeper.board;

import minesweeper.board.events.RevealEvent;
import minesweeper.board.events.SquareEventHandler;

public class NoBombSquare extends EventfulAbstractBoardSquare{

	//this flag is necessary to avoid stack overflow when reveal event is propogated.
	private boolean hidden = true;
	
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

		if(hidden){
			hidden = false;
			for(SquareEventHandler handler : handlerMap.get(RevealEvent.class)){
				handler.handle(new RevealEvent(rev));
			}
		}

		
		return rev;
	}

	@Override
	public boolean isBomb() {
		return false;
	}


}
