package com.triad.fourier;

import com.triad.math.Complex;

public final class ComplexSeriesProviderImplementation implements ComplexSeriesProvider {
    private Complex[] fourierSeries;
    private int length;
    private ComplexFunction function = null;
    private FourierUpdateHandler updateHandler = () -> {}; // do nothing by default.

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
    public ComplexFunction getFunction() {
        return function;
    }

    @Override
    public void setOnUpdateHandler(FourierUpdateHandler updateHandler) {
        this.updateHandler = updateHandler;
    }

    @Override
    public void setComplexFunction(ComplexFunction function) {
        this.function = function;
        generateSeries();
        updateHandler.method();
    }

    private void generateSeries() {
        fourierSeries = new Complex[length * 2 + 1];

        for (int k = -length; k <= length; k++) {
            fourierSeries[k + length] = integrateOver(k);
        }
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
