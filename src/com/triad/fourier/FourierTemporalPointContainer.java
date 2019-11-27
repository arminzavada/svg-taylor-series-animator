package com.triad.fourier;

public class FourierTemporalPointContainer {
    Point[][] points;

    public FourierTemporalPointContainer(Rotator[] rotators, int timeSteps) {
        points = new Point[timeSteps][];

        double deltaTime = 1f / timeSteps;

        for (int i = 0; i < timeSteps; i++) {
            points[i] = new Point[rotators.length];

            Point point = new Point(0, 0);

            for (int j = 0; j < rotators.length; j++) {
                point = Point.add(point, rotators[j].calculateTipAtTime(deltaTime * i));

                points[i][j] = point;
            }
        }
    }

}
