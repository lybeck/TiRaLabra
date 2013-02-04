/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package matrix;

/**
 * This class contains two matrices representing the LU-decomposition of a
 * matrix.
 *
 * @author Lasse
 */
public class LU {

    private Matrix l, u, p, permL;
    private int elementaryPermutations;
    private double determinant;

    /**
     * Generates an LU-decomposition of the input matrix.
     *
     * @param m Matrix to be decomposed.
     * @throws IllegalArgumentException If matrix is not square.
     */
    LU(Matrix m) {
        decompose(m);
        calculateDeterminant();
    }

    /**
     * Returns the L-matrix in the LU-decompostion. The L-matrix is a lower
     * triangular matrix.
     *
     * @return The lower triangular matrix of the decomposition.
     */
    public Matrix getL() {
        return l;
    }

    /**
     * Returns the permutated lower diagonal matrix. The result is of the result
     * of the multiplication transpose(P)*L, where P is the permutation matrix
     * of decomposition.
     *
     * @return The permutated lower triangular matrix of the decomposition.
     */
    public Matrix getPermutatedL() {
        return permL;
    }

    /**
     * Returns the upper triangular matrix of the decomposition.
     *
     * @return Upper triangular matrix from the decomposition.
     */
    public Matrix getU() {
        return u;
    }

    /**
     * Returns the permutation matrix of the decomposition.
     *
     * @return The permutation matrix of the decomposition.
     */
    public Matrix getP() {
        return p;
    }

    /**
     * Returns the determinant of the matrix represented by the
     * LU-decomposition. The determinant is calculated by multiplying the
     * determinant of the upper triangular matrix (the product of the diagonal
     * elements) with the determinant of permutation matrix (being always 1 or
     * -1). The lower triangular matrix has always determinant 1, and needs not
     * be taken into account.
     *
     * @return Determinant of the represented matrix.
     */
    double getDeterminant() {
        return determinant;
    }

    /**
     * Decomposes the matrix A into components P, L and U satisfying P*A = L*U,
     * where L is a lower diagonal matrix, U is an upper triangular matrix and P
     * is a permutation matrix.
     *
     * @param m Matrix to be decomposed.
     */
    private void decompose(Matrix m) {
        if (m.rows != m.cols) {
            throw new IllegalArgumentException("Matrix must be square!");
        }

        elementaryPermutations = 0;

        int n = m.rows;
        l = Matrix.eye(n);
        u = new Matrix(m);
        p = Matrix.eye(n);

        for (int i = 0; i < n; i++) {
            if (u.data[i][i] == 0) {
                if (!swapAwayZeroElement(i)) {
                    continue;
                }
            }
            for (int j = i + 1; j < n; j++) {
                double l_ji = -u.data[j][i] / u.data[i][i];
                addMulRow(u, i, j, l_ji);
                l.data[j][i] = -l_ji;
            }
        }
        permL = p.transpose().mul(l);
    }

    /**
     * Adds the row i multiplied with c into the row j in matrix m.
     *
     * @param m Matrix to be used.
     * @param i Row to be added.
     * @param j Row to be added to.
     * @param c Constant the added row should be multiplied with before adding.
     */
    private void addMulRow(Matrix m, int i, int j, double c) {
        for (int k = 0; k < m.cols; k++) {
            m.data[j][k] += m.data[i][k] * c;
        }
    }

    /**
     * Swaps away the row i to a lower row in the matrix, if possible without
     * getting a 0 element to index (i,i).
     *
     * @param i Row to be swapped.
     * @return True, if the swap was possible, otherwise false.
     */
    private boolean swapAwayZeroElement(int i) {
        for (int j = i + 1; j < u.rows; j++) {
            if (u.data[j][i] != 0) {
                swapRows(u, i, j);
                swapRows(p, i, j);
                ++elementaryPermutations;
                return true;
            }
        }
        return false;
    }

    /**
     * Swaps rows i and j in matrix m.
     *
     * @param m Matrix, whose rows should be swapped.
     * @param i First row to be swapped.
     * @param j Second row to be swapped.
     */
    private void swapRows(Matrix m, int i, int j) {
        double[] temp = new double[m.cols];
        System.arraycopy(m.data[i], 0, temp, 0, m.cols);
        System.arraycopy(m.data[j], 0, m.data[i], 0, m.cols);
        System.arraycopy(temp, 0, m.data[j], 0, m.cols);
    }

    /**
     * Calculates the determinant of this LU-decompostion. The determinant is
     * calculated by multiplying the determinant of the upper triangular matrix
     * (the product of the diagonal elements) with the determinant of
     * permutation matrix (being always 1 or -1). The lower triangular matrix
     * has always determinant 1, and needs not be taken into account.
     */
    private void calculateDeterminant() {
        determinant = 1;
        for (int i = 0; i < u.rows; i++) {
            determinant *= u.data[i][i];
        }
        if (elementaryPermutations % 2 == 1) {
            determinant *= -1;
        }
    }
}
