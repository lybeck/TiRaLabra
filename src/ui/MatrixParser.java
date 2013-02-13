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

    Matrix parseString(String string) {
        string = string.trim();
        String[] split = getParseSplit(string);
        int rows = split.length;
        int cols = countColumns(split);
        if (cols == -1) {
            System.err.println("Unparsable string!");
            return null;
        }
        double[][] m = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            split[i] = split[i].replaceAll(",", " ");
            Scanner row = new Scanner(split[i]);
            for (int j = 0; j < cols; j++) {
                m[i][j] = ParseUtils.parseDouble(row.next());
            }
        }
        return new Matrix(m);
    }

    private String[] getParseSplit(String string) {
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

    Matrix zeros(String params) {
        int[] ints = ParseUtils.getParameterSplitAsInts(params);
        if (ints.length == 1) {
            return new Matrix(ints[0]);
        }
        if (ints.length == 2) {
            return new Matrix(ints[0], ints[1]);
        }
        ParseUtils.wrongNumberOfParametersInFunction("zeros");
        return null;
    }

    Matrix ones(String params) {
        int[] ints = ParseUtils.getParameterSplitAsInts(params);
        if (ints.length == 1) {
            return Matrix.ones(ints[0]);
        }
        if (ints.length == 2) {
            return Matrix.ones(ints[0], ints[1]);
        }
        ParseUtils.wrongNumberOfParametersInFunction("ones");
        return null;
    }

    Matrix eye(String params) {
        int[] ints = ParseUtils.getParameterSplitAsInts(params);
        if (ints.length == 1) {
            return Matrix.eye(ints[0]);
        }
        if (ints.length == 2) {
            return Matrix.eye(ints[0], ints[1]);
        }
        ParseUtils.wrongNumberOfParametersInFunction("eye");
        return null;
    }

    Matrix rand(String params) {
        int[] ints = ParseUtils.getParameterSplitAsInts(params);
        if (ints.length == 1) {
            return Matrix.rand(ints[0]);
        }
        if (ints.length == 2) {
            return Matrix.rand(ints[0], ints[1]);
        }
        ParseUtils.wrongNumberOfParametersInFunction("rand");
        return null;
    }

    Matrix randi(String params) {
        int[] ints = ParseUtils.getParameterSplitAsInts(params);
        if (ints.length == 2) {
            return Matrix.randi(ints[0], ints[1]);
        }
        if (ints.length == 3) {
            return Matrix.randi(ints[0], ints[1], ints[2]);
        }
        if (ints.length == 4) {
            return Matrix.randi(ints[0], ints[1], ints[2], ints[3]);
        }
        ParseUtils.wrongNumberOfParametersInFunction("rand");
        return null;
    }
}
