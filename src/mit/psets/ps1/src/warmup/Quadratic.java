package warmup;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Quadratic {

	public static void log(Object message) {

		BufferedWriter bw = null;
		try {
			FileWriter writer = new FileWriter("log.txt", true);
			bw = new BufferedWriter(writer);
			bw.write(message.toString());
			bw.newLine();
			bw.flush();
		} catch (IOException e) {
			// do something
		} finally { // always close the file
			if (bw != null)
				try {
					bw.close();
				} catch (IOException ioe2) {
					// just ignore it
				}
		}
	}

	/**
	 * Find the integer roots of a quadratic equation, ax^2 + bx + c = 0.
	 * 
	 * @param a
	 *            coefficient of x^2
	 * @param b
	 *            coefficient of x
	 * @param c
	 *            constant term. Requires that a, b, and c are not ALL zero.
	 * @return all integers x such that ax^2 + bx + c = 0.
	 */
	public static Set<Integer> roots(int a, int b, int c) {
		Set<Integer> roots = new HashSet<>();

		long al = a;
		long bl = b;
		long cl = c;

		log(String.format("%d %d %d\n", a, b, c));

		long term = bl * bl - 4 * al * cl;

		double root1 = 0;
		double root2 = 0;
		if(al != 0){
			root1 = (-b + Math.sqrt(term)) / (2 * a);
			root2 = (-b - Math.sqrt(term)) / (2 * a);
		} else {
			root1 = (-2*c)/(b + Math.sqrt(term));
			root2 = (-2*c)/(b - Math.sqrt(term));
		}
		

		log(root1);

		if (isValidIntegerRoot(root1)) {
			roots.add((int) root1);
		}

		if (isValidIntegerRoot(root2)) {
			roots.add((int) root2);
		}

		return roots;
	}

	private static boolean isValidIntegerRoot(double root){
		return 	root < Double.POSITIVE_INFINITY && 
				root > Double.NEGATIVE_INFINITY && 
				root - (int) root < 0.000001;
	}
	
	
	/**
	 * Main function of program.
	 * 
	 * @param args
	 *            command-line arguments
	 */
	public static void main(String[] args) {
		System.out.println("For the equation x^2 - 4x + 3 = 0, the possible solutions are:");
		Set<Integer> result = roots(1, -4, 3);
		System.out.println(result);
	}

	/*
	 * Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
	 * Redistribution of original or derived work requires explicit permission.
	 * Don't post any of this code on the web or to a public Github repository.
	 */
}
