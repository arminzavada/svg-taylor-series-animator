package com.triad.view;

import com.triad.fourier.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class MainWindow extends JFrame {
    JFileChooser chooser = new JFileChooser();
    ComplexFunction function = new SVGComplexFunction(new URI("/home/armin/test.svg"), 1000);
    ComplexSeriesProvider seriesProvidier = new ComplexSeriesProviderImplementation(50);
    FourierFunction seriesFunction = new FourierTemporalPointContainer();

    public MainWindow() throws IOException, URISyntaxException {
        seriesProvidier.setComplexFunction(function);
        seriesFunction.setComplexSeriesProvider(seriesProvidier);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        var function = new SVGComplexFunction(new URI("/home/armin/test.svg"), 1000);
        var seriesProvidier = new ComplexSeriesProviderImplementation(function, 30);
        var seriesFunction = new FourierTemporalPointContainer(seriesProvidier);
        this.add(new FourierDrawingPanel(seriesFunction));
        this.pack();
    }
}
