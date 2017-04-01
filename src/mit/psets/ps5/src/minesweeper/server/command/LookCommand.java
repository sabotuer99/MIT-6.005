package minesweeper.server.command;

import minesweeper.board.Board;

public class LookCommand implements Command {

	@Override
	public CommandResult exectute(Board board) {
		
		return new CommandResultImpl(board.toString(), true);
	}

}
