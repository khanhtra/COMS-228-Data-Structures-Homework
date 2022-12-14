package edu.iastate.cs228.hw4;

/**
 *  
 * @author  Khanh Tran
 *
 */

/**
 * 
 * This class represents operators '+', '-', '*', '/', '%', '^', '(', and ')'.  
 * Every operator has an input precedence, a stack precedence, and a rank, as specified 
 * in the table below. 
 * 
 *   operator       input precedence        stack precedence       rank
 *   ___________________________________________________________________
 *   - (unary)				6						 5				 0
 *   +  -                   1                        1              -1
 *   *  /  %                2                        2              -1
 *   ^                      4                        3              -1
 *   (                      7                       -1               0
 *   )                      0                        0               0 
 *
 */


import java.lang.Comparable; 

public class Operator implements Comparable<Operator>
{
	public char operator; 	      // operator
	
	private	int inputPrecedence;  // input precedence of operator in the range [0, 6]
	private	int stackPrecedence;  // stack precedence of operator in the range [-1, 5]

	/**
	 * Constructor 
	 * 
	 * ch == '~' if the operator is unary minus. 
	 * 
	 * Refer to the table in Section 2.2 of the project description when setting the values of 
	 * inputPrecedence and stackPrecedence. 
	 * 
	 * @param ch
	 */
	public Operator(char ch) 
	{
		operator = ch;
		int inputP = 0;
		int stackP = 0;
		switch (operator) {
		case '~': 
			inputP = 6;
			stackP = 5;
			break;
		case '+':
		case '-':
			inputP = 1;
			stackP = 1;
			break;
		case '*':
		case '/':
		case '%':
			inputP = 2;
			stackP = 2;
			break;
		case '^':
			inputP = 4;
			stackP = 3;
			break;
		case '(':
			inputP = 7;
			stackP = -1;
			break;
		case ')':
			inputP = 0;
			stackP = 0;
			break;
		default:
		break;
		}
		inputPrecedence = inputP;
		stackPrecedence = stackP;
	}
	

	/**
	 * Returns 1, 0, -1 if the stackPrecedence of this operator is greater than, equal to, 
	 * or less than the inputPrecedence of the parameter operator op. It's for determining 
	 * whether this operator on the stack should be output before pushing op onto the stack.
	 */
	@Override
	public int compareTo(Operator op)
	{ 	
		if (stackPrecedence == op.inputPrecedence) {
			return 0;
		}
		else if (stackPrecedence > op.inputPrecedence) {
			return 1;
		}
		return -1;  
	} 


	/**
	 * 
	 * @return char Returns the operator character.  
	 */
	public char getOp()   
	{
	   return operator; 
	}
}
