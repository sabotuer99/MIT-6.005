package minesweeper.board;

import java.util.ArrayList;
import java.util.List;

import minesweeper.board.events.RevealEvent;
import minesweeper.board.events.SquareEvent;
import minesweeper.board.events.SquareEventHandler;

public abstract class AbstractBoardSquare implements BoardSquare {

	protected List<BoardSquare> neighbors;
	
	public List<BoardSquare> getNeighbors() {
		return neighbors;
	}

	public void setNeighbors(List<BoardSquare> neighbors) {
		this.neighbors = new ArrayList<>(neighbors);
	}

	@Override
	public String toString() {
		return "X";
	}
	
	private final SquareEventHandler bHandler = new SquareEventHandler(){

		@Override
		public void handle(SquareEvent event) {
			// No-op
		}};
		
	public SquareEventHandler getBoomHandler() {
		return bHandler;
	}
	
	private final SquareEventHandler rHandler = new SquareEventHandler(){

		@Override
		public void handle(SquareEvent event) {
			if(event instanceof RevealEvent){
				RevealEvent e = (RevealEvent)event;
				if(isBomb()){
					e.incBombCount();
				}
			}
		}};
	public SquareEventHandler getRevealHandler() {
		return rHandler;
	}
	
	
}
