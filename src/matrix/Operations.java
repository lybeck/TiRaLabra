/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package matrix;

/**
 * The class is a library class of matrix operations. It is package private, and
 * contains only static methods used by the {@link Matrix} class.
 *
 * @author lasse
 */
class Operations {

    /**
     * Checks if the input matrices are of equal size, if not throws exception.
     *
     * @param a First matrix.
     * @param b Second matrix.
     * @throws IllegalArgumentException
     */
    private static void checkSizesEqual(Matrix a, Matrix b) {
        if (a.rows != b.rows || a.cols != b.cols) {
            throw new IllegalArgumentException("Matrices of different size!");
        }
    }

    static Matrix scale(Matrix m, double k) {
        Matrix result = new Matrix(m);
        for (int i = 0; i < result.rows; i++) {
            for (int j = 0; j < result.cols; j++) {
                result.data[i][j] *= k;
            }
        }
        return result;
    }

    static Matrix add(Matrix a, Matrix b) {
        checkSizesEqual(a, b);
        Matrix result = new Matrix(a);
        for (int i = 0; i < result.rows; i++) {
            for (int j = 0; j < result.cols; j++) {
                result.data[i][j] += b.data[i][j];
            }
        }
        return result;
    }

    static Matrix subtract(Matrix a, Matrix b) {
        checkSizesEqual(a, b);
        Matrix result = new Matrix(a);
        for (int i = 0; i < result.rows; i++) {
            for (int j = 0; j < result.cols; j++) {
                result.data[i][j] -= b.data[i][j];
            }
        }
        return result;
    }

    /**
     * Checks if the operation (m1 * m2) is possible, meaning that m1 must have
     * as many columns as m2 has rows. If it is not possible the method throws
     * an exception.
     *
     * @param m1 Matrix on the left side in the multiplication.
     * @param m2 Matrix on the right side in the multiplication.
     * @throws IllegalArgumentException
     */
    private static void checkMultiplicationSizes(Matrix m1, Matrix m2) {
        if (m1.cols != m2.rows) {
            throw new IllegalArgumentException("In matrix multiplication the first matrix must have as many columns as the second has rows!");
        }
    }

    static Matrix mulNaive(Matrix m1, Matrix m2) {
        checkMultiplicationSizes(m1, m2);
        Matrix result = new Matrix(m1.rows, m2.cols);
        for (int i = 0; i < result.rows; i++) {
            for (int j = 0; j < result.cols; j++) {
                for (int k = 0; k < m1.cols; k++) {
                    result.data[i][j] += m1.data[i][k] * m2.data[k][j];
                }
            }
        }
        return result;
    }

    static Matrix mulStrassen(Matrix m1, Matrix m2) {
        checkMultiplicationSizes(m1, m2);
        int max = Math.max(Math.max(m1.rows, m1.cols), Math.max(m2.rows, m2.cols));
        int newSize = nextPowerOfTwoFrom(max);
        Matrix result = strassen(expand(m1, newSize), expand(m2, newSize), 1);
        return shrink(result, m1.rows, m2.cols);
    }

    /**
     * Returns the next power of two from the number a.
     *
     * @param a Number to find the next power of two for.
     * @return The next power of two from a.
     */
    private static int nextPowerOfTwoFrom(int a) {
        return (int) Math.round(Math.pow(2, Math.ceil(log2(a))));
    }

    /**
     * Returns the 2-logarithm if a.
     *
     * @param a Number to be taken the 2-logarithm of.
     * @return The 2-logarithm of a.
     */
    private static double log2(double a) {
        return Math.log10(a) / Math.log10(2);
    }

    /**
     * Returns a matrix having the same elements as the input matrix, but
     * expanded to size n x n. The new elements are padded with zeros.
     *
     * @param m Matrix to be expanded.
     * @param n New side of the matrix.
     * @return An n x n matrix containing the original values of the matrix,
     * padded with zeros. If the matrix was already of size n x n the original
     * instance of the matrix is returned.
     */
    private static Matrix expand(Matrix m, int n) {
        if (m.rows == n && m.cols == n) {
            return new Matrix(m);
        }
        Matrix result = new Matrix(n);
        for (int i = 0; i < m.rows; i++) {
            System.arraycopy(m.data[i], 0, result.data[i], 0, m.cols);
        }
        return result;
    }

    /**
     * Shrinks the matrix to the size rows x cols. The elements left outside of
     * this range are discarded.
     *
     * @param m Matrix to be shrunk.
     * @param rows Rows in the resulting matrix.
     * @param cols Columns in the resulting matrix.
     * @return A rows x cols matrix containing the values of the original matrix
     * at indeces in that range. All other elements are discarded. If the matrix
     * is already of the specified size the original instance of the matrix is
     * returned.
     */
    private static Matrix shrink(Matrix m, int rows, int cols) {
        if (m.rows == rows && m.cols == cols) {
            return m;
        }
        Matrix result = new Matrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            System.arraycopy(m.data[i], 0, result.data[i], 0, cols);
        }
        return result;
    }

    /**
     * Strassen's algorithm of matrix multiplication. <p> This method is used
     * recursively until the threshold for the minimum size is reached, and then
     * passed to the naive multiplication algorithm. If Strassen's algorithm is
     * wanted to be used all the way the threshold should be set as 1. <br> The
     * matrices passed to the method must have all sizes the same power-of-two.
     * This is not checked by the method to ensure good performance, but may
     * cause unexpected behaviour if not satisfied.
     *
     * @param m1 The left matrix in the multiplication.
     * @param m2 The right matrix in the multiplication.
     * @param threshold The size after which the multiplication is passed to the
     * naive algorithm. Set to 1 if Strassen's algorithm is wanted to be used
     * all the way.
     * @return The resulting matrix of the operation (m1 * m2).
     */
    private static Matrix strassen(Matrix m1, Matrix m2, int threshold) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    static Matrix pow(Matrix m, int e) {
        if (e < 0) {
            throw new IllegalArgumentException("Power of matrix must be a positive integer!");
        }
        checkIfSquare(m);
        if (e == 0) {
            return Matrix.eye(m.rows);
        }
        return expBySquaring(m, e);
    }

    /**
     * Checks if the matrix is a square matrix, if not, throw an exception.
     *
     * @param m Matrix to be checked.
     * @throws IllegalArgumentException
     */
    private static void checkIfSquare(Matrix m) {
        if (m.rows != m.cols) {
            throw new IllegalArgumentException("Matrix must be square!");
        }
    }

    /**
     * Calculates the operation (m to the power e) using the exp-by-squaring
     * method. This is done in approximately O(n3*log(e))-time.
     *
     * @param m Matrix to be powered.
     * @param e The (non-negative) exponent.
     * @return The resulting matrix of the operation (m to the power e).
     */
    private static Matrix expBySquaring(Matrix m, int e) {
        if (e == 1) {
            return m;
        }
        if (e % 2 == 0) {
            return expBySquaring(m.mul(m), e / 2);
        }
        return m.mul(expBySquaring(m.mul(m), (e - 1) / 2));
    }

    static Matrix transpose(Matrix m) {
        Matrix result = new Matrix(m.cols, m.rows);
        for (int i = 0; i < result.rows; i++) {
            for (int j = 0; j < result.cols; j++) {
                result.data[i][j] = m.data[j][i];
            }
        }
        return result;
    }

    static double det(Matrix m) {
        checkIfSquare(m);
        throw new UnsupportedOperationException("Not yet implemented");
    }

    static Matrix inv(Matrix m) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
