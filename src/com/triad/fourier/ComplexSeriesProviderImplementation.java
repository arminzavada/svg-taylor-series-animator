package com.triad.fourier;

import com.triad.math.Complex;

/**
 * An implementation of the {@link ComplexSeriesProvider}. It calculates all the values beforehand, so it becomes a lookup table. It is Serialisable.
 */
public final class ComplexSeriesProviderImplementation implements ComplexSeriesProvider {
    /**
     * The calculated {@link Complex} values.
     */
    private Complex[] fourierSeries;
    /**
     * The length.
     */
    private int length;
    /**
     * The number of samples of the set {@link ComplexFunction}.
     */
    private int numberOfSamples = 0;
    /**
     * The update event handler. Empty method by default.
     */
    private transient FourierUpdateHandler updateHandler = () -> {}; // do nothing by default.

    /**
     * Creates a {@link ComplexSeriesProvider} with the specified length.
     * @param length the specified length.
     */
    public ComplexSeriesProviderImplementation(int length) {
        this.length = length;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getLength() { return length; }

    /**
     * {@inheritDoc}
     */
    @Override
    public Complex getValueAt(int k) {
        return fourierSeries[k + length];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNumberOfSamples() {
        return numberOfSamples;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setOnUpdateHandler(FourierUpdateHandler updateHandler) {
        this.updateHandler = updateHandler;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setComplexFunction(ComplexFunction function) {
        this.numberOfSamples = function.getNumberOfSamples();
        generateSeries(function);
        updateHandler.method();
    }

    /**
     * Generates the series from the given {@link ComplexFunction}.
     * @param function the {@link ComplexFunction} to generate from.
     */
    private void generateSeries(ComplexFunction function) {
        fourierSeries = new Complex[length * 2 + 1];

        for (int k = -length; k <= length; k++) {
            fourierSeries[k + length] = integrateOver(function, k);
        }
    }

    /**
     * Calculates the Fourier integral coefficient of the specified {@link ComplexFunction} at the spcified index.
     * @param function the specified {@link ComplexFunction}
     * @param k the index.
     * @return the {@link Complex} coefficient at the index.
     */
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
