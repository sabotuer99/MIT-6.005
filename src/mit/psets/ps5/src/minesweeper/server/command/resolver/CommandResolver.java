package minesweeper.server.command.resolver;

import lib6005.parser.ParseTree;
import minesweeper.server.command.Command;
import minesweeper.server.grammar.CommandGrammar;

public interface CommandResolver {

	Command resolve(ParseTree<CommandGrammar> tree);
}

