package com.triad.math;

import java.io.Serializable;

/**
 * Represents a complex number with a real and an imaginary part.
 */
public final class Complex implements Serializable {
    private float real;
    private float imaginary;

    /**
     * The {@link Complex} I value, with real = 0 and imaginary = 1.
     */
    public static final Complex I = new Complex(0, 1);
    public static final Complex Zero = new Complex(0, 0);

    /**
     * Constructs a new Complex number with the specified parts.
     * @param real the real part.
     * @param imaginary the imaginary part.
     */
    public Complex(float real, float imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    /**
     * Returns the real part.
     * @return the real part.
     */
    public float getReal() { return real; }

    /**
     * Returns the imaginary part.
     * @return the imaginary part.
     */
    public float getImaginary() { return imaginary; }

    /**
     * Returns the magnitude.
     * @return the magnitude.
     */
    public float getAbsolute() { return (float)Math.hypot(real, imaginary); }

    /**
     * Returns the phase, in range of 0..2PI
     * @return the pase.
     */
    public float getAngle() { return (float)(Math.atan2(imaginary, real) + Math.PI); }

    /**
     * Adds two {@link Complex} numbers.
     * @param a the first number.
     * @param b the second number.
     * @return {@param a} + {@param b}
     */
    public static Complex add(Complex a, Complex b) {
        return new Complex(a.real + b.real, a.imaginary + b.imaginary);
    }

    /**
     * Adds a {@link Complex} number to a normal number.
     * @param a the {@link Complex} number.
     * @param b the ordinary number.
     * @return {@param a} + {@param b}
     */
    public static Complex add(Complex a, float b) {
        return new Complex(a.real + b, a.imaginary);
    }

    /**
     * Multiplies two {@link Complex} numbers.
     * @param a the first number.
     * @param b the second number.
     * @return {@param a} * {@param b}
     */
    public static Complex multiply(Complex a, Complex b) {
        return new Complex(a.real * b.real + a.imaginary * b.imaginary, a.real * b.imaginary + a.imaginary * b.real);
    }

    /**
     * Multiplies a {@link Complex} number with a normal number.
     * @param a the {@link Complex} number.
     * @param b the ordinary number.
     * @return {@param a} * {@param b}
     */
    public static Complex multiply(Complex a, float b) {
        return new Complex(a.real * b, a.imaginary * b);
    }

    /**
     * Computes a {@link Complex} number's exponent.
     * @param c the number.
     * @return e^c
     */
    public static Complex exp(Complex c) {
        return new Complex((float)(Math.exp(c.real) * Math.cos(c.imaginary)), (float)(Math.exp(c.real) * Math.sin(c.imaginary)));
    }
}
