package com.triad.fourier;

import com.triad.math.Complex;

/**
 * Represents a {@link ComplexFunction}'s Fourier series form.
 */
public interface FourierFunction {
    /**
     * Returns the coefficient at the specified k and t values.
     * @param k the coefficient's index.
     * @param t the time.
     * @return The {@link Complex} coefficient.
     */
    Complex getValueAt(int k, int t);

    /**
     * Returns the range of the underlying function.
     * @return the max input.
     */
    int getMaxTime();

    /**
     * Returns the number of coefficients, where the value is 0..n, and the coefficients are -n..n
     * @return the number of coefficients.
     */
    int getLength();

    /**
     * Sets the Update event handler. The handler is called, when there is an update in the Fourier chain.
     * @param updateHandler the {@link FourierUpdateHandler}.
     */
    void setOnUpdateHandler(FourierUpdateHandler updateHandler);

    /**
     * Sets the {@link ComplexSeriesProvider}, then calls the {@link FourierUpdateHandler}, after the internal values have been updated.
     * @param complexSeriesProvider the {@link ComplexSeriesProvider}.
     */
    void setComplexSeriesProvider(ComplexSeriesProvider complexSeriesProvider);
}
