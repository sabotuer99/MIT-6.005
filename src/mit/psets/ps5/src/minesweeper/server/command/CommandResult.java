package minesweeper.server.command;

public interface CommandResult {

	boolean keepPlaying();
	String getResponse();
}
