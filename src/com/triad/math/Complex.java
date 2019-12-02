package com.triad.math;

public final class Complex {
    private float real;
    private float imaginary;

    public static final Complex I = new Complex(0, 1);
    public static final Complex Zero = new Complex(0, 0);

    public Complex(float real, float imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    public float getReal() { return real; }
    public float getImaginary() { return imaginary; }
    public float getAbsolute() { return (float)Math.hypot(real, imaginary); }
    public float getAngle() { return (float)(Math.atan2(imaginary, real) + Math.PI); }

    public static Complex add(Complex a, Complex b) {
        return new Complex(a.real + b.real, a.imaginary + b.imaginary);
    }
    public static Complex add(Complex a, float b) {
        return new Complex(a.real + b, a.imaginary);
    }
    public static Complex multiply(Complex a, Complex b) {
        return new Complex(a.real * b.real + a.imaginary * b.imaginary, a.real * b.imaginary + a.imaginary * b.real);
    }
    public static Complex multiply(Complex a, float b) {
        return new Complex(a.real * b, a.imaginary * b);
    }
    public static Complex exp(Complex c) {
        return new Complex((float)(Math.exp(c.real) * Math.cos(c.imaginary)), (float)(Math.exp(c.real) * Math.sin(c.imaginary)));
    }
}
