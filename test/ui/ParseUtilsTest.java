/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

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
public class ParseUtilsTest {
    
    public ParseUtilsTest() {
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
    public void testIsDouble1(){
        assertTrue(ParseUtils.isDouble("3"));
    }

    @Test
    public void testIsDouble2(){
        assertTrue(ParseUtils.isDouble(".14"));
    }

    @Test
    public void testIsDouble3(){
        assertTrue(ParseUtils.isDouble("3e10"));
    }

    @Test
    public void testIsDouble4(){
        assertTrue(ParseUtils.isDouble("-3.14"));
    }

    @Test
    public void testIsDouble5(){
        assertTrue(ParseUtils.isDouble("-3.14E+321"));
    }

    @Test
    public void testIsDouble6(){
        assertFalse(ParseUtils.isDouble("3.14.3"));
    }

    @Test
    public void testIsDouble7(){
        assertFalse(ParseUtils.isDouble("3."));
    }
    
    @Test
    public void isDoubleAcceptsFractions() {
        assertTrue(ParseUtils.isDouble("-3.14E+321 / -3.14E+321"));
    }
    
    @Test
    public void testisNonNegativeInteger1() {
        assertTrue(ParseUtils.isNonNegativeInteger("4"));
    }
    
    @Test
    public void testisNonNegativeInteger2() {
        assertTrue(ParseUtils.isNonNegativeInteger("2e10"));
    }
    
    @Test
    public void testisNonNegativeInteger3() {
        assertFalse(ParseUtils.isNonNegativeInteger("-4"));
    }
    
    @Test
    public void testisNonNegativeInteger4() {
        assertFalse(ParseUtils.isNonNegativeInteger("4e-3"));
    }
}
