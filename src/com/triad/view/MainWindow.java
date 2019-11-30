package com.triad.view;

import com.triad.fourier.ComplexSeriesProviderImplementation;
import com.triad.fourier.FourierTemporalPointContainer;
import com.triad.fourier.SVGComplexFunction;

import javax.swing.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class MainWindow extends JFrame {
    public MainWindow() throws URISyntaxException, IOException {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        var function = new SVGComplexFunction(new URI("/home/armin/test.svg"), 1000);
        var seriesProvidier = new ComplexSeriesProviderImplementation(function, 30);
        var seriesFunction = new FourierTemporalPointContainer(seriesProvidier);
        this.add(new FourierDrawingPanel(seriesFunction));
        this.setSize(1000, 1000);
    }
}
