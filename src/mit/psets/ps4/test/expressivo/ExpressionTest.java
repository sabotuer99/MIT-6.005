/* Copyright (c) 2015-2017 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package expressivo;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import expressivo.expression.Addition;
import expressivo.expression.Number;
import expressivo.expression.Variable;
import lib6005.parser.GrammarCompiler;
import lib6005.parser.ParseTree;
import lib6005.parser.Parser;
import lib6005.parser.UnableToParseException;

import org.junit.Test;

/**
 * Tests for the Expression abstract data type.
 */
public class ExpressionTest {

    // Testing strategy
    //   TODO
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    
    // TODO tests for Expression
    @Test
    public void Addition_TwoNumberSubExpressions_evaluateReturnsSum(){
    	Expression a = new Number(1);
    	Expression b = new Number(2);
    	Expression sut = new Addition(a, b);
    	
    	String result = sut.getEvaluator(null).evaluate();
    	
    	assertEquals("3.0", result);
    }
    
    @Test
    public void Addition_NumberAndVariable_evaluateReturnsSum(){
    	Expression a = new Number(1);
    	Expression b = new Variable("x");
    	Expression sut = new Addition(a, b);
    	
    	String result = sut.getEvaluator(null).evaluate();
    	
    	assertEquals("1.0+x", result);
    }
    
    @Test
    public void Addition_TwoVariable_evaluateReturnsSum(){
    	Expression b = new Variable("x");
    	Expression sut = new Addition(b, b);
    	
    	String result = sut.getEvaluator(null).evaluate();
    	
    	assertEquals("x+x", result);
    }
    
    @Test
    public void parseSanityCheck() throws Exception{
    	
    	Parser<ExpressionGrammar> parser =
			     GrammarCompiler.compile(new File(getPath(), 
			    		 "Expression.g"), ExpressionGrammar.ROOT); 
    	
    	ParseTree<ExpressionGrammar> tree = parser.parse("5+5*5");
    	
    	visitAll(tree, "  ");
    	
        tree = parser.parse("5*5+5");
    	
    	visitAll(tree, "  ");
    	
    	tree = parser.parse("5*(X+5)");
    	
    	visitAll(tree, "  ");
    }
    
	/**
	 * Traverse a parse tree, indenting to make it easier to read.
	 * @param node
	 * Parse tree to print.
	 * @param indent
	 * Indentation to use.
	 */
	private static void visitAll(ParseTree<ExpressionGrammar> node, String indent){
	    if(node.isTerminal()){
	        System.out.println(indent + node.getName() + ":" + node.getContents());
	    }else{
	        System.out.println(indent + node.getName());
	        for(ParseTree<ExpressionGrammar> child: node){
	            visitAll(child, indent + "   ");
	        }
	    }
	}
	
	private static String getPath(){
		return "src/" + 
				Main.class.getPackage().getName().replaceAll("\\.","/");
	}
}
