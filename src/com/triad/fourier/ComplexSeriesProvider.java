package com.triad.fourier;

import com.triad.math.Complex;

import java.io.Serializable;

/**
 * Represents a {@link ComplexFunction}'s Fourier coefficients. It is Serialisable.
 */
public interface ComplexSeriesProvider extends Serializable {
    /**
     * Returns the number of coefficients, where the value is 0..n, and the coefficients are -n..n
     * @return the number of coefficients.
     */
    int getLength();

    /**
     * Gets the coefficient at the given k value.
     * @param k the given k value.
     * @return the {@link Complex} coefficient.
     */
    Complex getValueAt(int k);

    /**
     * Returns the range of the underlying function.
     * @return the max input.
     */
    int getNumberOfSamples();

    /**
     * Sets the Update event handler. The handler is called, when there is an update in the Fourier chain.
     * @param updateHandler the {@link FourierUpdateHandler}.
     */
    void setOnUpdateHandler(FourierUpdateHandler updateHandler);

    /**
     * Sets the {@link ComplexFunction}, then calls the {@link FourierUpdateHandler}, after the internal values have been updated.
     * @param function the {@link ComplexFunction}.
     */
    void setComplexFunction(ComplexFunction function);
}
