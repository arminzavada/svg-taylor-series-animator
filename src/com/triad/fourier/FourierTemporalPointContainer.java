package com.triad.fourier;

import com.triad.math.Complex;

import java.util.Arrays;
import java.util.List;

class Rotator {
    private float radius;
    private float angle;
    private int turnsPerSecond;

    private Rotator(Complex complex, int turnsPerSecond) {
        this.radius = complex.getAbsolute();
        this.angle = complex.getAngle();
        this.turnsPerSecond = turnsPerSecond;
    }

    Complex calculateTipAtTime(float time) {
        float currentAngle = angle + turnsPerSecond * 2 * time * (float)Math.PI;
        currentAngle %= 2 * Math.PI;

        float x = (float)Math.cos(currentAngle) * radius;
        float y = (float)Math.sin(currentAngle) * radius;

        return new Complex(x, y);
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
        this.complexSeriesProvider = complexSeriesProvider;
        points = new Complex[complexSeriesProvider.getLength() * 2 + 1][];

        List<Rotator> rotators = Rotator.convertSeries(complexSeriesProvider);

        float deltaTime = 1f / complexSeriesProvider.getFunction().getNumberOfSamples();

        for (int k = 0; k < complexSeriesProvider.getLength() * 2 + 1; k++) {
            points[k] = new Complex[complexSeriesProvider.getFunction().getNumberOfSamples()];

            for (int t = 0; t < complexSeriesProvider.getFunction().getNumberOfSamples(); t++) {
                points[k][t] = rotators.get(k).calculateTipAtTime(t * deltaTime);
            }
        }
    }

    @Override
    public Complex getValueAt(int k, int t) {
        return points[k + complexSeriesProvider.getLength()][t];
    }

    @Override
    public int getMaxTime() {
        return complexSeriesProvider.getFunction().getNumberOfSamples();
    }

    @Override
    public int getLength() {
        return complexSeriesProvider.getLength();
    }
}
