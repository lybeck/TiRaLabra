/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package matrix;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Lasse
 */
public class LUSolverTest {

    public LUSolverTest() {
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

    @Test
    public void testGetColumnVectors() {
        double[][] a = {
            {3, 2, -1},
            {2, -2, 4},
            {-1, .5, -1}};
        Matrix aMat = new Matrix(a);
        Matrix[] cols = LUSolver.getColumnVectors(aMat);
        double[][] c1 = {{3}, {2}, {-1}};
        double[][] c2 = {{2}, {-2}, {.5}};
        double[][] c3 = {{-1}, {4}, {-1}};
        Matrix[] expected = new Matrix[3];
        expected[0] = new Matrix(c1);
        expected[1] = new Matrix(c2);
        expected[2] = new Matrix(c3);
        if (cols.length != 3) {
            fail();
        }
        for (int i = 0; i < cols.length; i++) {
            TestUtils.assertMatrixEqual(expected[i], cols[i]);
        }
    }

    @Test
    public void testSolveLinearEquation1() {
        double[][] a = {
            {3, 2, -1},
            {2, -2, 4},
            {-1, .5, -1}};
        double[][] b = {{1}, {-2}, {0}};
        double[][] e = {{1}, {-2}, {-2}};
        Matrix aMat = new Matrix(a);
        Matrix bMat = new Matrix(b);
        Matrix expected = new Matrix(e);
        TestUtils.assertMatrixEqual(expected, LUSolver.solveLinearEquation(new LU(aMat), bMat));
    }

    @Test
    public void testSolveLinearEquation2() {
        double[][] a = {
            {2, 3},
            {4, 9}};
        double[][] b = {{6}, {15}};
        double[][] e = {{3.0 / 2}, {1}};
        Matrix aMat = new Matrix(a);
        Matrix bMat = new Matrix(b);
        Matrix expected = new Matrix(e);
        TestUtils.assertMatrixEqual(expected, LUSolver.solveLinearEquation(new LU(aMat), bMat));
    }

    @Test
    public void testSolveMatrixEquation1() {
        double[][] a = {
            {3, 2, -1},
            {2, -2, 4},
            {-1, .5, -1}};
        double[][] b = {
            {3, 2, -1},
            {2, -2, 4},
            {-1, .5, -1}};
        Matrix aMat = new Matrix(a);
        Matrix bMat = new Matrix(b);
        Matrix expected = Matrix.eye(3);
        TestUtils.assertMatrixEqual(expected, LUSolver.solveMatrixEquation(new LU(aMat), bMat));
    }

    @Test
    public void testSolveMatrixEquation2() {
        double[][] a = {
            {3, 2, -1},
            {2, -2, 4},
            {-1, .5, -1}};
        double[][] b = {
            {25, 5, 1},
            {64, 8, 1},
            {144, 12, 1}};
        Matrix aMat = new Matrix(a);
        Matrix bMat = new Matrix(b);
        Matrix result = LUSolver.solveMatrixEquation(new LU(aMat), bMat);
        TestUtils.assertMatrixEqual(aMat.mulNaive(result), bMat);
    }
}
