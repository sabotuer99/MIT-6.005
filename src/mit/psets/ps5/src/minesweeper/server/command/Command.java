package minesweeper.server.command;

import java.net.Socket;

import minesweeper.board.Board;

public interface Command {

	CommandResult exectute(Board board);
	
}
