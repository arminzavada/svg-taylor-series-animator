package com.triad.fourier;

import com.triad.math.Complex;

/**
 * Represents a {@link Complex} function over time.
 */
public interface ComplexFunction {
    /**
     * Returns the range of this function.
     * @return the max input.
     */
    int getNumberOfSamples();

    /**
     * Returns the {@link Complex} value corresponding to the given time.
     * @param t the time where the function is evaluated.
     * @return the {@link Complex}
     */
    Complex getValueAt(int t);

    /**
     * An empty {@link ComplexFunction}, with 0 samples.
     */
    ComplexFunction Empty = new ComplexFunction() {
        @Override
        public int getNumberOfSamples() {
            return 0;
        }

        @Override
        public Complex getValueAt(int t) {
            return null;
        }
    };
}
