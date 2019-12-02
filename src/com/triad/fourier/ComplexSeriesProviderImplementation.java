package com.triad.fourier;

import com.triad.math.Complex;

public final class ComplexSeriesProviderImplementation implements ComplexSeriesProvider {
    private Complex[] fourierSeries;
    private int length;
    private ComplexFunction function;

    public ComplexSeriesProviderImplementation(ComplexFunction function, int length) {
        this.length = length;
        this.function = function;

        fourierSeries = generateSeries();
    }

    @Override
    public int getLength() { return length; }

    @Override
    public Complex getValueAt(int k) {
        return fourierSeries[k + length];
    }

    @Override
    public ComplexFunction getFunction() {
        return function;
    }

    private Complex[] generateSeries() {
        Complex[] output = new Complex[length * 2 + 1];

        for (int k = -length; k <= length; k++) {
            output[k + length] = integrateOver(k);
        }

        return output;
    }

    private Complex integrateOver(int k) {
        Complex result = new Complex(0, 0);

        for (int i = 0; i < function.getNumberOfSamples(); i++) {
            float t = (float)i / function.getNumberOfSamples();
            result = Complex.add(
                    result,
                    Complex.multiply(
                            function.getValueAt(i),
                            Complex.exp(
                                    Complex.multiply(
                                            Complex.I,
                                            -k * 2 * (float)Math.PI * t
                                    )
                            )
                    )
            );
        }

        return Complex.multiply(result, 1f / function.getNumberOfSamples());
    }
}
