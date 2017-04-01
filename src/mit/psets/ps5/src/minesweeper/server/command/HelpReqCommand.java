package minesweeper.server.command;

import minesweeper.board.Board;

public class HelpReqCommand implements Command {

	private String helpMessage;

	public HelpReqCommand(String helpMessage) {
		this.helpMessage = helpMessage.replaceAll("[\r\n]", "") + "\n";
	}
	
	@Override
	public CommandResult exectute(Board board) {
		return new CommandResultImpl(helpMessage, true);
	}

}
