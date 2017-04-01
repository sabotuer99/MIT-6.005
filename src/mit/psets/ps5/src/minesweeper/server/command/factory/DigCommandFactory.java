package minesweeper.server.command.factory;

import lib6005.parser.ParseTree;
import minesweeper.server.command.Command;
import minesweeper.server.command.DigCommand;
import minesweeper.server.command.Point;
import minesweeper.server.grammar.CommandGrammar;

public class DigCommandFactory implements CommandFactory {

	@Override
	public Command buildCommand(ParseTree<CommandGrammar> tree) {
		Point coords = XYExtractor.extract(tree);
		return new DigCommand(coords.getX(), coords.getY());
	}

}
