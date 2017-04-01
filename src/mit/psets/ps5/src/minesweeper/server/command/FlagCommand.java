package minesweeper.server.command;

import minesweeper.board.Board;

public class FlagCommand implements Command {

	private final int x;
	private final int y;

	public FlagCommand(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public CommandResult exectute(Board board) {
		board.flag(x, y);
		return new CommandResultImpl(board.toString(), true);
	}

}
