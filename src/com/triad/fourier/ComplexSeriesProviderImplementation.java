package com.triad.fourier;

import com.triad.math.Complex;

import java.io.Serializable;

public final class ComplexSeriesProviderImplementation implements ComplexSeriesProvider, Serializable {
    private Complex[] fourierSeries;
    private int length;
    private int numberOfSamples = 0;
    private transient FourierUpdateHandler updateHandler = () -> {}; // do nothing by default.

    public ComplexSeriesProviderImplementation(int length) {
        this.length = length;
    }

    @Override
    public int getLength() { return length; }

    @Override
    public Complex getValueAt(int k) {
        return fourierSeries[k + length];
    }

    @Override
    public int getNumberOfSamples() {
        return numberOfSamples;
    }

    @Override
    public void setOnUpdateHandler(FourierUpdateHandler updateHandler) {
        this.updateHandler = updateHandler;
    }

    @Override
    public void setComplexFunction(ComplexFunction function) {
        this.numberOfSamples = function.getNumberOfSamples();
        generateSeries(function);
        updateHandler.method();
    }

    private void generateSeries(ComplexFunction function) {
        fourierSeries = new Complex[length * 2 + 1];

        for (int k = -length; k <= length; k++) {
            fourierSeries[k + length] = integrateOver(function, k);
        }
    }

    private Complex integrateOver(ComplexFunction function, int k) {
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
