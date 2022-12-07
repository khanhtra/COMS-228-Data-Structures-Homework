package junit;

import edu.iastate.cs228.hw4.InfixExpression;
import edu.iastate.cs228.hw4.PostfixExpression;
import org.junit.Assert;
import org.junit.Test;

/**
 * BasicEvaluation
 * <p>
 * Usage:
 *
 * @author John Chandara <chandara@iastate.edu>
 * @version 190413
 * @license MIT License (X11 Variant)
 * @category Educational
 */
public class BasicEvaluation {
	private static int CollectResults (String strExpression) throws Exception {
		InfixExpression expression = new InfixExpression (strExpression);
		expression.postfix ();
		return new PostfixExpression (expression.postfixString ()).evaluate ();
	}

	@Test
	public void Addition () throws Exception {
		Assert.assertEquals (2, CollectResults ("1 + 1"));
		Assert.assertEquals (76, CollectResults ("2 + 72 + 2"));
		Assert.assertEquals (12, CollectResults ("5 + 10 - 3"));
		Assert.assertEquals (0, CollectResults ("- 2 + 5 - 3"));
		Assert.assertEquals (0, CollectResults ("5 + - 2 - 3"));
	}

	@Test
	public void Multiplication () throws Exception {
		Assert.assertEquals (2, CollectResults ("1 * 2"));
		Assert.assertEquals (25, CollectResults ("50 * 1 / 2"));
		Assert.assertEquals (34, CollectResults ("8 * 2 + 19 / 2 * 2"));
		Assert.assertEquals (137, CollectResults ("9 * 15 - - 2 * 1"));
		Assert.assertEquals (65, CollectResults ("0 * 585 - 2 * - 25 + 15"));
	}

	@Test
	public void Exponentiation () throws Exception {
		Assert.assertEquals (25, CollectResults ("5 ^ 2"));
		Assert.assertEquals (50, CollectResults ("5 ^ 2 * 2 * 1"));
		Assert.assertEquals (-22, CollectResults ("- 2 ^ 5 + 10"));
		Assert.assertEquals (126, CollectResults ("5 ^ 2 * 5 - - 1"));
		Assert.assertEquals (22, CollectResults ("2 ^ 2 + 2 + 8 + 8"));
	}

	@Test
	public void Parenthesis () throws Exception {
		Assert.assertEquals (4, CollectResults ("2 + (2 * 1)"));
		Assert.assertEquals (35, CollectResults ("(5 + 1 * 5) * (5 + 2) / 2"));
		Assert.assertEquals (-2, CollectResults ("2 + 2 - (3 * 2)"));
		Assert.assertEquals (5, CollectResults ("1 ^ 1 * 1 - - (2 + 2)"));
		Assert.assertEquals (-14, CollectResults ("1 ^ 8 + - (2 + 5 + 8)"));
	}
}
