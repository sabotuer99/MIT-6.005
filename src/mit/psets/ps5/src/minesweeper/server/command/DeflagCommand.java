package minesweeper.server.command;

import minesweeper.board.Board;

public class DeflagCommand implements Command {

	private final int x;
	private final int y;
	
	public DeflagCommand(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public CommandResult exectute(Board board) {
		board.deflag(x, y);
		return new CommandResultImpl(board.toString(), true);
	}

}
