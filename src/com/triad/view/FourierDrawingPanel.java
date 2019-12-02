package com.triad.view;

import com.triad.fourier.FourierFunction;
import com.triad.math.Complex;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * A JPanel, that draws the specified FourierFunction.
 */
public class FourierDrawingPanel extends JPanel {
    private final FourierFunction fourierFunction;
    private final ArrayList<Complex> complexes = new ArrayList<>();
    private final Complex center;
    private final float animationDuration;
    private int time = 0;
    private Timer timer;

    /**
     * Default constructor.
     * @param fourierFunction The FourierFunction to be drawn.
     * @param width The width of the Panel in pixels.
     * @param height The height of the Panel in pixels.
     * @param animationDuration The animation duration in seconds.
     */
    public FourierDrawingPanel(FourierFunction fourierFunction, int width, int height, float animationDuration) {
        this.fourierFunction = fourierFunction;
        this.animationDuration = animationDuration;
        fourierFunction.setOnUpdateHandler(this::fourierFunctionUpdateHandler);
        center = new Complex(width / 2f, height / 2f);

        this.setSize(width, height);
        this.setBackground(Color.GRAY);
    }

    /**
     * Handles when the FourierFunction updates. It recalculates the path, and restarts the timer.
     */
    private void fourierFunctionUpdateHandler() {
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

    /**
     * Starts, or restarts the timer.
     */
    private void restartTimer() {
        if (timer != null) timer.stop();
        time = 0;
        timer = new Timer((int)(animationDuration / fourierFunction.getMaxTime()), (ActionEvent) -> {
            time++;
            time %= fourierFunction.getMaxTime();

            this.repaint();
        });

        timer.setRepeats(true);
        timer.start();
    }

    /**
     * Draws a line between c1 and c2 to the g Graphics.
     * @param g The Graphics2D object.
     * @param c1 First complex
     * @param c2 Second complex
     */
    private void drawLine(Graphics2D g, Complex c1, Complex c2) {
        g.drawLine((int)c1.getReal(), (int)c1.getImaginary(), (int)c2.getReal(), (int)c2.getImaginary());
    }

    /**
     * Draws the FourierFunction the the g Graphics2D object.
     * @param g The Graphics2D object to draw to.
     */
    private void drawFourier(Graphics2D g) {
        if (fourierFunction.getLength() == 0 || fourierFunction.getMaxTime() == 0) return;

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

    /**
     * {@inheritDoc}
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawFourier((Graphics2D) g);
    }
}
