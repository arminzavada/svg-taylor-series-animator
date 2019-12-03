package com.triad.fourier;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ComplexSeriesProviderImplementationTest {
    @Test
    public void updateHandlerTest() {
        var provider = new ComplexSeriesProviderImplementation(100);
        var ref = new Object() {
            boolean called = false;
        };
        provider.setOnUpdateHandler(() -> ref.called = true);
        provider.setComplexFunction(ComplexFunction.Empty);
        Assertions.assertTrue(ref.called);
    }
}
