package minesweeper.server.command.factory;

import lib6005.parser.ParseTree;
import minesweeper.server.command.Command;
import minesweeper.server.command.LookCommand;
import minesweeper.server.grammar.CommandGrammar;

public class LookCommandFactory implements CommandFactory {

	@Override
	public Command buildCommand(ParseTree<CommandGrammar> tree) {
		// TODO Auto-generated method stub
		return new LookCommand();
	}

}
