package minesweeper.server.command;

import minesweeper.board.Board;

public class DigCommand implements Command {

	private final int x;
	private final int y;

	public DigCommand(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public CommandResult exectute(Board board) {
		boolean keepPlaying = board.dig(x, y);
		String response = keepPlaying ? board.toString() : "BOOM!\n";
		return new CommandResultImpl(response, keepPlaying);
	}
	
	

}
