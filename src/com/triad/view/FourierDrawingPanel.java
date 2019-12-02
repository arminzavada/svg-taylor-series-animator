package com.triad.view;

import com.triad.fourier.FourierFunction;
import com.triad.math.Complex;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class FourierDrawingPanel extends JPanel {
    private FourierFunction fourierFunction;
    int time = 0;
    ArrayList<Complex> complexes = new ArrayList<>();
    Timer timer;

    public FourierDrawingPanel(FourierFunction fourierFunction) {
        this.fourierFunction = fourierFunction;
        fourierFunction.setOnUpdateHandler(this::updateComplexFunction);
    }

    void updateComplexFunction() {
        if (timer != null) timer.stop();
        timer = new Timer((int)(3000f / fourierFunction.getMaxTime()), (ActionEvent) -> {
            time++;
            time %= fourierFunction.getMaxTime();

            this.repaint();
        });
        timer.setRepeats(true);
        timer.start();

        complexes.clear();

        for (int time = 0; time < fourierFunction.getMaxTime(); time++) {
            Complex c = new Complex(0, 0);

            for (int k = -fourierFunction.getLength(); k <= fourierFunction.getLength(); k++) {
                c = Complex.add(c, fourierFunction.getValueAt(k, time));
            }

            complexes.add(Complex.multiply(c, -1));
        }
    }

    void drawLine(Graphics2D g, Complex c1, Complex c2) {
        g.drawLine((int)c1.getReal(), (int)c1.getImaginary(), (int)c2.getReal(), (int)c2.getImaginary());
    }

    private void drawFourier(Graphics2D g) {
        Complex c1 = new Complex(0, 0);
        Complex c2 = Complex.add(c1, Complex.multiply(fourierFunction.getValueAt(0, time), -1));

        drawLine(g, c1, c2);

        var line = new Line2D.Float();

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
