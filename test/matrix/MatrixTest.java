/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package matrix;

import java.util.Random;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author lasse
 */
public class MatrixTest {

    private static final double eps = 10e-12;
    private Random rand;
    private final Matrix smallTest, bigTest;

    public MatrixTest() {
        rand = new Random(42);
        smallTest = createSmallTestMatrix();
        bigTest = createBigTestMatrix();
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    private Matrix createSmallTestMatrix() {
        return createTestMatrix(3, 5);
    }

    private Matrix createBigTestMatrix() {
        return createTestMatrix(11, 13);
    }

    private Matrix createTestMatrix(int r, int c) {
        Matrix m = new Matrix(r, c);
        for (int i = 0; i < m.rows; i++) {
            for (int j = 0; j < m.cols; j++) {
                m.data[i][j] = 201 - 101 * rand.nextDouble();
            }
        }
        return m;
    }

    private void assertMatrixEqual(Matrix expected, Matrix result) {
        assertEquals(result.rows, expected.rows);
        assertEquals(result.cols, expected.cols);

        for (int i = 0; i < expected.rows; i++) {
            for (int j = 0; j < expected.cols; j++) {
                assertEquals(expected.data[i][j], result.data[i][j], eps);
            }
        }
    }

    @Test
    public void singleIntParameterConstructorWorks() {
        Matrix m = new Matrix(4);
        assertEquals(4, m.rows);
        assertEquals(4, m.cols);
        for (int i = 0; i < m.rows; i++) {
            for (int j = 0; j < m.cols; j++) {
                assertEquals(0, m.data[i][j], 0);
            }
        }
    }

    @Test
    public void twoIntParameterConstructorWorks() {
        int r = 6;
        int c = 9;
        Matrix m = new Matrix(r, c);
        assertEquals(r, m.rows);
        assertEquals(c, m.cols);
        for (int i = 0; i < m.rows; i++) {
            for (int j = 0; j < m.cols; j++) {
                assertEquals(0, m.data[i][j], 0);
            }
        }
    }

    @Test
    public void primitiveMatrixParameterConstructorWorks() {
        int r = 5;
        int c = 7;
        double[][] par = new double[r][c];
        for (int i = 0; i < par.length; i++) {
            for (int j = 0; j < par[i].length; j++) {
                par[i][j] = 1.0 / (1 + i + j);
            }
        }
        Matrix m = new Matrix(par);
        assertEquals(r, m.rows);
        assertEquals(c, m.cols);
        for (int i = 0; i < m.rows; i++) {
            for (int j = 0; j < m.cols; j++) {
                assertEquals(par[i][j], m.data[i][j], eps);
            }
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void primitiveMatrixParameterConstructorThrowsIfMatrixMalformed() {
        double[][] par = new double[3][3];
        par[2] = new double[5];
        new Matrix(par);
    }

    @Test
    public void matrixConstructorWorks() {
        Matrix m1 = new Matrix(4);
        Matrix m = new Matrix(m1);
        assertEquals(4, m.rows);
        assertEquals(4, m.cols);
        for (int i = 0; i < m.rows; i++) {
            for (int j = 0; j < m.cols; j++) {
                assertEquals(0, m.data[i][j], 0);
            }
        }
    }

    @Test
    public void matrixConstructorCreatesATrueCopy() {
        Matrix m1 = new Matrix(4);
        Matrix m = new Matrix(m1);
        m1.data[1][1] = 7;
        assertEquals(7, m1.data[1][1], 0);
        assertEquals(0, m.data[1][1], 0);
    }

    @Test
    public void creatingOneValuedMatrixWorksWithOneIntParameter() {
        Matrix m = Matrix.ones(4);
        assertEquals(4, m.rows);
        assertEquals(4, m.cols);
        for (int i = 0; i < m.rows; i++) {
            for (int j = 0; j < m.cols; j++) {
                assertEquals(1, m.data[i][j], 0);
            }
        }
    }

    @Test
    public void creatingOneValuedMatrixWorksWithTwoIntParameters() {
        int r = 12;
        int c = 4;
        Matrix m = Matrix.ones(r, c);
        assertEquals(r, m.rows);
        assertEquals(c, m.cols);
        for (int i = 0; i < m.rows; i++) {
            for (int j = 0; j < m.cols; j++) {
                assertEquals(1, m.data[i][j], 0);
            }
        }
    }

    @Test
    public void creatingIdentityMatrixWorks() {
        Matrix m = Matrix.eye(6);
        assertEquals(6, m.rows);
        assertEquals(6, m.cols);
        for (int i = 0; i < m.rows; i++) {
            for (int j = 0; j < m.cols; j++) {
                if (i == j) {
                    assertEquals(1, m.data[i][j], 0);
                } else {
                    assertEquals(0, m.data[i][j], 0);
                }
            }
        }
    }

    @Test
    public void creatingNonSquareIdentityMatrixWorks() {
        int r = 11;
        int c = 13;
        Matrix m = Matrix.eye(r, c);
        assertEquals(r, m.rows);
        assertEquals(c, m.cols);
        for (int i = 0; i < m.rows; i++) {
            for (int j = 0; j < m.cols; j++) {
                if (i == j) {
                    assertEquals(1, m.data[i][j], 0);
                } else {
                    assertEquals(0, m.data[i][j], 0);
                }
            }
        }
    }

    @Test
    public void matrixScaleTest1() {
        double k = 3.45;
        Matrix m = smallTest.scale(k);
        for (int i = 0; i < m.rows; i++) {
            for (int j = 0; j < m.cols; j++) {
                assertEquals(k * smallTest.data[i][j], m.data[i][j], eps);
            }
        }
    }

    @Test
    public void matrixScaleTest2() {
        double k = 3.45;
        Matrix m = bigTest.scale(k);
        for (int i = 0; i < m.rows; i++) {
            for (int j = 0; j < m.cols; j++) {
                assertEquals(k * bigTest.data[i][j], m.data[i][j], eps);
            }
        }
    }

    @Test
    public void matrixConstantAdditionTest1() {
        double k = 3.45;
        Matrix m = smallTest.add(k);
        for (int i = 0; i < m.rows; i++) {
            for (int j = 0; j < m.cols; j++) {
                assertEquals(k + smallTest.data[i][j], m.data[i][j], eps);
            }
        }
    }

    @Test
    public void matrixConstantAdditionTest2() {
        double k = 3.45;
        Matrix m = bigTest.add(k);
        for (int i = 0; i < m.rows; i++) {
            for (int j = 0; j < m.cols; j++) {
                assertEquals(k + bigTest.data[i][j], m.data[i][j], eps);
            }
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void matrixAdditionWithDifferentSizedMatricesThrows() {
        smallTest.add(bigTest);
    }

    @Test
    public void matrixAdditionTest1() {
        Matrix test = createTestMatrix(smallTest.rows, smallTest.cols);
        Matrix m = test.add(smallTest);
        for (int i = 0; i < m.rows; i++) {
            for (int j = 0; j < m.cols; j++) {
                assertEquals(test.data[i][j] + smallTest.data[i][j], m.data[i][j], eps);
            }
        }
    }

    @Test
    public void matrixAdditionTest2() {
        Matrix test = createTestMatrix(bigTest.rows, bigTest.cols);
        Matrix m = test.add(bigTest);
        for (int i = 0; i < m.rows; i++) {
            for (int j = 0; j < m.cols; j++) {
                assertEquals(test.data[i][j] + bigTest.data[i][j], m.data[i][j], eps);
            }
        }
    }

    @Test
    public void matrixSubtractionTest1() {
        Matrix test = createTestMatrix(smallTest.rows, smallTest.cols);
        Matrix m = test.sub(smallTest);
        for (int i = 0; i < m.rows; i++) {
            for (int j = 0; j < m.cols; j++) {
                assertEquals(test.data[i][j] - smallTest.data[i][j], m.data[i][j], eps);
            }
        }
    }

    @Test
    public void matrixSubtractionTest2() {
        Matrix test = createTestMatrix(bigTest.rows, bigTest.cols);
        Matrix m = test.sub(bigTest);
        for (int i = 0; i < m.rows; i++) {
            for (int j = 0; j < m.cols; j++) {
                assertEquals(test.data[i][j] - bigTest.data[i][j], m.data[i][j], eps);
            }
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void naiveMatrixMultiplicationThrowsIfSizesDoNotMatch() {
        bigTest.mulNaive(bigTest);
    }

    @Test
    public void naiveMatrixMultiplicationTest1() {
        double[][] a = {{1, 2, 3}, {4, 5, 6}};
        double[][] b = {{2, 3}, {3, 4}, {4, 5}};
        Matrix m1 = new Matrix(a);
        Matrix m2 = new Matrix(b);

        Matrix result = m1.mulNaive(m2);

        double[][] e = {{20, 26}, {47, 62}};
        Matrix expected = new Matrix(e);

        assertMatrixEqual(expected, result);
    }

    @Test
    public void naiveMatrixMultiplicationTest2() {
        double[][] a = {{2, 3, 5, 7}, {11, 13, 17, 19}};
        double[][] b = {{1, 2, 3}, {2, 3, 4}, {3, 4, 5}, {4, 5, 6}};
        Matrix m1 = new Matrix(a);
        Matrix m2 = new Matrix(b);

        Matrix result = m1.mulNaive(m2);

        double[][] e = {{51, 68, 85}, {164, 224, 284}};
        Matrix expected = new Matrix(e);

        assertMatrixEqual(expected, result);
    }

    @Test
    public void naiveMatrixMultiplicationTest3() {
        double[][] a = {
            {1.2, 1.3},
            {1.4, 1.5},
            {6.6, 6.5},
            {6.4, 6.3},
            {6.2, 6.1}
        };

        double[][] b = {
            {2.3, 5.7, 1.4},
            {6.8, 9.1, 12.1}
        };

        Matrix m1 = new Matrix(a);
        Matrix m2 = new Matrix(b);

        Matrix result = m1.mulNaive(m2);

        double[][] e = {
            {11.60, 18.67, 17.41},
            {13.42, 21.63, 20.11},
            {59.38, 96.77, 87.89},
            {57.56, 93.81, 85.19},
            {55.74, 90.85, 82.49}
        };

        Matrix expected = new Matrix(e);

        assertMatrixEqual(expected, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void strassenMatrixMultiplicationThrowsIfSizesDoNotMatch() {
        bigTest.mulStrassen(bigTest);
    }

    @Test
    public void strassenMatrixMultiplicationGivesSameResultAsNaive1() {
        Matrix test = createTestMatrix(smallTest.cols, smallTest.rows);

        Matrix naive = smallTest.mulNaive(test);
        Matrix strassen = smallTest.mulStrassen(test);

        assertMatrixEqual(naive, strassen);
    }

    @Test
    public void strassenMatrixMultiplicationGivesSameResultAsNaive2() {
        Matrix test = createTestMatrix(bigTest.cols, bigTest.rows);

        Matrix naive = bigTest.mulNaive(test);
        Matrix strassen = bigTest.mulStrassen(test);

        assertMatrixEqual(naive, strassen);
    }

    @Test
    public void strassenMatrixMultiplicationGivesSameResultAsNaive3() {
        Matrix m1 = createTestMatrix(79, 127);
        Matrix m2 = createTestMatrix(127, 56);

        Matrix naive = m1.mulNaive(m2);
        Matrix strassen = m1.mulStrassen(m2);

        assertMatrixEqual(naive, strassen);
    }

    @Test(expected = IllegalArgumentException.class)
    public void matrixPowerThrowsIfMatrixNotASquare() {
        smallTest.pow(4);
    }

    @Test(expected = IllegalArgumentException.class)
    public void matrixPowerThrowsIfTryingToGetNegativePower() {
        Matrix.ones(12).pow(-3);
    }

    @Test
    public void matrixPowerTest1() {
    }

    @Test
    public void matrixPowerTest2() {
    }

    @Test
    public void matrixTransposeTest1() {
    }

    @Test
    public void matrixTransposeTest2() {
    }

    @Test(expected = IllegalArgumentException.class)
    public void determinantFunctionThrowsIfMatrixNotSquare() {
        smallTest.det();
    }

    @Test
    public void determinantTest1() {
    }

    @Test
    public void determinantTest2() {
    }

    @Test
    public void determinantTest3() {
    }

    @Test
    public void inverseTest1() {
    }

    @Test
    public void inverseTest2() {
    }
}