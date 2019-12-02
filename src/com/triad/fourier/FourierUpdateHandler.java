package com.triad.fourier;

/**
 * Represents an update handler of the Fourier call chain event.
 */
@FunctionalInterface
public interface FourierUpdateHandler {
    /**
     * This method is called whenever an update occurs.
     */
    void method();
}