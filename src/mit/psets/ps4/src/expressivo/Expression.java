package expressivo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import expressivo.expression.Addition;
import expressivo.expression.ExpressionEvaluator;
import expressivo.expression.Multiplication;
import expressivo.expression.Number;
import expressivo.expression.Variable;
import lib6005.parser.GrammarCompiler;
import lib6005.parser.ParseTree;
import lib6005.parser.Parser;

/**
 * An immutable data type representing a polynomial expression of:
 *   + and *
 *   nonnegative integers and floating-point numbers
 *   variables (case-sensitive nonempty strings of letters)
 * 
 * <p>PS1 instructions: this is a required ADT interface.
 * You MUST NOT change its name or package or the names or type signatures of existing methods.
 * You may, however, add additional methods, or strengthen the specs of existing methods.
 * Declare concrete variants of Expression in their own Java source files.
 */
public interface Expression {
    
    // Datatype definition
    //   TODO
    
    /**
     * Parse an expression.
     * @param input expression to parse, as defined in the PS1 handout.
     * @return expression AST for the input
     * @throws IllegalArgumentException if the expression is invalid
     */
    public static Expression parse(String input) {
		
		String path = "src/" + Expression.class.getPackage().getName().replaceAll("\\.","/");
		
		Parser<ExpressionGrammar> parser;

			try {
				parser = GrammarCompiler.compile(new File(path, 
						 "Expression.g"), ExpressionGrammar.ROOT);
				
				ParseTree<ExpressionGrammar> tree = parser.parse(input);
				
				return buildAST(tree);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				IllegalArgumentException ex = new IllegalArgumentException("Unable to parse: \"" + input + "\"", e);
				throw ex;
			} 
	}

	/**
     * Function converts a ParseTree to an IntegerExpression. 
     * @param p
     *  ParseTree<IntegerGrammar> that is assumed to have been constructed by the grammar in IntegerExpression.g
     * @return
     */
    static Expression buildAST(ParseTree<ExpressionGrammar> p){

        switch(p.getName()){

        case ROOT:{
        	/*
        	 * root has a single expression child
        	 */
        	return buildAST(filterws(p).get(0));
        }
        case EXPRESSION:{
        	/*
        	 * expression has a single child, either a primitive,
        	 * addition, or subtraction
        	 */
        	return buildAST(filterws(p).get(0));
        }	

        case ADDITION:{
        	/*
        	 * addition can have many children, get those as expressions 
        	 * first to create addition expression
        	 */
        	List<Expression> terms = new ArrayList<>();
        	for(ParseTree<ExpressionGrammar> sub : filterws(p)){
            	terms.add(buildAST(sub));
        	}
        	
        	//if there is only one term, just pass it through
        	if(terms.size() == 1){
        		return terms.get(0);
        	} else {
        		return new Addition(terms);
        	}
        }
        
        case SUBTRACTION:{
        	throw new RuntimeException("Subraction not supported:" + p);
        }

		case MULTIPLICATION:{
        	/*
        	 * addition can have many children, get those as expressions 
        	 * first to create addition expression
        	 */
        	List<Expression> terms = new ArrayList<>();
        	for(ParseTree<ExpressionGrammar> sub : filterws(p)){
        			terms.add(buildAST(sub));
        	}
        	
        	//if there is only one term, just pass it through
        	if(terms.size() == 1){
        		return terms.get(0);
        	} else {
        		return new Multiplication(terms);
        	}
        }
		
		case DIVISION:{
        	throw new RuntimeException("Division not supported:" + p);
        }

        case PRIMITIVE: {
        	// A primitive can only have one non-whitespace child, so just return 
        	// the AST of that child
	        	for(ParseTree<ExpressionGrammar> sub : filterws(p)){
	        		return buildAST(sub);
	        	}
	        	throw new RuntimeException("Primitive should never reach here:" + p);
        	}
        
        case NUMBER:{
        	// A number is always a single literal, build and return
            return new Number(Double.parseDouble(p.getContents()));
        	}
        
        case VARIABLE:{
        	// A variable is always a single literal, build and return
        	return new Variable(p.getContents());
        	}
        
        case WHITESPACE: {
            /*
             * Since we are always avoiding calling buildAST with whitespace, 
             * the code should never make it here. 
             */
            throw new RuntimeException("Whitespace should never reach here:" + p);
        	}
        }   
        /*
         * The compiler should be smart enough to tell that this code is unreachable, but it isn't.
         */
        throw new RuntimeException("You should never reach here:" + p);
    }
    
    static List<ParseTree<ExpressionGrammar>> filterws(ParseTree<ExpressionGrammar> node){
    	List<ParseTree<ExpressionGrammar>> filtered = new ArrayList<>();
    	for(ParseTree<ExpressionGrammar> sub : node.children()){
    		if(!sub.getName().equals(ExpressionGrammar.WHITESPACE)){
    			filtered.add(sub);
    		}
    	}
    	
    	return filtered;
    }
    
    /**
     * @return a parsable representation of this expression, such that
     * for all e:Expression, e.equals(Expression.parse(e.toString())).
     */
    @Override 
    public String toString();

    /**
     * @param thatObject any object
     * @return true if and only if this and thatObject are structurally-equal
     * Expressions, as defined in the PS1 handout.
     */
    @Override
    public boolean equals(Object thatObject);
    
    /**
     * @return hash code value consistent with the equals() definition of structural
     * equality, such that for all e1,e2:Expression,
     *     e1.equals(e2) implies e1.hashCode() == e2.hashCode()
     */
    @Override
    public int hashCode();
    
    // TODO more instance methods
    public void visit(ExpressionVisitor visitor);
    public ExpressionEvaluator getEvaluator(Map<String, Double> environment);
    public Expression derive(String wrt);
    
    /* Copyright (c) 2015-2017 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires permission of course staff.
     */
}
