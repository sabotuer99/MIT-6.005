package minesweeper.server.grammar;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import lib6005.parser.GrammarCompiler;
import lib6005.parser.ParseTree;
import lib6005.parser.Parser;
import lib6005.parser.UnableToParseException;

public class CommandGrammarParseTreeTests {

	@Test
	public void Parser_LookParses() throws UnableToParseException, IOException{
		Parser<CommandGrammar> parser = getParser();
			
		String input = "look\n";
		ParseTree<CommandGrammar> tree = parser.parse(input);
		
		assertEquals(1, tree.childrenByName(CommandGrammar.LOOK).size());
	}
	
	@Test
	public void Parser_HandlesWindowsLineEndings() throws UnableToParseException, IOException{
		Parser<CommandGrammar> parser = getParser();
			
		String input = "look\r\n";
		ParseTree<CommandGrammar> tree = parser.parse(input);
		
		assertEquals(CommandGrammar.MESSAGE, tree.getName());
	}
	
	@Test
	public void Parser_HelpReqParses() throws UnableToParseException, IOException{
		Parser<CommandGrammar> parser = getParser();
			
		String input = "help\n";
		ParseTree<CommandGrammar> tree = parser.parse(input);
		
		assertEquals(1, tree.childrenByName(CommandGrammar.HELP_REQ).size());
	}
	
	@Test
	public void Parser_DigParses() throws UnableToParseException, IOException{
		Parser<CommandGrammar> parser = getParser();
		
		String input = "dig 100 200\n";
		ParseTree<CommandGrammar> tree = parser.parse(input);
		
		assertEquals(1, tree.childrenByName(CommandGrammar.DIG).size());
	}
	
	@Test
	public void Parser_FlagParses() throws UnableToParseException, IOException{
		Parser<CommandGrammar> parser = getParser();
		
		String input = "flag 100 200\n";
		ParseTree<CommandGrammar> tree = parser.parse(input);
		
		assertEquals(1, tree.childrenByName(CommandGrammar.FLAG).size());
	}

	@Test
	public void Parser_DeFlagParses() throws UnableToParseException, IOException{
		Parser<CommandGrammar> parser = getParser();
		
		String input = "deflag 100 200\n";
		ParseTree<CommandGrammar> tree = parser.parse(input);
		
		assertEquals(1, tree.childrenByName(CommandGrammar.DEFLAG).size());
	}
	
	@Test
	public void Parser_ByeParses() throws UnableToParseException, IOException{
		Parser<CommandGrammar> parser = getParser();
		
		String input = "bye\n";
		ParseTree<CommandGrammar> tree = parser.parse(input);
		
		assertEquals(1, tree.childrenByName(CommandGrammar.BYE).size());
	}
	
	
	
	
	/*
	 * 
	 *  	TEST HELPERS
	 * 
	 * 
	 * 
	 * 
	 */
	
	private Parser<CommandGrammar> getParser() throws UnableToParseException, IOException {
		Parser<CommandGrammar> parser;
		String path = "src/" + CommandGrammar.class.getPackage().getName().replaceAll("\\.","/");
		
		parser = GrammarCompiler.compile(new File(path, "CommandGrammar.g"), CommandGrammar.MESSAGE);
		return parser;
	}
	
	
}
