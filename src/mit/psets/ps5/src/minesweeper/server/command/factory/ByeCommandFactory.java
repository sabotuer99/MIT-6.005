package minesweeper.server.command.factory;

import lib6005.parser.ParseTree;
import minesweeper.server.command.ByeCommand;
import minesweeper.server.command.Command;
import minesweeper.server.grammar.CommandGrammar;

public class ByeCommandFactory implements CommandFactory{

	@Override
	public Command buildCommand(ParseTree<CommandGrammar> tree) {
		// TODO Auto-generated method stub
		return new ByeCommand();
	}

	
}
