/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.util.Scanner;
import matrix.Matrix;

/**
 *
 * @author llybeck
 */
class MatrixParser {

    Matrix parse(String string) {
        string = string.trim();
        String[] split = getSplit(string);
        int rows = split.length;
        int cols = countColumns(split);
        if (cols == -1) {
            System.out.println("Unparsable string!");
            return null;
        }
        double[][] m = new double[rows][cols];
        try {
            for (int i = 0; i < rows; i++) {
                split[i] = split[i].replaceAll(",", " ");
                Scanner row = new Scanner(split[i]);
                for (int j = 0; j < cols; j++) {
                    m[i][j] = row.nextDouble();
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Unparsable string!");
            return null;
        }
        return new Matrix(m);
    }

    private String[] getSplit(String string) {
        String[] split = string.split(";");
        for (int i = 0; i < split.length; i++) {
            split[i] = split[i].replaceAll("(,|\\s)+", " ");
            split[i] = split[i].trim();
        }
        return split;
    }

    private int countColumns(String[] split) {
        String regex = "\\s";
        int cols = split[0].split(regex).length;
        for (int i = 0; i < split.length; i++) {
            if (cols != split[i].split(regex).length) {
                return -1;
            }
        }
        return cols;
    }
}
