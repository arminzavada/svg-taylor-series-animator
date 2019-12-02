package com.triad.fourier;

import com.triad.math.Complex;

public interface ComplexFunction {
    int getNumberOfSamples();
    Complex getValueAt(int t);
}
