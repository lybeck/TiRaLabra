/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package matrix;

import static org.junit.Assert.*;

/**
 *
 * @author Lasse
 */
public class TestUtils {

    private static final double eps = 10e-12;

    static void assertMatrixEqual(Matrix expected, Matrix result) {
        assertEquals(result.rows, expected.rows);
        assertEquals(result.cols, expected.cols);

        for (int i = 0; i < expected.rows; i++) {
            for (int j = 0; j < expected.cols; j++) {
                assertEquals(expected.data[i][j], result.data[i][j], eps);
            }
        }
    }

    static void assertMatrixEqual(Matrix expected, Matrix result, double epsilon) {
        assertEquals(result.rows, expected.rows);
        assertEquals(result.cols, expected.cols);

        for (int i = 0; i < expected.rows; i++) {
            for (int j = 0; j < expected.cols; j++) {
                assertEquals(expected.data[i][j], result.data[i][j], epsilon);
            }
        }
    }
}
