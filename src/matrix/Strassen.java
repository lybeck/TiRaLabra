/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package matrix;

/**
 * The class is a library class containing the Strassen algorithm for matrix
 * multiplication.
 *
 * @author lasse
 */
class Strassen {

    static Matrix mulStrassen(Matrix m1, Matrix m2) {
        return mulStrassen(m1, m2, 1);
    }

    static Matrix mulStrassen(Matrix m1, Matrix m2, int threshold) {
        if (threshold < 1) {
            throw new IllegalArgumentException("Threshold in Strassen's algorthm must be a positive integer!");
        }
        Operations.checkMultiplicationSizes(m1, m2);
        int max = Math.max(Math.max(m1.rows, m1.cols), Math.max(m2.rows, m2.cols));
        int newSize = nextPowerOfTwoFrom(max);
        double[][] result = strassen(expand(m1, newSize), expand(m2, newSize), newSize, threshold);
        return new Matrix(shrink(result, m1.rows, m2.cols));

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
    private static double[][] expand(Matrix m, int n) {
        if (m.rows == n && m.cols == n) {
            return new Matrix(m).data;
        }
        double[][] result = new double[n][n];
        for (int i = 0; i < m.rows; i++) {
            System.arraycopy(m.data[i], 0, result[i], 0, m.cols);
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
    private static double[][] shrink(double[][] m, int rows, int cols) {
        if (m.length == rows && m[0].length == cols) {
            return m;
        }
        double[][] result = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            System.arraycopy(m[i], 0, result[i], 0, cols);
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
    private static double[][] strassen(double[][] a, double[][] b, int size, int threshold) {
        if (size <= threshold) {
            return Operations.mulNaive(new Matrix(a), new Matrix(b)).getDataCopy();
        }

        int newSize = size / 2;

        // Dividing matrix into four submatrices
        double[][] a11, a12, a21, a22;
        double[][] b11, b12, b21, b22;
        a11 = subMatrix(a, 0, newSize, 0, newSize);
        a12 = subMatrix(a, 0, newSize, newSize, size);
        a21 = subMatrix(a, newSize, size, 0, newSize);
        a22 = subMatrix(a, newSize, size, newSize, size);
        b11 = subMatrix(b, 0, newSize, 0, newSize);
        b12 = subMatrix(b, 0, newSize, newSize, size);
        b21 = subMatrix(b, newSize, size, 0, newSize);
        b22 = subMatrix(b, newSize, size, newSize, size);


        // Create help matrices
        double[][] m1, m2, m3, m4, m5, m6, m7;
        // m1 = (a11 + a22) * (b11 + b22)
        m1 = strassen(add(a11, a22), add(b11, b22), newSize, threshold);
        // m2 = (a21 + a22) * b11
        m2 = strassen(add(a21, a22), b11, newSize, threshold);
        // m3 = a11 * (b12 - b22)
        m3 = strassen(a11, sub(b12, b22), newSize, threshold);
        // m4 = a22 * (b21 - b11)
        m4 = strassen(a22, sub(b21, b11), newSize, threshold);
        // m5 = (a11 + a12) * b22
        m5 = strassen(add(a11, a12), b22, newSize, threshold);
        // m6 = (a21 - a11) * (b11 + b12)
        m6 = strassen(sub(a21, a11), add(b11, b12), newSize, threshold);
        // m7 = (a12 - a22) * (b21 + b22)
        m7 = strassen(sub(a12, a22), add(b21, b22), newSize, threshold);


        // Create submatrices for the result matrix c
        double[][] c11, c12, c21, c22;
        // c11 = m1 + m4 - m5 + m7
        c11 = add(sub(add(m1, m4), m5), m7);
        // c12 = m3 + m5
        c12 = add(m3, m5);
        // c21 = m2 + m4
        c21 = add(m2, m4);
        // c22 = m1 - m2 + m3 + m6
        c22 = add(add(sub(m1, m2), m3), m6);

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
    private static double[][] subMatrix(double[][] m, int startRow, int endRow, int startCol, int endCol) {
        int rows = endRow - startRow;
        int cols = endCol - startCol;
        double[][] sub = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                sub[i][j] = m[i + startRow][j + startCol];
            }
        }
        return sub;
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
    private static double[][] createMatrixFromBlocks(double[][] c11, double[][] c12,
            double[][] c21, double[][] c22, int n) {
        
        double[][] c = new double[2 * n][2 * n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                c[i][j] = c11[i][j];
                c[i][j + n] = c12[i][j];
                c[i + n][j] = c21[i][j];
                c[i + n][j + n] = c22[i][j];
            }
        }
        return c;
    }

    private static double[][] add(double[][] a, double[][] b) {
        double[][] result = new double[a.length][a[0].length];
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[0].length; j++) {
                result[i][j] = a[i][j] + b[i][j];
            }
        }
        return result;
    }

    private static double[][] sub(double[][] a, double[][] b) {
        double[][] result = new double[a.length][a[0].length];
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[0].length; j++) {
                result[i][j] = a[i][j] - b[i][j];
            }
        }
        return result;
    }
}
