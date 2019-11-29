package com.triad.fourier;

import com.triad.math.Complex;

import java.util.Arrays;
import java.util.List;

class Rotator {
    private float radius;
    private float angle;
    private int turnsPerSecond;

    private Rotator(Complex complex, int turnsPerSecond) {
        this.radius = (float)Math.sqrt(Math.pow(complex.getReal(), 2) + Math.pow(complex.getImaginary(), 2));
        this.angle = (float)(1 / Math.tan(complex.getImaginary() / complex.getReal()));
        this.turnsPerSecond = turnsPerSecond;
    }

    Complex calculateTipAtTime(float time) {
        double currentAngle = angle + 2 * Math.PI * turnsPerSecond * time;

        return new Complex(
                (float)Math.cos(currentAngle) * radius,
                (float)Math.sin(currentAngle) * radius
        );
    }

    static List<Rotator> convertSeries(ComplexSeriesProvider complexSeriesProvider) {
        Rotator[] rotators = new Rotator[complexSeriesProvider.getLength() * 2 + 1];

        for (int k = -complexSeriesProvider.getLength(); k <= complexSeriesProvider.getLength(); k++) {
            rotators[k + complexSeriesProvider.getLength()] = new Rotator(complexSeriesProvider.getValueAt(k), k);
        }

        return Arrays.asList(rotators);
    }
}

public class FourierTemporalPointContainer implements FourierFunction {
    private Complex[][] points;
    private ComplexSeriesProvider complexSeriesProvider;

    public FourierTemporalPointContainer(ComplexSeriesProvider complexSeriesProvider) {
        points = new Complex[complexSeriesProvider.getLength()][];

        List<Rotator> rotators = Rotator.convertSeries(complexSeriesProvider);

        double deltaTime = 1f / complexSeriesProvider.getFunction().getNumberOfSamples();

        for (int k = 0; k < complexSeriesProvider.getLength() * 2 + 1; k++) {
            points[k] = new Complex[complexSeriesProvider.getFunction().getNumberOfSamples()];

            for (int t = 0; t < complexSeriesProvider.getFunction().getNumberOfSamples(); t++) {
                points[k][t] = rotators.get(k).calculateTipAtTime(t);
            }
        }
    }

    @Override
    public Complex getValueAt(int k, int t) {
        return points[k + complexSeriesProvider.getFunction().getNumberOfSamples()][t];
    }
}
