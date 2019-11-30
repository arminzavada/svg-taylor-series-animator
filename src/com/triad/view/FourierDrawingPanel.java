package com.triad.view;

import com.triad.fourier.FourierFunction;
import com.triad.math.Complex;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class FourierDrawingPanel extends JPanel {
    private FourierFunction fourierFunction;
    int time = 0;
    ArrayList<Complex> complexes = new ArrayList<>();

    public FourierDrawingPanel(FourierFunction fourierFunction) {
        this.fourierFunction = fourierFunction;

        for (int time = 0; time < fourierFunction.getMaxTime(); time++) {
            Complex c = new Complex(1000, 1000);

            for (int k = -fourierFunction.getLength(); k <= fourierFunction.getLength(); k++) {
                c = Complex.add(c, fourierFunction.getValueAt(k, time));
            }

            complexes.add(c);
        }


        Timer timer = new Timer(10, (ActionEvent) -> {
            time++;
            time %= fourierFunction.getMaxTime();

            this. repaint();
        });
        timer.setRepeats(true);
        timer.start();
    }

    void drawLine(Graphics2D g, Complex c1, Complex c2) {
        g.drawLine((int)c1.getReal(), (int)c1.getImaginary(), (int)c2.getReal(), (int)c2.getImaginary());
    }

    private void drawFourier(Graphics2D g) {
        Complex c1 = new Complex(1000, 1000);
        Complex c2 = Complex.add(c1, fourierFunction.getValueAt(0, time));

        drawLine(g, c1, c2);

        for (int i = 1; i <= fourierFunction.getLength(); i++) {
            c1 = Complex.add(c2, fourierFunction.getValueAt(i, time));
            drawLine(g, c2, c1);
            c2 = Complex.add(c1, fourierFunction.getValueAt(-i, time));
            drawLine(g, c1, c2);
        }

        for (int i = 1; i < time; i++) {
            drawLine(g, complexes.get(i - 1), complexes.get(i));
        }

        g.drawString(Integer.toString(time), 100, 100);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawFourier((Graphics2D) g);
    }
}
