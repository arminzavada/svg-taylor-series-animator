package com.triad.fourier;

import com.triad.math.Complex;

public interface ComplexSeriesProvider {
    int getLength();
    Complex getValueAt(int k);
    int getNumberOfSamples();
    void setOnUpdateHandler(FourierUpdateHandler updateHandler);
    void setComplexFunction(ComplexFunction function);
}
