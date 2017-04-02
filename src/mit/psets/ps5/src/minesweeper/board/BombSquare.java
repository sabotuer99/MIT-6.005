package minesweeper.board;

import java.util.List;

import minesweeper.board.events.BoomEvent;
import minesweeper.board.events.RevealEvent;
import minesweeper.board.events.SquareEventHandler;

public class BombSquare extends AbstractBoardSquare {

	//after it explodes it's not a bomb anymore
	private boolean isBomb = true;
	
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
		
		isBomb = false;
		
		//fire boom event on all handlers
		//then remove this nodes listener
		List<SquareEventHandler> b = handlerMap.get(BoomEvent.class);
		if(b != null){
			for(int i = b.size()-1; i >=0; i--){
				SquareEventHandler bh = b.get(i);
				bh.handle(new BoomEvent());
				b.remove(bh);
			}
		}

		RevealedSquare rev = new RevealedSquare();

		//fire reveal event on all handlers
		List<SquareEventHandler> h = handlerMap.get(RevealEvent.class);
		if(h != null){
			for(int i = h.size()-1; i >=0; i--){
				h.get(i).handle(new RevealEvent(rev));
			}
		}
		
		return rev;
	}

	@Override
	public boolean isBomb() {
		return isBomb;
	}
	
}
