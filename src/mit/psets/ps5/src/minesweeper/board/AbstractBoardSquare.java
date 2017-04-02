package minesweeper.board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	protected final Map<Class<? extends SquareEvent>, List<SquareEventHandler>> handlerMap = new HashMap<>();

	@Override
	public void addListener(Class<? extends SquareEvent> eventType, SquareEventHandler handler) {

		List<SquareEventHandler> handlers = safeGet(eventType);
		
		handlers.add(handler);
		handlerMap.put(eventType, handlers);
	}
	
	@Override
	public void removeListener(Class<? extends SquareEvent> eventType, SquareEventHandler handler) {

		List<SquareEventHandler> handlers = safeGet(eventType);
		
		handlers.remove(handler);
		handlerMap.put(eventType, handlers);
	}
	
	@Override
	public void removeAllListeners() {
		for(List<SquareEventHandler> h : handlerMap.values()){
			h.clear();
		}
		handlerMap.clear();
	}
	
	@Override
	public void removeAllListenersForEventType(Class<? extends SquareEvent> eventType) {
		List<SquareEventHandler> h = handlerMap.get(eventType);
		if(h != null){
			h.clear();
		}
		handlerMap.remove(eventType);
	}
	
	
	private List<SquareEventHandler> safeGet(Class<? extends SquareEvent> eventType) {
		List<SquareEventHandler> handlers = handlerMap.containsKey(eventType) ?
				handlerMap.get(eventType) :
				new ArrayList<>();
		return handlers;
	}
	
}
