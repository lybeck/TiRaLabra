/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package matrix;

/**
 *
 * @author lasse
 */
public class Matrix {

    private double[][] data;
    private int rows, cols;
    private static final double largeValueLimit = 1000000;
    private static final double smallValueLimit = 1 / largeValueLimit;

    public Matrix(int n) {
        this.rows = this.cols = n;
    }

    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
    }

    public Matrix(double[][] matrix) {
        if (!validMatrix(matrix)) {
            throw new IllegalArgumentException("All columns in matrix should be of same size!");
        }
        rows = data.length;
        cols = data[0].length;
        data = copyOfMatrix(matrix);
    }

    private boolean validMatrix(double[][] matrix) {
        for (int i = 0; i < matrix.length - 1; i++) {
            if (matrix[i].length != matrix[i + 1].length) {
                return false;
            }
        }
        return true;
    }

    private double[][] copyOfMatrix(double[][] matrix) {
        double[][] newMatrix = new double[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            System.arraycopy(matrix[i], 0, newMatrix[i], 0, matrix[i].length);
        }
        return newMatrix;
    }

    public void print() {
        String format;
        if (containsLargeOrSmallValues()) {
            format = "%14.4e  ";
        } else {
            format = "%14.4f  ";
        }
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                System.out.format(format, at(i, j));
            }
            System.out.println();
        }
    }

    public double at(int i, int j) {
        return data[i][j];
    }

    private boolean containsLargeOrSmallValues() {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if (at(i, j) > largeValueLimit || at(i, j) < smallValueLimit) {
                    return true;
                }
            }
        }
        return false;
    }
}
