/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package matrix;

/**
 *
 * @author lasse
 */
public class Operations {

    private static void checkSizesEqual(Matrix a, Matrix b) {
        if (a.rows != b.rows || a.cols != b.cols) {
            throw new IllegalArgumentException("Matrices of different size!");
        }
    }

    public static Matrix add(Matrix a, Matrix b) {
        checkSizesEqual(a, b);
        Matrix result = new Matrix(a);
        for (int i = 0; i < result.rows; i++) {
            for (int j = 0; j < result.cols; j++) {
                result.data[i][j] += b.data[i][j];
            }
        }
        return result;
    }

    public static Matrix subtract(Matrix a, Matrix b) {
        checkSizesEqual(a, b);
        Matrix result = new Matrix(a);
        for (int i = 0; i < result.rows; i++) {
            for (int j = 0; j < result.cols; j++) {
                result.data[i][j] -= b.data[i][j];
            }
        }
        return result;
    }

    public static Matrix negate(Matrix m) {
        Matrix result = new Matrix(m);
        for (int i = 0; i < result.rows; i++) {
            for (int j = 0; j < result.cols; j++) {
                result.data[i][j] = -result.data[i][j];
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Matrix a = Matrix.ones(3, 4);
        Matrix b = Matrix.eye(3, 4);
        a.print();
        b.print();
        add(a, b).print();
    }
}
