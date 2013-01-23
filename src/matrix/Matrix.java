/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package matrix;

/**
 * Class that represents a matrix.
 * 
 * @author lasse
 */
public class Matrix {

    protected double[][] data;
    protected final int rows, cols;
    
    private static final double largeValueLimit = 1000000;
    private static final double smallValueLimit = 1 / largeValueLimit;

    /**
     * Creates a zero-valued matrix of size n x n.
     * 
     * @param n Number of rows and columns in the matrix.
     */
    public Matrix(int n) {
        this(n, n);
    }

    /**
     * Creates a zero-valued matrix of size rows x cols.
     * 
     * @param rows Number of rows in the matrix.
     * @param cols Number of columns in the matrix.
     */
    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        data = new double[rows][cols];
    }

    /**
     * Creates a matrix of the input.
     * The matrix must have as many columns in every row.
     * 
     * @param matrix The matrix to be created.
     */
    public Matrix(double[][] matrix) {
        if (!validMatrix(matrix)) {
            throw new IllegalArgumentException("All columns in matrix should be of same size!");
        }
        data = copyOfMatrix(matrix);
        rows = data.length;
        cols = data[0].length;
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

    /**
     *
     * @param orig
     */
    public Matrix(Matrix orig) {
        this(orig.data);
    }

    /**
     *
     * @param rows
     * @param cols
     * @return
     */
    public static Matrix ones(int rows, int cols) {
        Matrix m = new Matrix(rows, cols);
        for (int i = 0; i < m.rows; i++) {
            for (int j = 0; j < m.cols; j++) {
                m.data[i][j] = 1;
            }
        }
        return m;
    }

    /**
     *
     * @param n
     * @return
     */
    public static Matrix ones(int n) {
        return ones(n, n);
    }

    /**
     *
     * @param rows
     * @param cols
     * @return
     */
    public static Matrix eye(int rows, int cols) {
        Matrix m = new Matrix(rows, cols);
        for (int i = 0; i < Math.min(m.rows, m.cols); i++) {
            m.data[i][i] = 1;
        }
        return m;
    }

    /**
     *
     * @param n
     * @return
     */
    public static Matrix eye(int n) {
        return eye(n, n);
    }

    /**
     *
     */
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

    /**
     *
     * @param i
     * @param j
     * @return
     */
    public double at(int i, int j) {
        return data[i][j];
    }

    /**
     *
     * @return
     */
    public int getRows() {
        return rows;
    }

    /**
     *
     * @return
     */
    public int getCols() {
        return cols;
    }

    /**
     *
     * @return
     */
    protected double[][] getData() {
        return data;
    }

    /**
     *
     * @param k
     * @return
     */
    public Matrix scale(double k) {
        return Operations.scale(this, k);
    }

    /**
     *
     * @param k
     * @return
     */
    public Matrix add(double k) {
        return Operations.add(this, k);
    }

    /**
     *
     * @param m
     * @return
     */
    public Matrix add(Matrix m) {
        return Operations.add(this, m);
    }

    /**
     *
     * @param m
     * @return
     */
    public Matrix sub(Matrix m) {
        return Operations.subtract(this, m);
    }

    /**
     *
     * @param m
     * @return
     */
    public Matrix mul(Matrix m) {
        return mulNaive(m);
    }

    /**
     *
     * @param m
     * @return
     */
    public Matrix mulNaive(Matrix m) {
        return Operations.mulNaive(this, m);
    }

    /**
     *
     * @param m
     * @return
     */
    public Matrix mulStrassen(Matrix m) {
        return Operations.mulStrassen(this, m);
    }

    /**
     *
     * @param e
     * @return
     */
    public Matrix pow(int e) {
        return Operations.pow(this, e);
    }

    /**
     *
     * @return
     */
    public Matrix transpose() {
        return Operations.transpose(this);
    }

    /**
     *
     * @return
     */
    public double det() {
        return Operations.det(this);
    }

    /**
     *
     * @return
     */
    public Matrix inv() {
        return Operations.inv(this);
    }
}
