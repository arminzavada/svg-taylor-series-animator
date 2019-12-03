package com.triad.fourier;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FourierTemporalPointContainerTest {
    @Test
    public void updateHandlerTest() {
        var fourierFunction = new FourierTemporalPointContainer();
        var provider = new ComplexSeriesProviderImplementation(100);
        provider.setComplexFunction(ComplexFunction.Empty);
        var ref = new Object() {
            boolean called = false;
        };
        fourierFunction.setOnUpdateHandler(() -> ref.called = true);

        fourierFunction.setComplexSeriesProvider(provider);
        Assertions.assertTrue(ref.called);
    }
}
