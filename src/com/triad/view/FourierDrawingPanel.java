package com.triad.view;

import javax.swing.*;
import java.awt.*;

public class FourierDrawingPanel extends JPanel {
    public FourierDrawingPanel(int timeStep) {
        Timer timer = new Timer(timeStep, (ActionEvent) -> {
            invalidate();
        });
    }

    private void drawFourier(Graphics2D g) {

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawFourier((Graphics2D) g);
    }
}
