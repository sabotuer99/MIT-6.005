/* Copyright (c) 2015-2017 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package expressivo;

import static org.junit.Assert.*;

import expressivo.expression.Addition;
import expressivo.expression.Number;
import expressivo.expression.Variable;

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
    
}
