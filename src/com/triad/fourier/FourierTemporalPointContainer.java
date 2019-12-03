package com.triad.fourier;

import com.triad.math.Complex;

import java.util.Arrays;
import java.util.List;

/**
 * Helper class for {@link FourierTemporalPointContainer}. It converts a {@link Complex} number to a radius and angle, and can be used to "rotate" it.
 */
class Rotator {
    /**
     * The radius.
     */
    private float radius;
    /**
     * The angle.
     */
    private float angle;
    /**
     * The rotations to turn, when time = 1.
     */
    private int turnsPerSecond;

    /**
     * Calculates the raius and angle, and sets the turnPerSecond.
     * @param complex the {@link Complex} to convert.
     * @param turnsPerSecond the number of turns to add when time equals 1
     */
    private Rotator(Complex complex, int turnsPerSecond) {
        this.radius = complex.getAbsolute();
        this.angle = complex.getAngle();
        this.turnsPerSecond = turnsPerSecond;
    }

    /**
     * Rotates the tip with the specified time. New angle is calculated as: turnsPerSecond * 2 * time * PI
     * @param time the time.
     * @return the rotated tip's {@link Complex} representation.
     */
    Complex calculateTipAtTime(float time) {
        float currentAngle = angle + turnsPerSecond * 2 * time * (float)Math.PI;
        currentAngle %= 2 * Math.PI;

        float x = (float)Math.cos(currentAngle) * radius;
        float y = (float)Math.sin(currentAngle) * radius;

        return new Complex(x, y);
    }

    /**
     * Converts a {@link ComplexSeriesProvider}'s possible values to {@link Rotator}s.
     * @param complexSeriesProvider the {@link ComplexSeriesProvider}.
     * @return a list of {@link Rotator}.
     */
    static List<Rotator> convertSeries(ComplexSeriesProvider complexSeriesProvider) {
        Rotator[] rotators = new Rotator[complexSeriesProvider.getLength() * 2 + 1];

        for (int k = -complexSeriesProvider.getLength(); k <= complexSeriesProvider.getLength(); k++) {
            rotators[k + complexSeriesProvider.getLength()] = new Rotator(complexSeriesProvider.getValueAt(k), k);
        }

        return Arrays.asList(rotators);
    }
}

/**
 * An implementation of the {@link FourierFunction}. It calculates all the values beforehand, so it becomes a lookup table.
 */
public class FourierTemporalPointContainer implements FourierFunction {
    /**
     * The calculated {@link Complex} values.
     */
    private Complex[][] points = null;
    /**
     * The {@link ComplexSeriesProvider} this {@link FourierFunction} uses.
     */
    private ComplexSeriesProvider complexSeriesProvider = null;
    /**
     * The update event handler. Empty method by default.
     */
    private FourierUpdateHandler updateHandler = () -> {}; // do nothing by default.

    /**
     * {@inheritDoc}
     */
    @Override
    public Complex getValueAt(int k, int t) {
        return points[k + complexSeriesProvider.getLength()][t];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMaxTime() {
        return complexSeriesProvider.getNumberOfSamples();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getLength() {
        return complexSeriesProvider.getLength();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setOnUpdateHandler(FourierUpdateHandler updateHandler) {
        this.updateHandler = updateHandler;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setComplexSeriesProvider(ComplexSeriesProvider complexSeriesProvider) {
        this.complexSeriesProvider = complexSeriesProvider;

        complexSeriesProvider.setOnUpdateHandler(() -> {
            generatePoints();
            updateHandler.method();
        });

        generatePoints();
        updateHandler.method();
    }

    /**
     * Calculates the {@link Complex} values from the {@link ComplexSeriesProvider}.
     */
    private void generatePoints() {
        points = new Complex[complexSeriesProvider.getLength() * 2 + 1][];

        List<Rotator> rotators = Rotator.convertSeries(complexSeriesProvider);

        float deltaTime = 1f / complexSeriesProvider.getNumberOfSamples();

        for (int k = 0; k < complexSeriesProvider.getLength() * 2 + 1; k++) {
            points[k] = new Complex[complexSeriesProvider.getNumberOfSamples()];

            for (int t = 0; t < complexSeriesProvider.getNumberOfSamples(); t++) {
                points[k][t] = rotators.get(k).calculateTipAtTime(t * deltaTime);
            }
        }
    }
}
