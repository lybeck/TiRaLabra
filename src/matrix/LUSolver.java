/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package matrix;

/**
 * Class for calculations with square matrices. All methods use the
 * LU-decomposition in the calculations.
 *
 * @author Lasse
 */
class LUSolver {

    /**
     * Calculates the inverse matrix of the input matrix if possible. The method
     * uses LU-decomposition in the inverting process.
     *
     * @param m A square matrix.
     * @return The inverse matrix of m, if it exists, otherwise null.
     */
    static Matrix calculateInverse(Matrix m) {
        LU lu = m.lu();
        if (lu.getDeterminant() == 0) {
            return null;
        }
        return solveMatrixEquation(m.lu(), Matrix.eye(m.cols));
    }

    /**
     * Solves the equation (L*U)*x = b, where L*U is an LU-decomposition of some
     * matrix and b is a square matrix of the same size as L*U.
     *
     * @param lu The LU-decomposition of an n x n matrix.
     * @param b An n x n matrix.
     * @return The resulting matrix x, if the solution is unique, otherwise
     * null.
     */
    static Matrix solveMatrixEquation(LU lu, Matrix b) {
        int n = lu.getN();
        if (b.rows != n || b.cols != n) {
            throw new IllegalArgumentException("Not a valid equation!");
        }

        Matrix[] bColumns = getColumnVectors(b);
        Matrix[] xColumns = new Matrix[n];

        for (int i = 0; i < n; i++) {
            xColumns[i] = solveLinearEquation(lu, bColumns[i]);
        }

        return createMatrixFromColumns(xColumns);
    }

    /**
     * Solves a linear system of equations of type (L*U)*x = b, where L*U is an
     * LU-decomposition of some matrix and b is a column vector.
     *
     * @param lu The LU-decomposition of a square matrix.
     * @param b A column vector.
     * @return The resulting vector x, if the solution is unique, otherwise
     * null.
     */
    static Matrix solveLinearEquation(LU lu, Matrix b) {
        int n = lu.getN();
        if (b.rows != n || b.cols != 1) {
            throw new IllegalArgumentException("Not a valid system of equations!");
        }
        if (lu.getDeterminant() == 0) {
            System.out.println("No unique solutions found!");
            return null;
        }

        Matrix z = forwardSubstitute(lu.getL(), b);
        Matrix x = backwardSubstitute(lu.getU(), z);

        return x;
    }

    /**
     * Solves by forward substitution the linear system l*x = b, where l is a
     * <b>unit</b> lower triangular matrix and b is a column vector.
     *
     * @param l A unit lower triangular matrix.
     * @param b A column vector.
     * @return The resulting vector x of the equation l*x = b.
     */
    private static Matrix forwardSubstitute(Matrix l, Matrix b) {
        Matrix x = new Matrix(b.rows, 1);
        x.data[0][0] = b.data[0][0];
        for (int i = 1; i < b.rows; i++) {
            double sum = 0;
            for (int j = 0; j < i; j++) {
                sum += l.data[i][j] * x.data[j][0];
            }
            x.data[i][0] = b.data[i][0] - sum;
        }
        return x;
    }

    /**
     * Solves by backward substitution the linear system u*x = b, where u is an
     * upper triangular matrix and b is a column vector.
     *
     * @param u A unit lower triangular matrix.
     * @param b A column vector.
     * @return The resulting vector x of the equation u*x = b.
     */
    private static Matrix backwardSubstitute(Matrix u, Matrix b) {
        int n = b.rows;
        Matrix x = new Matrix(n, 1);
        x.data[n - 1][0] = b.data[n - 1][0] / u.data[n - 1][n - 1];

        for (int i = n - 2; i >= 0; i--) {
            double sum = 0;
            for (int j = n - 1; j > i; j--) {
                sum += u.data[i][j] * x.data[j][0];
            }
            x.data[i][0] = (b.data[i][0] - sum) / u.data[i][i];
        }
        return x;
    }

    static Matrix[] getColumnVectors(Matrix a) {
        Matrix[] columns = new Matrix[a.rows];
        for (int i = 0; i < columns.length; i++) {
            columns[i] = new Matrix(a.rows, 1);
            for (int j = 0; j < a.rows; j++) {
                columns[i].data[j][0] = a.data[j][i];
            }
        }
        return columns;
    }

    /**
     * Creates a matrix from the input array, treating the matrices in the array
     * as columnvectors.
     *
     * @param columns Array of columns vectors.
     * @return Matrix constructed from columns vectors.
     */
    private static Matrix createMatrixFromColumns(Matrix[] columns) {
        Matrix result = new Matrix(columns[0].rows, columns.length);
        for (int i = 0; i < columns.length; i++) {
            for (int j = 0; j < columns[0].rows; j++) {
                result.data[j][i] = columns[i].data[j][0];
            }
        }
        return result;
    }
}
