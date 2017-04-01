package minesweeper.board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import minesweeper.board.events.SquareEvent;
import minesweeper.board.events.SquareEventHandler;

public abstract class EventfulAbstractBoardSquare extends AbstractBoardSquare {


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
	
	
	private List<SquareEventHandler> safeGet(Class<? extends SquareEvent> eventType) {
		List<SquareEventHandler> handlers = handlerMap.containsKey(eventType) ?
				handlerMap.get(eventType) :
				new ArrayList<>();
		return handlers;
	}

}
