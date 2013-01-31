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
    static void checkMultiplicationSizes(Matrix m1, Matrix m2) {
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
        return Strassen.mulStrassen(m1, m2);
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
