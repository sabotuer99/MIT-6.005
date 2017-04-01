package minesweeper.board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	
	
}
