package com.triad.math;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ComplexTest {
    @Test
    public void constructorTest() {
        Complex complex = new Complex(10, -20);
        Assertions.assertEquals(10, complex.getReal());
        Assertions.assertEquals(-20, complex.getImaginary());
    }

    @Test
    public void geAbsoluteTest() {
        Complex complex = new Complex(10, -20);
        Assertions.assertEquals((float)Math.hypot(10, -20), complex.getAbsolute());
    }

    @Test
    public void getAngleTest() {
        Complex complex = new Complex(10, -20);
        Assertions.assertEquals((float)(Math.atan2(-20f, 10f) + Math.PI), complex.getAngle());
    }

    @Test
    public void equalityTest() {
        Complex c1 = new Complex(1,1);
        Complex c2 = new Complex(1, 1);
        Assertions.assertEquals(c1, c2);
    }

    @Test
    public void addTwoComplexesTest() {
        Complex c1 = new Complex(-1, 1);
        Complex c2 = new Complex(1, -1);
        Complex actual = Complex.add(c1, c2);
        Complex expected = new Complex(0, 0);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void addNumberToComplexTest() {
        Complex c1 = new Complex(-1, 1);
        Complex actual = Complex.add(c1, 1);
        Complex expected = new Complex(0, 1);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void multiplyTwoComplexesTest() {
        Complex c1 = new Complex(-1, 1);
        Complex c2 = new Complex(1, -1);
        Complex actual = Complex.multiply(c1, c2);
        Complex expected = new Complex(-2, 2);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void multiplyComplexByNumberTest() {
        Complex c1 = new Complex(-1, 1);
        Complex actual = Complex.multiply(c1, 10);
        Complex expected = new Complex(-10, 10);
        Assertions.assertEquals(expected, actual);
    }
}
