/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package matrix;

import java.util.Random;

/**
 * Class that represents a matrix.
 *
 * <p>
 *
 * The matrix operations are included in this class for easy usage. If m is a
 * Matrix instance, all operations can be accessed by calling
 * m.<i>operation</i>(). The operations are implemented in the
 * {@link Operations} class.
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
     * Creates a 1x1 matrix of the input number.
     *
     * @param d The only element in the matrix.
     */
    public Matrix(double d) {
        this.rows = this.cols = 1;
        data = new double[][]{{d}};
    }

    /**
     * Creates a matrix of the input. The matrix must have as many columns in
     * every row.
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

    /**
     * Checks if matrix is valid, is a sense that it has as many elements in
     * each row. Returns true if it is, otherwise false.
     *
     * @param matrix Matrix to be checked.
     * @return True if matrix is valid, otherwise false.
     */
    private boolean validMatrix(double[][] matrix) {
        for (int i = 0; i < matrix.length - 1; i++) {
            if (matrix[i].length != matrix[i + 1].length) {
                return false;
            }
        }
        return true;
    }

    /**
     * Creates a copy of the matrix, by declaring a new one and copying all the
     * values to it.
     *
     * @param matrix Matrix to copied.
     * @return A true copy of the input matrix.
     */
    private double[][] copyOfMatrix(double[][] matrix) {
        double[][] newMatrix = new double[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            System.arraycopy(matrix[i], 0, newMatrix[i], 0, matrix[i].length);
        }
        return newMatrix;
    }

    /**
     * Creates a real copy of the given matrix.
     *
     * @param orig Matrix to be copied.
     */
    public Matrix(Matrix orig) {
        this(orig.data);
    }

    /**
     * Creates a rows x cols matrix with every element having the value 1.
     *
     * @param rows Number of rows in the matrix.
     * @param cols Number of columns in the matrix.
     * @return A rows x cols matrix with every element having the value 1.
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
     * Creates a n x n matrix with every element having the value 1.
     *
     * @param n Number of rows and columns in the matrix.
     * @return An n x n matrix with every element having the value 1.
     */
    public static Matrix ones(int n) {
        return ones(n, n);
    }

    /**
     * Creates an identity matrix of size rows x cols.
     *
     * @param rows Number of rows in the matrix.
     * @param cols Number of columns in the matrix.
     * @return A rows x cols identity matrix.
     */
    public static Matrix eye(int rows, int cols) {
        Matrix m = new Matrix(rows, cols);
        for (int i = 0; i < Math.min(m.rows, m.cols); i++) {
            m.data[i][i] = 1;
        }
        return m;
    }

    /**
     * Creates an identity matrix of size n x n.
     *
     * @param n Number of rows and columns in the matrix.
     * @return An n x n identity matrix.
     */
    public static Matrix eye(int n) {
        return eye(n, n);
    }

    /**
     * Creates a rows x cols matrix with random double values between 0 and 1,
     * using the specific random number generator.
     *
     * @param rows Numer of rows in the matrix.
     * @param cols Number of columns in the matrix.
     * @param rand Random number generator to be used.
     * @return A rows x cols matrix with random double values between 0 and 1.
     */
    private static Matrix rand(int rows, int cols, Random rand) {
        Matrix m = new Matrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                m.data[i][j] = rand.nextDouble();
            }
        }
        return m;
    }

    /**
     * Creates a rows x cols matrix with random double values between 0 and 1.
     *
     * @param rows Numer of rows in the matrix.
     * @param cols Number of columns in the matrix.
     * @return A rows x cols matrix with random double values between 0 and 1.
     */
    static Matrix rand(int rows, int cols, long seed) {
        return rand(rows, cols, new Random(seed));
    }

    /**
     * Creates a rows x cols matrix with random double values between 0 and 1,
     * using the specified seed to seed the random number generator.
     *
     * @param rows Numer of rows in the matrix.
     * @param cols Number of columns in the matrix.
     * @return A rows x cols matrix with random double values between 0 and 1.
     */
    public static Matrix rand(int rows, int cols) {
        return rand(rows, cols, new Random());
    }

    /**
     * Creates an n x n matrix with random double values between 0 and 1.
     *
     * @param n Numer of rows and columns in the matrix.
     * @return An n x n matrix with random double values between 0 and 1.
     */
    public static Matrix rand(int n) {
        return rand(n, n);
    }

    /**
     * Creates a rows x cols matrix with random integer values from the interval
     * [imin, imax]. The method also takes a Random-instance as a parameter,
     * which is to be used to generate the values in the matrix.
     *
     * @param imin Lower bound for the values.
     * @param imax Upper bound for the values.
     * @param rows Number of rows in the matrix.
     * @param cols Number of columns in the matrix.
     * @param rand Random number generator to be used.
     * @return A rows x cols matrix with random integer values from the interval
     * [imin, imax].
     * @throws IllegalArgumentException, if imin &gt imax.
     */
    private static Matrix randi(int imin, int imax, int rows, int cols, Random rand) {
        if (imin > imax) {
            throw new IllegalArgumentException("Should be imin <= imax!");
        }
        Matrix m = new Matrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                m.data[i][j] = rand.nextInt(imax - imin + 1) + imin;
            }
        }
        return m;
    }

    /**
     * Creates a rows x cols matrix with random integer values from the interval
     * [imin, imax], using 'seed' to seed the random numbers. Giving the same
     * seed generates the same random numbers for the matrix. If the method is
     * wanted to be seeded automatically, use {@link #randi(int, int, int, int)
     * } instead.
     *
     * @param imin Lower bound for the values.
     * @param imax Upper bound for the values.
     * @param rows Number of rows in the matrix.
     * @param cols Number of columns in the matrix.
     * @param seed Seed for the random number generator.
     * @return A rows x cols matrix with random integer values from the interval
     * [imin, imax].
     * @throws IllegalArgumentException, if imin &gt imax.
     */
    static Matrix randi(int imin, int imax, int rows, int cols, long seed) {
        return randi(imin, imax, rows, cols, new Random(seed));
    }

    /**
     * Creates a rows x cols matrix with random integer values from the interval
     * [imin, imax].
     *
     * @param imin Lower bound for the values.
     * @param imax Upper bound for the values.
     * @param rows Number of rows in the matrix.
     * @param cols Number of columns in the matrix.
     * @return A rows x cols matrix with random integer values from the interval
     * [imin, imax].
     * @throws IllegalArgumentException, if imin &gt imax.
     */
    public static Matrix randi(int imin, int imax, int rows, int cols) {
        return randi(imin, imax, rows, cols, new Random());
    }

    /**
     * Creates a rows x cols matrix with random integer values from the interval
     * [0, imax].
     *
     * @param imax Upper bound for the values.
     * @param rows Number of rows in the matrix.
     * @param cols Number of columns in the matrix.
     * @return A rows x cols matrix with random integer values from the interval
     * [0, imax].
     * @throws IllegalArgumentException, if imax &lt 0.
     */
    public static Matrix randi(int imax, int rows, int cols) {
        return randi(0, imax, rows, cols);
    }

    /**
     * Creates an n x n matrix with random integer values from the interval [0,
     * imax].
     *
     * @param imax Upper bound for the values.
     * @param n Number of rows and columns in the matrix.
     * @return An n x n matrix with random integer values from the interval [0,
     * imax].
     * @throws IllegalArgumentException, if imax &lt 0.
     */
    public static Matrix randi(int imax, int n) {
        return randi(imax, n, n);
    }

    /**
     * Prints the matrix. <p> The method prints the matrix to the console. It
     * also checks if the matrix contains very small or large values, and if it
     * does, the elements are shown in e-notation, otherwise they are shown in
     * normal floating point notation.
     */
    public void print() {
        String formatString;
        if (containsLargeOrSmallValues()) {
            formatString = "%10.4e  ";
        } else {
            formatString = "%14.4f  ";
        }
        System.out.println();
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                System.out.format(formatString, at(i, j));
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Checks if this matrix contains large or small values in its elements.
     *
     * @return True, if matrix contains large or small values, otherwise false.
     */
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
     * Returns the value of the element at the index (i,j).
     *
     * @param i Rows index.
     * @param j Column index.
     * @return Value of the element at the index (i,j).
     */
    public double at(int i, int j) {
        return data[i][j];
    }

    /**
     * Returns the number of rows in the matrix.
     *
     * @return The number of rows in the matrix.
     */
    public int getRows() {
        return rows;
    }

    /**
     * Returns the number of columns in the matrix.
     *
     * @return The number of columns in the matrix.
     */
    public int getCols() {
        return cols;
    }

    /**
     * Returns the double[][] of data the matrix represents. <p> The data is the
     * same double[][] used by the matrix, meaning that changing the values in
     * the returned double[][] changes the matrix itself. If the values are
     * needed to be altered without changing the instance of the matrix, use
     * {@link #getDataCopy()}.
     *
     * @return Data represented by the matrix.
     */
    protected double[][] getData() {
        return data;
    }

    /**
     * Returns a copy of the double[][] of data the matrix represents.
     *
     * @return A copy of the data represented by the matrix.
     */
    public double[][] getDataCopy() {
        return copyOfMatrix(data);
    }

    /**
     * Multiplies the matrix with a scalar.
     *
     * @param k Scalar to be multiplied to the matrix.
     * @return The resulting matrix of the scalar multiplication (k * this).
     */
    public Matrix scale(double k) {
        return Operations.scale(this, k);
    }

    /**
     * Adds the given matrix to this matrix.
     *
     * @param m Matrix to be added.
     * @return The resulting matrix of (this + m).
     */
    public Matrix add(Matrix m) {
        return Operations.add(this, m);
    }

    /**
     * Subtracts the given matrix from this matrix.
     *
     * @param m Matrix to be subtracted.
     * @return The resulting matrix of (this - m).
     */
    public Matrix sub(Matrix m) {
        return Operations.subtract(this, m);
    }

    /**
     * Multiplies this matrix with the given matrix. <p> This method uses a
     * combination of Strassen's algorithm and the naive multiplication for the
     * task. If it is important to use a specific method to multiply the
     * matrices use {@link #mulNaive(matrix.Matrix)} or
     * {@link #mulStrassen(matrix.Matrix)} instead.
     *
     * @param m Matrix to be multiplied with.
     * @return The resulting matrix of the operation (this * m).
     */
    public Matrix mul(Matrix m) {
        return mulNaive(m);
    }

    /**
     * Multiplies this matrix with the given matrix using the naive algorithm.
     *
     * @param m Matrix to be multiplied with.
     * @return The resulting matrix of the operation (this * m).
     */
    public Matrix mulNaive(Matrix m) {
        return Operations.mulNaive(this, m);
    }

    /**
     * Multiplies this matrix with the given matrix using Strassen's algorithm.
     *
     * @param m Matrix to be multiplied with.
     * @return The resulting matrix of the operation (this * m).
     */
    public Matrix mulStrassen(Matrix m) {
        return Operations.mulStrassen(this, m);
    }

    /**
     * Uses Strassen's algorithm to multiply this matrix with the matrix m,
     * until the recursion has reached size threshold, and naive multiplication
     * from there on.
     *
     * @param m Matrix to be multiplied with.
     * @param threshold Staring size for naive multiplication.
     * @return The resulting matrix of the operation (this * m).
     */
    public Matrix mulStrassen(Matrix m, int threshold) {
        return Operations.mulStrassen(this, m, threshold);
    }

    /**
     * Raises the matrix to the power e.
     *
     * @param e The (non-negative) power the matrix is raised to.
     * @return Resulting matrix of the operation (this to the power e).
     */
    public Matrix pow(int e) {
        return Operations.pow(this, e);
    }

    /**
     * Transposes the matrix.
     *
     * @return The transpose of this.
     */
    public Matrix transpose() {
        return Operations.transpose(this);
    }

    /**
     * Returns the determinant of this matrix.
     *
     * @return The determinant of this matrix.
     */
    public double det() {
        return Operations.det(this);
    }

    /**
     * Inverts this matrix, if possible.
     *
     * @return The inverse of this matrix, if it exists, otherwise null.
     */
    public Matrix inv() {
        return Operations.inv(this);
    }

    /**
     * Decomposes the matrix this into components P, L and U satisfying P*this =
     * L*U, where L is a lower diagonal matrix, U is an upper triangular matrix
     * and P is a permutation matrix.
     *
     * @return The LU-decomposition of this matrix.
     */
    public LU lu() {
        return Operations.lu(this);
    }
}
