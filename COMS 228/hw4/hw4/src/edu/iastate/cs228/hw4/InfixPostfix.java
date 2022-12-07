package edu.iastate.cs228.hw4;

import java.io.File;

/**
 *  
 * @author
 *
 */

/**
 * 
 * This class evaluates input infix and postfix expressions. 
 *
 */

import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

/**
 * 
 * @author Khanh Tran
 *
 */
public class InfixPostfix {

	/**
	 * Repeatedly evaluates input infix and postfix expressions. See the project
	 * description for the input description. It constructs a HashMap object for
	 * each expression and passes it to the created InfixExpression or
	 * PostfixExpression object.
	 * 
	 * @param args
	 * @throws ExpressionFormatException
	 * @throws UnassignedVariableException
	 **/
	public static void main(String[] args) {
		// Initial lines
		System.out.println("Evaluation of Infix and Postfix Expressions");
		System.out.println("keys: 1 (standard input) 2 (file input) 3 (exit)");
		System.out.println(("Enter “I” before an infix expression, “P” before a postfix expression”" + "\n"));
		// Trials should update
		int trials = 1;
		System.out.print("Trial 1: ");
		// Scanner for user input
		Scanner in = new Scanner(System.in);
		String input = ""; // initial cond. for switch case
		// Loop for inputs
		try {
			loop: while (in.hasNext()) {

				input = in.nextLine();
				switch (input) {

				// Postfix/Infix
				case "1":
					System.out.print("Expression: ");
					HashMap<Character, Integer> hM = new HashMap<Character, Integer>();
					input = in.nextLine();
					InfixExpression infix = new InfixExpression(input);
					PostfixExpression postFixx = new PostfixExpression(input);
					if (infix.toString().contains("I")) {
						String resultI = infix.toString().replaceAll("I", "");
						infix.postfix();
						String resultP = infix.postfixString().replaceAll("I", "").trim();
						System.out.println("Infix form: " + Expression.removeExtraSpaces(resultI).trim());
						System.out.println("Postfix form: " + Expression.removeExtraSpaces(resultP).trim());

						// String that does not contain the letter
						String resultAfterTrim = resultP;
						InfixExpression afterTrim = new InfixExpression(resultAfterTrim);
						if (!containsOnlyIorP(resultI)) {
							System.out.println("Expression value: " + afterTrim.evaluate());
						}
						if (containsOnlyIorP(resultI)) {
							System.out.println("where");
							Scanner infixSca = new Scanner(resultI);
							while (infixSca.hasNext()) {
								String st = infixSca.next();
								char c = st.charAt(0);
								if (Expression.isVariable(c)) {
									System.out.print(c + " : ");
									int varInput = in.nextInt();
									hM.put(c, varInput);
									PostfixExpression pf = new PostfixExpression(resultI, hM);
									System.out.println("Expression value: " + pf.evaluate());
								}
							}
							infixSca.close();
						}
					} else if (postFixx.toString().contains("P")) {
						String resultP = postFixx.toString().replaceAll("P", "");
						System.out.println("Postfix form: " + Expression.removeExtraSpaces(resultP).trim());

						// String that does not contain the letter
						String resultAfterTrim = resultP;
						PostfixExpression afterTrim = new PostfixExpression(resultAfterTrim);
						if (!containsOnlyIorP(resultP)) {
							System.out.println("Expression value: " + afterTrim.evaluate() + "\n");
						}
						if (containsOnlyIorP(resultP)) {
							System.out.println("where");
							Scanner infixSca = new Scanner(resultP);
							while (infixSca.hasNext()) {
								String st = infixSca.next();
								char c = st.charAt(0);
								if (Expression.isVariable(c)) {
									System.out.print(c + " : ");
									int varInput = in.nextInt();
									hM.put(c, varInput);
								}
							}
							PostfixExpression pf = new PostfixExpression(resultP, hM);
							System.out.println("Expression value: " + pf.evaluate() + "\n");
							infixSca.close();
						}
						in.nextLine();
					}
					trials++;
					System.out.print("Trial " + trials + ": ");
					break;

				// file input
				case "2":
					System.out.println("\nInput from file");
					System.out.print("Enter file name: ");
					HashMap<Character, Integer> hMa = new HashMap<Character, Integer>();
					String fileIn = in.nextLine();
					File f = new File (fileIn);
					Scanner fileScan = new Scanner(f);
		

					// Postfix
					while (fileScan.hasNextLine()) {
						PostfixExpression pf = new PostfixExpression(fileScan.nextLine());
						InfixExpression inf = new InfixExpression(fileScan.nextLine());
						
						if (inf.toString().contains("I")) {
							String resultI = inf.toString().replaceAll("I", "");
							inf.postfix();
							String resultP = inf.postfixString().replaceAll("I", "").trim();
							System.out.println("Infix form: " + Expression.removeExtraSpaces(resultI).trim());
							System.out.println("Postfix form: " + Expression.removeExtraSpaces(resultP).trim());

							// String that does not contain the letter
							String resultAfterTrim = resultP;
							InfixExpression afterTrim = new InfixExpression(resultAfterTrim);
							if (!containsOnlyIorP(resultI)) {
								System.out.println("Expression value: " + afterTrim.evaluate());
							}
							if (containsOnlyIorP(resultI)) {
								System.out.println("where");
								Scanner infixSca = new Scanner(resultI);
								while (infixSca.hasNext()) {
									String st = infixSca.next();
									int number = infixSca.nextInt();
									char c = st.charAt(0);
									if (Expression.isVariable(c)) {
										System.out.print(c + " : " + number);
										hMa.put(c, number);
										PostfixExpression pfi = new PostfixExpression(resultI, hMa);
										System.out.println("Expression value: " + pfi.evaluate());
									}
								}
								infixSca.close();
							}
						}
						else if (pf.toString().contains("P")) {
							String resultP = pf.toString().replaceAll("P", "");
							System.out.println("Postfix form: " + Expression.removeExtraSpaces(resultP).trim());

							// String that does not contain the letter
							String resultAfterTrim = resultP;
							char temp;
							int number = 0;
							PostfixExpression afterTrim = new PostfixExpression(resultAfterTrim);
							if (!containsOnlyIorP(resultP)) {
								System.out.println("Expression value: " + afterTrim.evaluate() + "\n");
							}
							if (containsOnlyIorP(resultP)) {
							
								Scanner infixSca = new Scanner(resultP);
								while (infixSca.hasNext()) {
									String st = infixSca.next();
									char c = st.charAt(0);
									if (Expression.isVariable(c)) {
										temp = c;
										if (Expression.isInt(st)) {
											number = Integer.parseInt(st); 
										}
										System.out.println("where " + c + " : " + st);
										hMa.put(temp, number);
									}
								}
								PostfixExpression pfi = new PostfixExpression(resultP, hMa);
								System.out.println("Expression value: " + pfi.evaluate() + "\n");
								infixSca.close();
							}
						}
					}

					// exit program
				case "3":
					System.out.println("Program has ended");
					break loop;
				// if not one of the options
				default:
					System.out.println("Please enter a valid choice!");
					break;

				}
			}
		} catch (Exception e) {
			System.out.println("Error! Please restart program, possible errors with expression!");
		}

	}

	private static boolean containsOnlyIorP(String s) {
		Scanner in = new Scanner(s);
		while (in.hasNext()) {
			String a = in.next();
			char ch = a.charAt(0);
			if (Expression.isVariable(ch)) {
				return true;
			}
		}

		return false;
	}
}

// helper methods if needed
