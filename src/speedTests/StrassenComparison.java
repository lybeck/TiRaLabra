/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package speedTests;

import matrix.Matrix;

/**
 *
 * @author Lasse
 */
public class StrassenComparison {

    public static void main(String[] args) {
        compareMultiplication(1000,1000,100);        
    }

    private static void compareMultiplication(int minSize, int maxSize, int dSize) {
        Matrix a;
        long startNaive, startStrassen, endNaive, endStrassen;
        double timeNaive, timeStrassen;
        System.out.format("%-6s%-12s%-12s\n", "n:  ", "Naive:", "Strassen:");
        for (int i = minSize; i <= maxSize; i += dSize) {
            a = Matrix.rand(i);
            
            startNaive = System.currentTimeMillis();
            a.mulNaive(a);
            endNaive = System.currentTimeMillis();
            timeNaive = (endNaive - startNaive) * 1.0 / 1000;
            
            startStrassen = System.currentTimeMillis();
            a.mulStrassen(a);
            endStrassen = System.currentTimeMillis();
            timeStrassen = (endStrassen - startStrassen) * 1.0 / 1000;
            
            System.out.format("%-6d%-12.4f%-12.4f\n", i, timeNaive, timeStrassen);
        }
        
//        Matrix b = Matrix.rand(1000);
//        long start = System.currentTimeMillis();
//        b.mulNaive(b);
//        long end = System.currentTimeMillis();
//        System.out.println((end - start) * 1.0 / 1000);
    }
}
