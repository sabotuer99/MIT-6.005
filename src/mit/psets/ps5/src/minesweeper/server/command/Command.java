package minesweeper.server.command;

import minesweeper.board.Board;

public interface Command {

	CommandResult exectute(Board board);
	
}
