package minesweeper.server.command.parser;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;

import lib6005.parser.ParseTree;
import lib6005.parser.UnableToParseException;
import minesweeper.server.grammar.CommandGrammar;

public interface CommandParser {
	
	ParseTree<CommandGrammar> parse(File input) throws UnableToParseException;
	ParseTree<CommandGrammar> parse(String input) throws UnableToParseException;
	ParseTree<CommandGrammar> parse(Reader input) throws UnableToParseException;
	ParseTree<CommandGrammar> parse(InputStream input) throws UnableToParseException;
	
}
