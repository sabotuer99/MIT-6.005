package minesweeper.server.command.factory;

import lib6005.parser.ParseTree;
import minesweeper.BoardSize;
import minesweeper.server.command.Command;
import minesweeper.server.command.DeflagCommand;
import minesweeper.server.command.DigCommand;
import minesweeper.server.grammar.CommandGrammar;

public class DeflagCommandFactory implements CommandFactory {

	@Override
	public Command buildCommand(ParseTree<CommandGrammar> tree) {
		BoardSize coords = XYExtractor.extract(tree);
		return new DeflagCommand(coords.getX(), coords.getY());
	}

}
