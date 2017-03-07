/* Copyright (c) 2015-2017 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */

// grammar Expression;

/*
 *
 * You should make sure you have one rule that describes the entire input.
 * This is the "start rule". Below, "root" is the start rule.
 *
 * For more information, see the parsers reading.
 */
 
 // Got help from https://cs61.seas.harvard.edu/wiki/images/d/d4/Cs61-2013-l22-scribe2.pdf --left recursion, no good
 // Tried https://en.wikipedia.org/wiki/Operator-precedence_parser
 

@skip whitespace{
    root ::= expression;
	expression ::= (primitive | addition | subtraction);
	
	addition ::= (primitive | multiplication | division) ("+" (primitive | multiplication | division))*;
	subtraction ::= (primitive | multiplication | division) ("-" (primitive | multiplication | division))*;
	
	multiplication ::= primitive ("*" primitive)*;
	division ::= primitive ("/" primitive)*;
	
	primitive ::= number | variable | '(' expression ')';
}

number ::= [0-9]+;
variable ::= [A-Za-z]+;
whitespace ::= [ ]+;

/*
//The IntegerExpression grammar
@skip whitespace{
    root ::= sum;
    sum ::= primitive ('+' primitive)*;
    primitive ::= number | '(' sum ')';
}
whitespace ::= [ \t\r\n];
number ::= [0-9]+;
*/
