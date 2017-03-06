package mit.exercises;
import java.io.File;

import lib6005.parser.GrammarCompiler;
import lib6005.parser.ParseTree;
import lib6005.parser.Parser;

public class ParseExample {
	enum IntegerGrammar {ROOT, SUM, PRIMITIVE, NUMBER, WHITESPACE};
	
	public static void main(String[] args) throws Exception{
		Parser<IntegerGrammar> parser =
			     GrammarCompiler.compile(new File(getPath(), "IntegerExpression.g"), IntegerGrammar.ROOT); //ParseExample.class.getResourceAsStream(
		
		ParseTree<IntegerGrammar> tree = parser.parse("5+2+3+21");
		
		System.out.println(tree.toString());
		
		tree.display();
	}
	
	private static String getPath(){
		return "src/" + ParseExample.class.getPackage().getName().replaceAll("\\.","/");
	}
}
