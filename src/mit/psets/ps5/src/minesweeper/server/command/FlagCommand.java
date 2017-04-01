package minesweeper.server.command;

public class FlagCommand implements Command {

	private final int x;
	private final int y;

	public FlagCommand(int x, int y) {
		this.x = x;
		this.y = y;
	}

}
