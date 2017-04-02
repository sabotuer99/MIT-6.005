package minesweeper.server.command.parser;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import lib6005.parser.GrammarCompiler;
import lib6005.parser.ParseTree;
import lib6005.parser.Parser;
import lib6005.parser.UnableToParseException;
import minesweeper.server.grammar.CommandGrammar;

public class CommandParserImpl implements CommandParser {

	private Parser<CommandGrammar> getParser() throws UnableToParseException, IOException {
		Parser<CommandGrammar> parser;
		String path = "src/" + CommandGrammar.class.getPackage().getName().replaceAll("\\.","/");
		
		parser = GrammarCompiler.compile(new File(path, "CommandGrammar.g"), CommandGrammar.MESSAGE);
		return parser;
	}

	@Override
	public ParseTree<CommandGrammar> parse(File input) throws UnableToParseException {
		try {
			return getParser().parse(input);
		} catch (UnableToParseException | IOException e) {
			e.printStackTrace();
			throw new UnableToParseException(e.getMessage());
		}
	}

	@Override
	public ParseTree<CommandGrammar> parse(String input) throws UnableToParseException {
		try {
			return getParser().parse(input);
		} catch (UnableToParseException | IOException e) {
			e.printStackTrace();
			throw new UnableToParseException(e.getMessage());
		}
	}

	@Override
	public ParseTree<CommandGrammar> parse(Reader input) throws UnableToParseException {
		try {
			return getParser().parse(input);
		} catch (UnableToParseException | IOException e) {
			e.printStackTrace();
			throw new UnableToParseException(e.getMessage());
		}
	}

	@Override
	public ParseTree<CommandGrammar> parse(InputStream input) throws UnableToParseException {
		try {
			return getParser().parse(input);
		} catch (UnableToParseException | IOException e) {
			e.printStackTrace();
			throw new UnableToParseException(e.getMessage());
		}
	}
}
