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

    protected double[][] data;
    protected final int rows, cols;
    private static final double largeValueLimit = 1000000;
    private static final double smallValueLimit = 1 / largeValueLimit;

    public Matrix(int n) {
        this(n, n);
    }

    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        data = new double[rows][cols];
    }

    public Matrix(double[][] matrix) {
        if (!validMatrix(matrix)) {
            throw new IllegalArgumentException("All columns in matrix should be of same size!");
        }
        data = copyOfMatrix(matrix);
        rows = data.length;
        cols = data[0].length;
    }

    public Matrix(Matrix orig) {
        this(orig.data);
    }

    public static Matrix ones(int rows, int cols) {
        Matrix m = new Matrix(rows, cols);
        for (int i = 0; i < m.rows; i++) {
            for (int j = 0; j < m.cols; j++) {
                m.data[i][j] = 1;
            }
        }
        return m;
    }

    public static Matrix ones(int n) {
        return ones(n, n);
    }

    public static Matrix eye(int rows, int cols) {
        Matrix m = new Matrix(rows, cols);
        for (int i = 0; i < Math.min(m.rows, m.cols); i++) {
            m.data[i][i] = 1;
        }
        return m;
    }
    
    public static Matrix eye(int n){
        return eye(n, n);
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
        String formatString;
        if (containsLargeOrSmallValues()) {
            formatString = "%10.4e  ";
        } else {
            formatString = "%14.4f  ";
        }
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                System.out.format(formatString, at(i, j));
            }
            System.out.println();
        }
        System.out.println();
    }

    private boolean containsLargeOrSmallValues() {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if (Math.abs(at(i, j)) > largeValueLimit
                        || (Math.abs(at(i, j)) < smallValueLimit && Math.abs(at(i, j)) > 0)) {
                    return true;
                }
            }
        }
        return false;
    }

    public double at(int i, int j) {
        return data[i][j];
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    protected double[][] getData() {
        return data;
    }
    
    public Matrix add(Matrix m){
        return Operations.add(this, m);
    }
    
    public Matrix sub(Matrix m) {
        return Operations.subtract(this, m);
    }
}
