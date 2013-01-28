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
        Matrix result = strassen(expand(m1, newSize), expand(m2, newSize), newSize, 1);
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
     * @param a The left matrix in the multiplication.
     * @param b The right matrix in the multiplication.
     * @param threshold The size after which the multiplication is passed to the
     * naive algorithm. Set to 1 if Strassen's algorithm is wanted to be used
     * all the way.
     * @return The resulting matrix of the operation (m1 * m2).
     */
    private static Matrix strassen(Matrix a, Matrix b, int size, int threshold) {
        if (size <= threshold) {
            return a.mulNaive(b);
        }

        int newSize = size / 2;

        // Dividing matrix into four submatrices
        Matrix a11, a12, a21, a22;
        Matrix b11, b12, b21, b22;
        a11 = subMatrix(a, 0, newSize, 0, newSize);
        a12 = subMatrix(a, 0, newSize, newSize, size);
        a21 = subMatrix(a, newSize, size, 0, newSize);
        a22 = subMatrix(a, newSize, size, newSize, size);
        b11 = subMatrix(b, 0, newSize, 0, newSize);
        b12 = subMatrix(b, 0, newSize, newSize, size);
        b21 = subMatrix(b, newSize, size, 0, newSize);
        b22 = subMatrix(b, newSize, size, newSize, size);


        // Create help matrices
        Matrix m1, m2, m3, m4, m5, m6, m7;
        // m1 = (a11 + a22) * (b11 + b22)
        m1 = strassen(a11.add(a22), b11.add(b22), newSize, threshold);
        // m2 = (a21 + a22) * b11
        m2 = strassen(a21.add(a22), b11, newSize, threshold);
        // m3 = a11 * (b12 - b22)
        m3 = strassen(a11, b12.sub(b22), newSize, threshold);
        // m4 = a22 * (b21 - b11)
        m4 = strassen(a22, b21.sub(b11), newSize, threshold);
        // m5 = (a11 + a12) * b22
        m5 = strassen(a11.add(a12), b22, newSize, threshold);
        // m6 = (a21 - a11) * (b11 + b12)
        m6 = strassen(a21.sub(a11), b11.add(b12), newSize, threshold);
        // m7 = (a12 - a22) * (b21 + b22)
        m7 = strassen(a12.sub(a22), b21.add(b22), newSize, threshold);


        // Create submatrices for the result matrix c
        Matrix c11, c12, c21, c22;
        // c11 = m1 + m4 - m5 + m7
        c11 = m1.add(m4).sub(m5).add(m7);
        // c12 = m3 + m5
        c12 = m3.add(m5);
        // c21 = m2 + m4
        c21 = m2.add(m4);
        // c22 = m1 - m2 + m3 + m6
        c22 = m1.sub(m2).add(m3).add(m6);

        return createMatrixFromBlocks(c11, c12, c21, c22, newSize);
    }

    /**
     * Creates a submatrix of m, with rows [startRow, endRow[ and columns
     * [startCol, endCol[.
     *
     * @param m Matrix to get submatrix from.
     * @param startRow First row (inclusive).
     * @param endRow Last row (exclusive).
     * @param startCol First column (inclusive).
     * @param endCol Last column (exclusive).
     * @return The submatrix of m, starting from startRow (inclusive) to endRow
     * (exclusive), with columns startRow (inclusive), to endRow (exclusive).
     */
    private static Matrix subMatrix(Matrix m, int startRow, int endRow, int startCol, int endCol) {
        int rows = endRow - startRow;
        int cols = endCol - startCol;
        Matrix sub = new Matrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                sub.data[i][j] = m.data[i + startRow][j + startCol];
            }
        }
        return sub;
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
     * Creates matrix from four blockmatrices. All the matrices should be of the
     * form n x n, and the resulting matrix will be of size 2n x 2n.
     *
     * @param c11 Upper left block of the matrix.
     * @param c12 Upper right block of the matrix.
     * @param c21 Lower left block of the matrix.
     * @param c22 Lower right block of the matrix.
     * @param n The size of the blockmatrices.
     * @return The matrix constructed of the block. The returned matrix will be
     * of size 2n x 2n.
     */
    private static Matrix createMatrixFromBlocks(Matrix c11, Matrix c12, Matrix c21, Matrix c22, int n) {
        Matrix c = new Matrix(2 * n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                c.data[i][j] = c11.data[i][j];
                c.data[i][j + n] = c12.data[i][j];
                c.data[i + n][j] = c21.data[i][j];
                c.data[i + n][j + n] = c22.data[i][j];
            }
        }
        return c;
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

    public static void main(String[] args) {
        Matrix a = new Matrix(5, 6);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 6; j++) {
                a.data[i][j] = i + j + 1;
            }
        }
//        Matrix b = expand(a, nextPowerOfTwoFrom(Math.max(5, 6)));
//        b.print();
//        
//        subMatrix(b, 4, 8, 0, 4).print();
        
        Matrix c11, c12, c21, c22;
        c11 = Matrix.ones(4);
        c12 = Matrix.ones(4).scale(2);
        c21 = Matrix.ones(4).scale(3);
        c22 = Matrix.ones(4).scale(4);
        
        Matrix c = createMatrixFromBlocks(c11, c12, c21, c22, 4);
        
        c.print();
    }
}
