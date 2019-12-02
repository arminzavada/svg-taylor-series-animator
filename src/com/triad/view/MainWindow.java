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
    ComplexFunction function = new SVGComplexFunction(new URI("/home/armin/test.svg"), 100);
    ComplexSeriesProvider seriesProvidier = new ComplexSeriesProviderImplementation(10);
    FourierFunction seriesFunction = new FourierTemporalPointContainer();

    public MainWindow() throws IOException, URISyntaxException {
        seriesProvidier.setComplexFunction(function);
        seriesFunction.setComplexSeriesProvider(seriesProvidier);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        var filter = new FileNameExtensionFilter("SVG files", "svg");
        chooser.setFileFilter(filter);
        initMenu();
        var panel = new JPanel();
        panel.setBackground(Color.RED);
        this.add(new FourierDrawingPanel(seriesFunction, 600, 600, 3000));
        this.setSize(600, 600);
    }

    private void openLoadFileDialog() {
        if(chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                seriesProvidier.setComplexFunction(new SVGComplexFunction(chooser.getSelectedFile().toURI(), 1000));
            }
            catch (IOException e) {
                 JOptionPane.showMessageDialog(this, "SVG file could not be loaded properly.", "File error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void openSaveFileDialog() {

    }

    private void initMenu() {
        var menuBar = new MenuBar();
        var menu = new Menu("File");

        var saveMenuItem = new MenuItem("Load");
        saveMenuItem.getAccessibleContext().setAccessibleDescription("Loads a new SVG file to display.");
        saveMenuItem.addActionListener(actionEvent -> openLoadFileDialog());

        var loadMenuItem = new MenuItem("Save");
        loadMenuItem.getAccessibleContext().setAccessibleDescription("The only menu in this program that has menu items");
        loadMenuItem.addActionListener(actionEvent -> openSaveFileDialog());

        menu.add(saveMenuItem);
        menu.add(loadMenuItem);
        menuBar.add(menu);
        this.setMenuBar(menuBar);
    }
}
