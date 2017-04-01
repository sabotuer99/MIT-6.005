package minesweeper.server.command.factory;

import lib6005.parser.ParseTree;
import minesweeper.server.command.Command;
import minesweeper.server.command.HelpReqCommand;
import minesweeper.server.grammar.CommandGrammar;

public class HelpReqCommandFactory implements CommandFactory {

	@Override
	public Command buildCommand(ParseTree<CommandGrammar> tree) {
		
		return new HelpReqCommand("AVAILABLE COMMANDS:  "
				                + "look, "
				                + "dig x y, "
				                + "flag x y, "
				                + "deflag x y, "
				                + "help, "
				                + "bye");
	}

}
