package minesweeper.server.command;

import minesweeper.board.Board;

public class ByeCommand implements Command {

	@Override
	public CommandResult exectute(Board board) {
		return new CommandResultImpl("", false);
	}

}
