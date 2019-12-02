package com.triad.view;

import com.triad.fourier.FourierFunction;
import com.triad.math.Complex;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class FourierDrawingPanel extends JPanel {
    private final FourierFunction fourierFunction;
    private final ArrayList<Complex> complexes = new ArrayList<>();
    private final Complex center;
    private final float animationDuration;
    private int time = 0;
    private Timer timer;

    public FourierDrawingPanel(FourierFunction fourierFunction, int width, int height, float animationDuration) {
        this.fourierFunction = fourierFunction;
        this.animationDuration = animationDuration;
        fourierFunction.setOnUpdateHandler(this::updateComplexFunction);
        center = new Complex(width / 2f, height / 2f);

        this.setSize(width, height);
        this.setBackground(Color.GRAY);
    }

    private void updateComplexFunction() {
        restartTimer();

        complexes.clear();

        for (int time = 0; time < fourierFunction.getMaxTime(); time++) {
            Complex c = Complex.multiply(center, -1);

            for (int k = -fourierFunction.getLength(); k <= fourierFunction.getLength(); k++) {
                c = Complex.add(c, fourierFunction.getValueAt(k, time));
            }

            complexes.add(Complex.multiply(c, -1));
        }
    }

    private void restartTimer() {
        if (timer != null) timer.stop();
        timer = new Timer((int)(animationDuration / fourierFunction.getMaxTime()), (ActionEvent) -> {
            time++;
            time %= fourierFunction.getMaxTime();

            this.repaint();
        });

        timer.setRepeats(true);
        timer.start();
    }

    private void drawLine(Graphics2D g, Complex c1, Complex c2) {
        g.drawLine((int)c1.getReal(), (int)c1.getImaginary(), (int)c2.getReal(), (int)c2.getImaginary());
    }

    private void drawFourier(Graphics2D g) {
        if (fourierFunction.getLength() == 0) return;

        Complex c1 = Complex.Zero;
        Complex c2 = center;

        drawLine(g, c1, c2);

        for (int i = 1; i <= fourierFunction.getLength(); i++) {
            c1 = Complex.add(c2, Complex.multiply(fourierFunction.getValueAt(i, time), -1));
            drawLine(g, c2, c1);

            c2 = Complex.add(c1, Complex.multiply(fourierFunction.getValueAt(-i, time), -1));
            drawLine(g, c1, c2);
        }

        for (int i = 1; i < time; i++) {
            drawLine(g, complexes.get(i - 1), complexes.get(i));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawFourier((Graphics2D) g);
    }
}
