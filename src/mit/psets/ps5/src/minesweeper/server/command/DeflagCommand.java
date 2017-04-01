package minesweeper.server.command;

public class DeflagCommand implements Command {

	private final int x;
	private final int y;
	
	public DeflagCommand(int x, int y) {
		this.x = x;
		this.y = y;
	}

}
