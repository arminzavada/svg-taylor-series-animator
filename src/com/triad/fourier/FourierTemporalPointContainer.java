package com.triad.fourier;

class Rotator {
    private double radius;
    private double angle;
    private int turnsPerSecond;

    Rotator(double radius, double angle, int turnsPerSecond) {
        this.radius = radius;
        this.angle = angle;
        this.turnsPerSecond = turnsPerSecond;
    }

    Point calculateTipAtTime(double time) {
        double currentAngle = angle + 2 * Math.PI * turnsPerSecond * time;

        return new Point(
                Math.cos(currentAngle) * radius,
                Math.sin(currentAngle) * radius
        );
    }
}

public class FourierTemporalPointContainer {
    Point[][] points;

    public FourierTemporalPointContainer(ComplexSeriesProvider complexSeriesProvider) {
        points = new Point[complexSeriesProvider.getLength()][];

        double deltaTime = 1f / complexSeriesProvider.getFunction().getNumberOfSamples();

        for (int i = 0; i < complexSeriesProvider.getLength(); i++) {
            points[i] = new Point[complexSeriesProvider.getFunction().getNumberOfSamples()];

            Point point = new Point(0, 0);

            for (int t = 0; t < complexSeriesProvider.getFunction().getNumberOfSamples(); t++) {
//                point = Point.add(point, rotators[t].calculateTipAtTime(deltaTime * i));

                points[i][t] = point;
            }
        }
    }

}
