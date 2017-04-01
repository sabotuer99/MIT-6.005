package minesweeper.server.command.factory;

import lib6005.parser.ParseTree;
import minesweeper.server.command.Command;
import minesweeper.server.command.FlagCommand;
import minesweeper.server.grammar.CommandGrammar;

public class FlagCommandFactory implements CommandFactory {

	@Override
	public Command buildCommand(ParseTree<CommandGrammar> tree) {
		
		return new FlagCommand();
	}

}
