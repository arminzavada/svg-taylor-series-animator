package com.triad.fourier;

import java.util.ArrayList;

public class Rotator {
    private double radius;
    private double angle;
    private int turnsPerSecond;

    public Rotator(double radius, double angle, int turnsPerSecond) {
        this.radius = radius;
        this.angle = angle;
        this.turnsPerSecond = turnsPerSecond;
    }

    public Point calculateTipAtTime(double time) {
        double currentAngle = angle + 2 * Math.PI * turnsPerSecond * time;

        return new Point(
            Math.cos(currentAngle) * radius,
            Math.sin(currentAngle) * radius
        );
    }
}


