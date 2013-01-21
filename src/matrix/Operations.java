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
        throw new UnsupportedOperationException("Not yet implemented");
    }

    static Matrix pow(Matrix m, int e) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    static Matrix transpose(Matrix m) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    static double det(Matrix m) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    static Matrix inv(Matrix m) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    static Matrix pinv(Matrix m) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
