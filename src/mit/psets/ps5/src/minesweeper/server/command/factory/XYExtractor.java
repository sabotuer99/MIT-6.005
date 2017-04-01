package minesweeper.server.command.factory;

import lib6005.parser.ParseTree;
import minesweeper.server.command.Point;
import minesweeper.server.grammar.CommandGrammar;

public class XYExtractor {

	public static Point extract(ParseTree<CommandGrammar> tree){
		Point coords = new Point();
		
		int x = 0;
		for(ParseTree<CommandGrammar> X : tree.childrenByName(CommandGrammar.X)){
			x = Integer.parseInt(X.getContents());
		}
		
		int y = 0;
		for(ParseTree<CommandGrammar> Y : tree.childrenByName(CommandGrammar.Y)){
			y = Integer.parseInt(Y.getContents());
		}
		
		coords.setX(x);
		coords.setY(y);
		
		return coords;
	}
}
