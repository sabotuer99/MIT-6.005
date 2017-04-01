package minesweeper.server.command.resolver;

import java.util.HashMap;
import java.util.Map;

import lib6005.parser.ParseTree;
import minesweeper.server.command.Command;
import minesweeper.server.command.factory.ByeCommandFactory;
import minesweeper.server.command.factory.CommandFactory;
import minesweeper.server.command.factory.DeflagCommandFactory;
import minesweeper.server.command.factory.DigCommandFactory;
import minesweeper.server.command.factory.FlagCommandFactory;
import minesweeper.server.command.factory.HelpReqCommandFactory;
import minesweeper.server.command.factory.LookCommandFactory;
import minesweeper.server.grammar.CommandGrammar;

public class CommandResolverImpl implements CommandResolver {

	Map<CommandGrammar, CommandFactory> map = new HashMap<>();
	{
		map.put(CommandGrammar.BYE,      new ByeCommandFactory());
		map.put(CommandGrammar.DEFLAG,   new DeflagCommandFactory());
		map.put(CommandGrammar.DIG,      new DigCommandFactory());
		map.put(CommandGrammar.FLAG,     new FlagCommandFactory());
		map.put(CommandGrammar.HELP_REQ, new HelpReqCommandFactory());
		map.put(CommandGrammar.LOOK,     new LookCommandFactory());
	}

	@Override
	public Command resolve(ParseTree<CommandGrammar> tree) {

		//Check if tree is a valid command
		//this should always be "MESSAGE", but best to be safe...
		if(inMap(tree)){
			return map.get(tree.getName()).buildCommand(tree);
		}
		
		//Otherwise, check if one of the children is a valid command
		for(ParseTree<CommandGrammar> sub : tree.children()){
			if(inMap(sub)){
				return map.get(sub.getName()).buildCommand(sub);
			}
		}
		
		
		return null;
	}

	private boolean inMap(ParseTree<CommandGrammar> tree) {
		return map.get(tree.getName()) != null;
	}
	
}
