package minesweeper.server.command;

public class CommandResultImpl implements CommandResult {

	private final boolean keepPlaying;
	private final String response;

	public CommandResultImpl(String response, boolean keepPlaying){
		this.response = response;
		this.keepPlaying = keepPlaying;
	}
	
	@Override
	public boolean keepPlaying() {
		return keepPlaying;
	}

	@Override
	public String getResponse() {
		return response;
	}

}
