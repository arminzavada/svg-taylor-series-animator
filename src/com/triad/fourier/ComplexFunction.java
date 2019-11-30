package com.triad.fourier;

import com.triad.math.Complex;

public interface ComplexFunction {
    float getFunctionRange();
    int getNumberOfSamples();
    Complex getValueAt(int t);
}
