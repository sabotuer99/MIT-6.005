/* Copyright (c) 2015-2017 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package expressivo;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Arrays;

import expressivo.expression.Addition;
import expressivo.expression.Multiplication;
import expressivo.expression.Number;
import expressivo.expression.Variable;
import lib6005.parser.GrammarCompiler;
import lib6005.parser.ParseTree;
import lib6005.parser.Parser;

import org.junit.Ignore;
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
    	Expression sut = new Addition(Arrays.asList(a,b));
    	
    	String result = sut.toString();
    	
    	assertEquals("(1.0000+2.0000)", result);
    }
    
    @Test
    public void Addition_TwoEquivilentExpressions_EqualsReturnsTrue(){
    	Expression a = new Number(1);
    	Expression b = new Number(2);
    	Expression test = new Addition(Arrays.asList(a,b));
    	
    	Expression c = new Number(1);
    	Expression d = new Number(2);
    	Expression sut = new Addition(Arrays.asList(c,d));
    	
    	boolean result = sut.equals(test);
    	
    	assertTrue(result);
    }
    
    @Test
    public void Multiplication_TwoEquivilentExpressions_EqualsReturnsTrue(){
    	Expression a = new Number(1);
    	Expression b = new Number(2);
    	Expression test = new Multiplication(Arrays.asList(a,b));
    	
    	Expression c = new Number(1);
    	Expression d = new Number(2);
    	Expression sut = new Multiplication(Arrays.asList(c,d));
    	
    	boolean result = sut.equals(test);
    	
    	assertTrue(result);
    }

    @Test
    public void Variable_TwoEquivilentExpressions_EqualsReturnsTrue(){

    	Expression test = new Variable("A");
    	Expression sut = new Variable("A");
    	
    	boolean result = sut.equals(test);
    	
    	assertTrue(result);
    }
    
    @Test
    public void Number_TwoEquivilentExpressions_EqualsReturnsTrue(){

    	Expression test = new Number(1);
    	Expression sut = new Number(1);
    	
    	boolean result = sut.equals(test);
    	
    	assertTrue(result);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void Parse_InvalidInput_ThrowsIllegalArgumentException(){
    	Expression.parse("!");
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void Parse_EmptyInput_ThrowsIllegalArgumentException(){
    	Expression.parse("");
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void Parse_NullInput_ThrowsIllegalArgumentException(){
    	Expression.parse(null);
    }
    
    @Test
    public void Parse_TwoEquivalentExpressions_ReturnsEqualASTs(){
    	Expression sut1 = Expression.parse("5+5*5");
    	Expression sut2 = Expression.parse("((5.000)     + ((5.0) *    5.00000))    ");
    			
    	boolean result = sut1.equals(sut2);
    	
    	System.out.println(sut1.toString());
    	System.out.println(sut2.toString());
    	
    	assertTrue(result);
    }
    
    @Test
    public void Parse_SingleFloatExpression_ReturnsCorrectExpression(){
    	Expression sut = Expression.parse("1.0");
    	
    	assertEquals("1.0000", sut.toString());
    }
    
    @Ignore
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
    	
    	tree = parser.parse("5");
    	
    	visitAll(tree, "  ");
    	
    	tree = parser.parse("(5) + (5)");
    	
    	visitAll(tree, "  ");
    	
    	tree = parser.parse("((5) + (5))");
    	
    	visitAll(tree, "  ");
    	
    	tree = parser.parse("(x + y) * ((5 + w) +52) + 1 + 2 + 3");
    	//tree.display();
    	visitAll(tree, "");
    	
    	Expression root = Expression.parse("5*5+5");
    	
    	System.out.println(root.toString());
    	
    	root = Expression.parse("(x + y) * ((5 + w) +52) + 1 + 2 + 3");
    	System.out.println(root.toString());
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
	            visitAll(child, indent + "|  ");
	        }
	    }
	}
	
	private static String getPath(){
		return "src/" + 
				Main.class.getPackage().getName().replaceAll("\\.","/");
	}
}
