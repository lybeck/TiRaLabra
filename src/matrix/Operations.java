/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package matrix;

/**
 *
 * @author lasse
 */
class Operations {

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

    static Matrix add(Matrix a, double k) {
        Matrix result = new Matrix(a);
        for (int i = 0; i < result.rows; i++) {
            for (int j = 0; j < result.cols; j++) {
                result.data[i][j] += k;
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

    static Matrix negate(Matrix m) {
        return scale(m, -1);
    }

    private static void checkMultiplicationSizes(Matrix m1, Matrix m2) throws IllegalArgumentException {
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

    private static int nextPowerOfTwoFrom(int a) {
        return (int) Math.round(Math.pow(2, Math.ceil(log2(a))));
    }

    private static double log2(double a) {
        return Math.log10(a) / Math.log10(2);
    }

    private static Matrix expand(Matrix m, int n) {
        if (m.rows == n && m.cols == n) {
            return m;
        }
        Matrix result = new Matrix(n);
        for (int i = 0; i < m.rows; i++) {
            System.arraycopy(m.data[i], 0, result.data[i], 0, m.cols);
        }
        return result;
    }

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

    private static Matrix strassen(Matrix m1, Matrix m2, int size, int minSize) {
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

    private static void checkIfSquare(Matrix m) {
        if (m.rows != m.cols) {
            throw new IllegalArgumentException("Matrix must be square!");
        }
    }

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
