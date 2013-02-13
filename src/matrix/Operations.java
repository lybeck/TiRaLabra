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

    /**
     * Checks if the operation (m1 * m2) is possible, meaning that m1 must have
     * as many columns as m2 has rows. If it is not possible the method throws
     * an exception.
     *
     * @param m1 Matrix on the left side in the multiplication.
     * @param m2 Matrix on the right side in the multiplication.
     * @throws IllegalArgumentException if sizes do not match.
     */
    static void checkMultiplicationSizes(Matrix m1, Matrix m2) {
        if (m1.cols != m2.rows) {
            throw new IllegalArgumentException("In matrix multiplication the first matrix must have as many columns as the second has rows!");
        }
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
     * Multiplies the matrix m with the scalar k.
     *
     * @param m Matrix to be scaled.
     * @param k Scaling factor.
     * @return The matrix resulting from the operation k * m.
     */
    static Matrix scale(Matrix m, double k) {
        Matrix result = new Matrix(m);
        for (int i = 0; i < result.rows; i++) {
            for (int j = 0; j < result.cols; j++) {
                result.data[i][j] *= k;
            }
        }
        return result;
    }

    /**
     * Adds matrix b to matrix a.
     *
     * @param a Matrix to be added to.
     * @param b Matrix to be added.
     * @return The matrix resulting from the operation a + b.
     * @throws IllegalArgumentException if sizes do not match.
     */
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

    /**
     * Adds matrix b to matrix a.
     *
     * @param a Matrix to be subtracted from.
     * @param b Matrix to be subtracted.
     * @return The matrix resulting from the operation a - b.
     * @throws IllegalArgumentException if sizes do not match.
     */
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
     * Multiplies matrix m1 with matrix m2 using the naive multiplication
     * algorithm.
     *
     * @param m1 Matrix on the left matrix in the multiplication.
     * @param m2 Matrix on the right matrix in the multiplication.
     * @return The matrix resulting from the operation m1 * m2.
     * @throws IllegalArgumentException if sizes do not match.
     */
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

    /**
     * Multiplies matrix m1 with matrix m2 using Strassen's algorithm for matrix
     * multiplication.
     *
     * <p>
     *
     * The algorithm is implemented in the class {@link Strassen}.
     *
     * @param m1 Matrix on the left matrix in the multiplication.
     * @param m2 Matrix on the right matrix in the multiplication.
     * @return The matrix resulting from the operation m1 * m2.
     * @throws IllegalArgumentException if sizes do not match.
     */
    static Matrix mulStrassen(Matrix m1, Matrix m2) {
        return Strassen.mulStrassen(m1, m2);
    }

    /**
     * Multiplies matrix m1 with matrix m2 using Strassen's algorithm for matrix
     * multiplication, changing to the naive multiplication when the size drops
     * below the threshold.
     *
     * <p>
     *
     * The algorithm is implemented in the class {@link Strassen}.
     *
     * @param m1 Matrix on the left matrix in the multiplication.
     * @param m2 Matrix on the right matrix in the multiplication.
     * @param threshold Threshold size for chaning to the naive algorithm.
     * @return The matrix resulting from the operation m1 * m2.
     * @throws IllegalArgumentException if sizes do not match.
     */
    static Matrix mulStrassen(Matrix m1, Matrix m2, int threshold) {
        return Strassen.mulStrassen(m1, m2, threshold);
    }

    /**
     * Takes the power of the input matrix.
     *
     * @param m Matrix to be powered.
     * @param e Exponent, must be a non-negative integer.
     * @return The matrix resulting from the operation m^e.
     * @throws IllegalArgumentException exponent is negative.
     */
    static Matrix pow(Matrix m, int e) {
        if (e < 0) {
            throw new IllegalArgumentException("Power of matrix must be a non-negative integer!");
        }
        checkIfSquare(m);
        if (e == 0) {
            return Matrix.eye(m.rows);
        }
        return expBySquaring(m, e);
    }

    /**
     * Calculates the operation (m to the power e) using the exp-by-squaring
     * method. This is done in approximately O(n^3*log(e))-time.
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

    /**
     * Transposes the input matrix.
     *
     * @param m Matrix to tranposed.
     * @return The transpose of m.
     */
    static Matrix transpose(Matrix m) {
        Matrix result = new Matrix(m.cols, m.rows);
        for (int i = 0; i < result.rows; i++) {
            for (int j = 0; j < result.cols; j++) {
                result.data[i][j] = m.data[j][i];
            }
        }
        return result;
    }

    /**
     * Calculates the determinant of the input (square) matrix using
     * LU-decoposition.
     *
     * <p>
     *
     * The calculations are implemented in the class {@link LU}.
     *
     * @param m A square matrix.
     * @return The determinant value of m.
     * @throws IllegalArgumentException if matrix not square.
     */
    static double det(Matrix m) {
        checkIfSquare(m);
        return new LU(m).getDeterminant();
    }

    /**
     * Calculates the inverse matrix of the input (square) matrix.
     *
     * <p>
     *
     * The calculations are implemented in the class {@link LUSolver}.
     *
     * @param m A square matrix.
     * @return Inverse of m, if it is invertible, otherwise null.
     * @throws IllegalArgumentException if matrix not square.
     */
    static Matrix inv(Matrix m) {
        checkIfSquare(m);
        return LUSolver.calculateInverse(m);
    }

    /**
     * Calculates the LU-decomposition of the input (square) matrix.
     *
     * <p>
     *
     * The calculations are implemented in the class {@link LU}.
     *
     * @param m A square matrix.
     * @return The LU-decomposition of the input matrix.
     */
    static LU lu(Matrix m) {
        return new LU(m);
    }
}
