/* Copyright (c) 2015-2017 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package expressivo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.experimental.runners.Enclosed;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;

import expressivo.expression.Addition;
import expressivo.expression.Multiplication;
import expressivo.expression.Number;
import expressivo.expression.Variable;
import lib6005.parser.GrammarCompiler;
import lib6005.parser.ParseTree;
import lib6005.parser.Parser;
import lib6005.parser.UnableToParseException;

/**
 * Tests for the Expression abstract data type.
 */
@RunWith(Enclosed.class)
public class ExpressionTest {

    // Testing strategy
    //   TODO
	public static class AssertionTest{
	    @Test(expected=AssertionError.class)
	    public void testAssertionsEnabled() {
	        assert false; // make sure assertions are enabled with VM argument: -ea
	    }
	}
    
    // TODO tests for Expression
    public static class AdditionTest{
    	
	    @Test
	    public void AdditionDerive_TwoNumbers_ReturnsNumberZero(){
	    	Expression a = new Number(1);
	    	Expression b = new Number(2);
	    	Expression sut = new Addition(Arrays.asList(a,b));
	    	
	    	Expression result = sut.derive("x");
	    	
	    	assertEquals(0.0, (double)result.getEvaluator(null).getNumericValue().getValue().get(0), 0);
	    }
	    
	    @Test
	    public void AdditionDerive_OneNumOneVar_ReturnsNumberOne(){
	    	Expression a = new Number(1);
	    	Expression b = new Variable("x");
	    	Expression sut = new Addition(Arrays.asList(a,b));
	    	
	    	Expression result = sut.derive("x");
	    	
	    	assertEquals(1.0, (double)result.getEvaluator(null).getNumericValue().getValue().get(0), 0);
	    }
    	
    	
	    @Test
	    public void AdditionEval_TwoNumbers_ReturnsOneNumber(){
	    	Expression a = new Number(1);
	    	Expression b = new Number(2);
	    	Expression sut = new Addition(Arrays.asList(a,b));
	    	
	    	String result = sut.getEvaluator(null).getSymbolicValue();
	    	
	    	assertEquals("3.0000", result);
	    }
	    
	    @Test
	    public void AdditionEval_TwoVarsNullEnvironment_ReturnsSymbolic(){
	    	Expression a = new Variable("A");
	    	Expression b = new Variable("B");
	    	Expression sut = new Addition(Arrays.asList(a,b));
	    	
	    	String result = sut.getEvaluator(null).getSymbolicValue();
	    	
	    	assertEquals("(A+B)", result);
	    }
	    
	    @Test
	    public void AdditionEval_TwoVarsOneAssigned_ReturnsPartialSymbolic(){
	    	Expression a = new Variable("A");
	    	Expression b = new Variable("B");
	    	Expression sut = new Addition(Arrays.asList(a,b));
	    	
	    	Map<String,Double> env = new HashMap<>();
	    	env.put("A", 5.0);
	    	
	    	String result = sut.getEvaluator(env).getSymbolicValue();
	    	
	    	assertEquals("(5.0000+B)", result);
	    }
	    
	    @Test
	    public void AdditionEval_TwoVarsBothAssigned_ReturnsSummedValue(){
	    	Expression a = new Variable("A");
	    	Expression b = new Variable("B");
	    	Expression sut = new Addition(Arrays.asList(a,b));
	    	
	    	Map<String,Double> env = new HashMap<>();
	    	env.put("A", 5.0);
	    	env.put("B", 11.0);
	    	
	    	String result = sut.getEvaluator(env).getSymbolicValue();
	    	
	    	assertEquals("16.0000", result);
	    }
	    
	    
	    @Test
	    public void Addition_TwoNumberSubExpressions_toStringIsRight(){
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
	    public void Addition_TwoEquivilentExpressionsDifferentOrder_EqualsReturnsFalse(){
	    	Expression a = new Number(1);
	    	Expression b = new Number(2);
	    	Expression test = new Addition(Arrays.asList(a,b));
	    	
	    	Expression c = new Number(2);
	    	Expression d = new Number(1);
	    	Expression sut = new Addition(Arrays.asList(c,d));
	    	
	    	boolean result = sut.equals(test);
	    	
	    	assertFalse(result);
	    }
	    
	    @Test
	    public void Addition_TwoDifferentExpressions_DifferentHashCodes(){
	    	Expression a = new Number(1);
	    	Expression b = new Number(2);
	    	Expression test = new Addition(Arrays.asList(a,b));
	    	
	    	Expression c = new Number(2);
	    	Expression d = new Number(1);
	    	Expression sut = new Addition(Arrays.asList(c,d));
	    	
	    	int hc1 = sut.hashCode();
	    	int hc2 = test.hashCode();
	    	
	    	assertTrue(hc1 != hc2);
	    }
    
    }
    
    
    public static class MultiplicationTest{
        
	    @Test
	    public void MultiplicationDerive_TwoNumbers_ReturnsNumberZero(){
	    	Expression a = new Number(1);
	    	Expression b = new Number(2);
	    	Expression sut = new Multiplication(Arrays.asList(a,b));
	    	
	    	Expression result = sut.derive("x");
	    	
	    	assertEquals(0.0, (double)result.getEvaluator(null).getNumericValue().getValue().get(0), 0);
	    }
	    
	    @Test
	    public void MultiplicationDerive_TwoVars_ReturnsExampleResult(){
	    	Expression a = new Variable("y");
	    	Expression b = new Variable("x");
	    	Expression sut = new Multiplication(Arrays.asList(a,b));
	    	
	    	Expression result = sut.derive("x");
	    	
	    	assertEquals("y", result.getEvaluator(null).getSymbolicValue());
	    }
	    
	    @Test
	    public void MultiplicationDerive_ThreeX_ReturnsExampleResult(){
	    	Expression x = new Variable("x");
	    	Expression sut = new Multiplication(Arrays.asList(x,x,x));
	    	
	    	Expression result = sut.derive("x");
	    	
	    	assertEquals("((x*(x+x))+(x*x))", result.getEvaluator(null).getSymbolicValue());
	    }
	    
	    @Test
	    public void MultiplicationEval_TwoNumbers_ReturnsOneNumber(){
	    	Expression a = new Number(1);
	    	Expression b = new Number(2);
	    	Expression sut = new Multiplication(Arrays.asList(a,b));
	    	
	    	String result = sut.getEvaluator(null).getSymbolicValue();
	    	
	    	assertEquals("2.0000", result);
	    }
	    
	    @Test
	    public void MultiplicationEval_VarAndNumber1_ReturnsJustVar(){
	    	Expression a = new Number(1);
	    	Expression b = new Variable("B");
	    	Expression sut = new Multiplication(Arrays.asList(a,b));
	    	
	    	String result = sut.getEvaluator(null).getSymbolicValue();
	    	
	    	assertEquals("B", result);
	    }
	    
	    @Test
	    public void MultiplicationEval_TwoVarsNullEnvironment_ReturnsSymbolic(){
	    	Expression a = new Variable("A");
	    	Expression b = new Variable("B");
	    	Expression sut = new Multiplication(Arrays.asList(a,b));
	    	
	    	String result = sut.getEvaluator(null).getSymbolicValue();
	    	
	    	assertEquals("(A*B)", result);
	    }
	    
	    @Test
	    public void MultiplicationEval_TwoVarsOneAssigned_ReturnsPartialSymbolic(){
	    	Expression a = new Variable("A");
	    	Expression b = new Variable("B");
	    	Expression sut = new Multiplication(Arrays.asList(a,b));
	    	
	    	Map<String,Double> env = new HashMap<>();
	    	env.put("A", 5.0);
	    	
	    	String result = sut.getEvaluator(env).getSymbolicValue();
	    	
	    	assertEquals("(5.0000*B)", result);
	    }
	    
	    @Test
	    public void MultiplicationEval_TwoVarsBothAssigned_ReturnsSummedValue(){
	    	Expression a = new Variable("A");
	    	Expression b = new Variable("B");
	    	Expression sut = new Multiplication(Arrays.asList(a,b));
	    	
	    	Map<String,Double> env = new HashMap<>();
	    	env.put("A", 5.0);
	    	env.put("B", 11.0);
	    	
	    	String result = sut.getEvaluator(env).getSymbolicValue();
	    	
	    	assertEquals("55.0000", result);
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
	    public void Multiplication_TwoEquivilentExpressionsDifferentOrder_EqualsReturnsFalse(){
	    	Expression a = new Number(1);
	    	Expression b = new Number(2);
	    	Expression test = new Multiplication(Arrays.asList(a,b));
	    	
	    	Expression c = new Number(2);
	    	Expression d = new Number(1);
	    	Expression sut = new Multiplication(Arrays.asList(c,d));
	    	
	    	boolean result = sut.equals(test);
	    	
	    	assertFalse(result);
	    }
	    
	    @Test
	    public void Multiplication_TwoDifferentExpressions_DifferentHashCodes(){
	    	Expression a = new Number(1);
	    	Expression b = new Number(2);
	    	Expression test = new Multiplication(Arrays.asList(a,b));
	    	
	    	Expression c = new Number(2);
	    	Expression d = new Number(1);
	    	Expression sut = new Multiplication(Arrays.asList(c,d));
	    	
	    	int hc1 = sut.hashCode();
	    	int hc2 = test.hashCode();
	    	
	    	assertTrue(hc1 != hc2);
	    }

   }
    
    public static class VariableTest{
	    @Test
	    public void Variable_TwoEquivilentExpressions_EqualsReturnsTrue(){
	
	    	Expression test = new Variable("A");
	    	Expression sut = new Variable("A");
	    	
	    	boolean result = sut.equals(test);
	    	
	    	assertTrue(result);
	    }
	    
	    @Test
	    public void Variable_DifferentExpressions_DifferentHashcodes(){
	
	    	Expression test = new Variable("A");
	    	Expression sut = new Variable("B");
	    	
	    	boolean result = sut.hashCode() != test.hashCode();
	    	
	    	assertTrue(result);
	    }
	    
	    @Test
	    public void VariableDeriv_wrtThisVariable_ReturnsNumberOneExpression(){
	    	Expression sut = new Variable("x");
	    	
	    	Expression result = sut.derive("x");
	    	
	    	assertEquals(1.0, (double)result.getEvaluator(null).getNumericValue().getValue().get(0), 0);
	    }
	    
	    @Test
	    public void VariableDeriv_wrtAnotherVariable_ReturnsNumberZeroExpression(){
	    	Expression sut = new Variable("x");
	    	
	    	Expression result = sut.derive("y");
	    	
	    	assertEquals(0.0, (double)result.getEvaluator(null).getNumericValue().getValue().get(0), 0);
	    }
    }
    
    public static class NumberTest{
	    @Test
	    public void Number_TwoEquivilentExpressions_EqualsReturnsTrue(){
	
	    	Expression test = new Number(1);
	    	Expression sut = new Number(1);
	    	
	    	boolean result = sut.equals(test);
	    	
	    	assertTrue(result);
	    }
	    
	    @Test
	    public void Number_DifferentExpressions_DifferentHashcodes(){
	
	    	Expression test = new Number(1);
	    	Expression sut = new Number(2);
	    	
	    	boolean result = sut.hashCode() != test.hashCode();
	    	
	    	assertTrue(result);
	    }
	    
	    @Test
	    public void NumberDeriv_ReturnsNumberZeroExpression(){
	    	Expression sut = new Number(21);
	    	
	    	Expression result = sut.derive("x");
	    	
	    	assertEquals(0.0, (double)result.getEvaluator(null).getNumericValue().getValue().get(0), 0);
	    }
    }
    
    public static class ParseTest{
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
	    	
	    	assertTrue(result);
	    }
	    
	    @Test
	    public void Parse_SingleFloatExpression_ReturnsCorrectExpression(){
	    	Expression sut = Expression.parse("1.0");
	    	
	    	assertEquals("1.0000", sut.toString());
	    }
	    
//	    @Test
//	    public void Parse_LeftNest_ParsesQuickly(){
//	    	Expression.parse("(((((((a + b) * c) * d) + e) + f) * g) * x)");
//	    	
//	    	assertTrue(true);
//	    }
	    
	    @Test
	    public void Parse_sandbox() throws UnableToParseException, IOException{
	    	
			Parser<ExpressionGrammar> parser =
				     GrammarCompiler.compile(new File(getPath(), 
				    		 "Expression.g"), ExpressionGrammar.ROOT); 
	    	
	    	ParseTree<ExpressionGrammar> tree = parser.parse("5 + 2 * 3 + 21");
			
			System.out.println(tree.toString());
			
			//Display a graph of the tree in a web browser
			//tree.display();
			
			System.out.println("\n\nIndented representation of tree:");
			visitAll(tree, "  ");
	    }
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
