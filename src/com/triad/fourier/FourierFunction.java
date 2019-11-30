package com.triad.fourier;

import com.triad.math.Complex;

public interface FourierFunction {
    Complex getValueAt(int k, int t);
    int getMaxTime();
    int getLength();
}
