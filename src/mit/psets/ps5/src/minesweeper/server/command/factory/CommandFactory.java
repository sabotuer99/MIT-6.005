package minesweeper.server.command.factory;

import lib6005.parser.ParseTree;
import minesweeper.server.command.Command;
import minesweeper.server.grammar.CommandGrammar;

public interface CommandFactory {

	Command buildCommand(ParseTree<CommandGrammar> tree);
	
}
