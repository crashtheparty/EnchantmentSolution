package org.ctp.enchantmentsolution.utils;

import java.util.Random;

public class MathUtils {

	public static int normalize(int min, int max, int normalize) {
		double num = min;
		if (normalize >= 1) for(int i = 0; i < normalize; i++)
			num += Math.random() * (max / normalize);
		else if (normalize <= -1) {
			for(int i = 0; i < normalize * -1; i++)
				num += Math.random() * (max / normalize);
			double middle = (max - min) * 0.5;
			if (num > middle) num = max - (num - middle);
			else
				num = min + (middle - num);
		} else
			num += Math.random() * max;
		return (int) num;
	}
	
	public static int randomInt(int min, int max) {
		return (int) (Math.random() * (max - min + 1) + min);
	}

	public static int randomIntWithChance(double min, double max) {
		double random = (Math.random() * (max - min) + min);
		int randInt = 0;
		while (random >= 1) {
			randInt++;
			random--;
		}
		if (random > Math.random()) randInt++;
		return randInt;
	}

	public static int randomInt(int num) {
		double random = Math.random();

		return (int) (random * num);
	}

	public static float randomFloat(double no) {
		return (float) Math.random();
	}

	public static double eval(final String str) {
		return new Object() {
			int pos = -1, ch;

			void nextChar() {
				ch = ++pos < str.length() ? str.charAt(pos) : -1;
			}

			boolean eat(int charToEat) {
				while (ch == ' ')
					nextChar();
				if (ch == charToEat) {
					nextChar();
					return true;
				}
				return false;
			}

			double parse() {
				nextChar();
				double x = parseExpression();
				if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char) ch);
				return x;
			}

			// Grammar:
			// expression = term | expression `+` term | expression `-` term
			// term = factor | term `*` factor | term `/` factor
			// factor = `+` factor | `-` factor | `(` expression `)`
			// | number | functionName factor | factor `^` factor

			double parseExpression() {
				double x = parseTerm();
				for(;;)
					if (eat('+')) x += parseTerm(); // addition
					else if (eat('-')) x -= parseTerm(); // subtraction
					else
						return x;
			}

			double parseTerm() {
				double x = parseFactor();
				for(;;)
					if (eat('*')) x *= parseFactor(); // multiplication
					else if (eat('/')) x /= parseFactor(); // division
					else
						return x;
			}

			double parseFactor() {
				if (eat('+')) return parseFactor(); // unary plus
				if (eat('-')) return -parseFactor(); // unary minus

				double x;
				int startPos = pos;
				if (eat('(')) { // parentheses
					x = parseExpression();
					eat(')');
				} else if (ch >= '0' && ch <= '9' || ch == '.' || ch == 'E') { // numbers
					while (ch >= '0' && ch <= '9' || ch == '.' || ch == 'E')
						if (ch == 'E') {
							nextChar();
							eat('-');
						} else
							nextChar();
					x = Double.parseDouble(str.substring(startPos, pos));
				} else if (ch >= 'a' && ch <= 'z' || ch >= 'A' && ch <= 'Z') { // functions
					while (ch >= 'a' && ch <= 'z' || ch >= 'A' && ch <= 'Z')
						nextChar();
					String func = str.substring(startPos, pos);
					x = parseFactor();
					if (func.equals("sqrt")) x = Math.sqrt(x);
					else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
					else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
					else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
					else if (func.equals("log")) x = Math.log(x);
					else if (func.equals("rand")) x = randomInt((int) x);
					else if (func.equals("randFloat")) x = randomFloat(x);
					else if (func.equals("int")) x = (int) x;
					else if (func.equals("float")) x = (float) x;
					else if (func.equals("roundUp")) x = (int) (x + 0.5);
					else
						throw new RuntimeException("Unknown function: " + func);
				} else
					throw new RuntimeException("Unexpected: " + (char) ch);

				if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

				return x;
			}
		}.parse();
	}

	public static int randomIntFromSeed(Random random, int num) {
		return (int) (random.nextDouble() * num);
	}

	public static float randomFloatFromSeed(Random random) {
		return random.nextFloat();
	}
}
