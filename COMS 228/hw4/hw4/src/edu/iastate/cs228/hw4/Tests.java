package edu.iastate.cs228.hw4;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

public class Tests {

@Test
public void testExpressionHelpers() {
	
	String  a = "s f  s a b c";
	char b = '#';
	char c =  '(';
	String d = "4";
	assertEquals(true, Expression.isVariable('s'));
	assertEquals(false, Expression.isVariable('%'));
	assertEquals("s f s a b c", Expression.removeExtraSpaces(a));
	assertEquals(false, Expression.isOperator(c) && Expression.isOperator(b));
	assertEquals(true, Expression.isInt(d));
}

@Test
public void testOperationComparTo() {
	Operator test = new Operator('+');
	Operator test2 = new Operator('-');
	assertEquals(0, test.compareTo(test2));
}

@Test
public void testPostEval() throws ExpressionFormatException, UnassignedVariableException {
PostfixExpression a = new PostfixExpression("6 12 *");
assertEquals(72, a.evaluate());
assertEquals("6 12 *", a.toString());
PostfixExpression x = new PostfixExpression("9 ~");
assertEquals(-9, x.evaluate());
HashMap<Character, Integer> hashI = new HashMap<Character, Integer> ();
hashI.put('a', 2);
hashI.put('b', 3);
PostfixExpression test = new PostfixExpression("a 5 + 2 -", hashI);
assertEquals(5, test.evaluate());
}

@Test 
public void testInfixEval() throws ExpressionFormatException, UnassignedVariableException{
	InfixExpression test = new InfixExpression ("4 * 2");
	assertEquals("4 * 2", test.toString());
	test.postfix();
	assertEquals("4 2 *", test.postfixString());
	assertEquals(8, test.evaluate());
	
}

}
