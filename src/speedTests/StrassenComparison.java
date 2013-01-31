/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package speedTests;

import java.util.Scanner;
import matrix.Matrix;

/**
 * Class is an interactive console program to compare the naive and Strassen
 * algorithms for matrix multiplication.
 *
 * @author Lasse
 */
public class StrassenComparison {

    private Scanner scanner;

    /**
     * Creates a new comparison program.
     */
    public StrassenComparison() {
        scanner = new Scanner(System.in);
    }

    /**
     * Runs the program.
     */
    public void run() {

        System.out.println();
        System.out.println("Entered matrix multiplication comparison.");
        System.out.println("This program will compare Strassen's algorithm "
                + "for matrix multiplication with the standard method.");

        chooseComparison();

    }

    /**
     * Lets the user choose what kind of comparison to use.
     */
    private void chooseComparison() {
        String input;
        while (true) {
            System.out.println();
            System.out.println("Select comparison method.");
            System.out.println("1: Custom comparison");
            System.out.println("2: Default comparison");
            System.out.println("0: Quit comparison program");
            System.out.println();

            System.out.println("Enter selection:");
            System.out.print(">> ");

            input = scanner.nextLine();

            switch (input) {
                case "1":
                    runCustomComparison();
                    continue;
                case "2":
                    runDefaultComparison();
                    continue;
                case "0":
                    return;
                default:
                    System.out.println("Unrecognised command: '" + input + "'");
            }
        }
    }

    /**
     * Runs the predefined default comparison.
     */
    private void runDefaultComparison() {
        compareMultiplicationMultiplicative(64, 1024, 2, 1);
    }

    /**
     * Runs a custom comparison, where the user first gets to choose the
     * parameters.
     */
    private void runCustomComparison() {
        int minSize, maxSize, dSize, threshold;

        while (true) {
            try {
                System.out.println();

                System.out.println("Enter minimum matrix size:");
                System.out.print(">> ");
                minSize = Integer.parseInt(scanner.nextLine());

                if (minSize < 1) {
                    reportInvalidSelection("Size must be a positive integer!");
                    continue;
                }

                System.out.println("Enter maximum matrix size:");
                System.out.print(">> ");
                maxSize = Integer.parseInt(scanner.nextLine());

                if (maxSize < minSize) {
                    reportInvalidSelection("Maximum size must be at least minimum size!");
                    continue;
                }

                System.out.println("Enter step to increase matrix size:");
                System.out.print(">> ");
                dSize = Integer.parseInt(scanner.nextLine());

                if (dSize < 1) {
                    reportInvalidSelection("Step must be a positive integer!");
                    continue;
                }

                System.out.println("Enter threshold for Strassen's algorithm: (1 for pure Strassen)");
                System.out.print(">> ");
                threshold = Integer.parseInt(scanner.nextLine());

                if (threshold < 1) {
                    reportInvalidSelection("Threshold must be a positive integer!");
                    continue;
                }

                compareMultiplicationAdditive(minSize, maxSize, dSize, threshold);

                if (!wantsNewComparison()) {
                    return;
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid input!");
                continue;
            }
        }
    }

    /**
     * Reports that an invalid selection has been made.
     *
     * @param message The error message.
     */
    private void reportInvalidSelection(String message) {
        System.out.println("Invalid selection!");
        System.out.println(message);
    }

    /**
     * Asks the user if another comparison is wanted.
     *
     * @return True, if the user wants another comparison, false otherwise.
     */
    private boolean wantsNewComparison() {
        System.out.println();
        String input;
        while (true) {
            System.out.println("Do you want to run another comparison? (Y/N)");
            System.out.print(">> ");
            input = scanner.nextLine();
            if (input.equalsIgnoreCase("Y")) {
                return true;
            }
            if (input.equalsIgnoreCase("N")) {
                return false;
            }
            System.out.println("Invalid input!");
        }
    }

    /**
     * Runs a comparison to matrices of sizes minSize x minSize to maxSize x
     * maxSize, with steps of dSize.
     *
     * @param minSize The starting size of the matrices.
     * @param maxSize The last size of the matrices.
     * @param dSize The addition made to the sizes of the matrices between each
     * compared multiplication.
     * @param threshold Threshold used in Strassen's multiplication.
     */
    private void compareMultiplicationAdditive(int minSize, int maxSize, int dSize, int threshold) {
        System.out.println();
        System.out.format("%-6s%-12s%-16s%-12s\n", "n:  ", "Naive (s):", "Strassen (s):", "ratio:");
        for (int i = minSize; i <= maxSize; i += dSize) {
            compareMultiplications(i, threshold);
        }
    }

    /**
     * Runs a comparison to matrices of sizes minSize x minSize to maxSize x
     * maxSize, multiplying the size of the matrices with dSize between each
     * comparison.
     *
     * @param minSize The starting size of the matrices.
     * @param maxSize The last size of the matrices.
     * @param dSize The multiplier used to resize the sizes of the matrices
     * between each compared multiplication.
     * @param threshold Threshold used in Strassen's multiplication.
     */
    private void compareMultiplicationMultiplicative(int minSize, int maxSize, int dSize, int threshold) {
        System.out.println();
        System.out.format("%-6s%-12s%-16s%-12s\n", "n:  ", "Naive (s):", "Strassen (s):", "ratio:");
        for (int i = minSize; i <= maxSize; i *= dSize) {
            compareMultiplications(i, threshold);
        }
    }

    /**
     * Compares and prints the result of one comparison between size n x n
     * matrices.
     *
     * @param n The side of the matrices.
     * @param threshold Threshold used in Strassen's multiplication.
     */
    private void compareMultiplications(int n, int threshold) {
        Matrix a;
        long startNaive;
        long endNaive;
        double timeNaive;
        long startStrassen;
        long endStrassen;
        double timeStrassen;

        a = Matrix.rand(n);

        System.out.format("%-6d", n);

        startNaive = System.currentTimeMillis();
        a.mulNaive(a);
        endNaive = System.currentTimeMillis();

        timeNaive = (endNaive - startNaive) * 1.0 / 1000;
        System.out.format("%-12.4f", timeNaive);

        startStrassen = System.currentTimeMillis();
        a.mulStrassen(a, threshold);
        endStrassen = System.currentTimeMillis();

        timeStrassen = (endStrassen - startStrassen) * 1.0 / 1000;
        System.out.format("%-16.4f", timeStrassen);

        System.out.format("%-12.4f\n", timeStrassen / timeNaive);
    }
}
