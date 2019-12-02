package com.triad.view;

import com.triad.fourier.*;
import org.apache.commons.io.FilenameUtils;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * The main window.
 */
public class MainWindow extends JFrame {
    private final int Width = 600;
    private final int Heigth = 600;
    private final int NumberOfSamples = 10000;
    private final int FourierSeriesLength = 100;
    private final float AnimationDuration = 3000;

    private final JFileChooser chooser = new JFileChooser();
    private ComplexSeriesProvider seriesProvidier = new ComplexSeriesProviderImplementation(FourierSeriesLength);
    private final FourierFunction seriesFunction = new FourierTemporalPointContainer();

    /**
     * The default constructor.
     */
    public MainWindow() {
        seriesProvidier.setComplexFunction(ComplexFunction.Empty);
        seriesFunction.setComplexSeriesProvider(seriesProvidier);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initMenu();
        var panel = new JPanel();
        panel.setBackground(Color.RED);
        this.add(new FourierDrawingPanel(seriesFunction, Width, Heigth, AnimationDuration));
        this.setSize(Width, Heigth);
    }

    /**
     * Opens a load file dialog, where the user can select what file to display. It handles SVG and FSVG files.
     */
    private void openLoadFileDialog() {
        var filter = new FileNameExtensionFilter("SVG files", "svg", "fsvg");
        chooser.setFileFilter(filter);
        if(chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            if (FilenameUtils.getExtension(chooser.getSelectedFile().getName()).equals("svg")) {
                loadSVGFile(chooser.getSelectedFile());
            } else {
                loadFSVGFile(chooser.getSelectedFile());
            }
        }
    }

    /**
     * Loads an SVG file, and sets the {@link ComplexSeriesProvider}'s {@link ComplexFunction} to the loaded SVG file.
     * @param file the file to be loaded.
     */
    private void loadSVGFile(File file) {
        try {
            seriesProvidier.setComplexFunction(new SVGComplexFunction(file.toURI(), NumberOfSamples));
        } catch (IOException e) {
            showErrorDialog("SVG file could not be loaded properly.", "File error");
        }
    }

    /**
     * Loads an FSVG file, and sets the {@link FourierFunction}'s {@link ComplexSeriesProvider} to the loaded FSVG file.
     * @param file the file to be loaded.
     */
    private void loadFSVGFile(File file) {
        try {
            var fileIn = new FileInputStream(file);
            var in = new ObjectInputStream(fileIn);
            seriesProvidier = (ComplexSeriesProvider) in.readObject();
            seriesFunction.setComplexSeriesProvider(seriesProvidier);
        } catch (ClassNotFoundException e) {
            showErrorDialog("FSVG file is not in the correct format", "Parsing error");
        } catch (IOException e) {
            showErrorDialog("File could not be found!", "File error");
        }
    }

    /**
     * Shows an error dialog.
     * @param message the message of the dialog.
     * @param title the title of the dialog.
     */
    private void showErrorDialog(String message, String title) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Opens a save file dialog, where the user can chose where to save the currently loaded {@link ComplexSeriesProvider} as a FSVG file
     */
    private void openSaveFileDialog() {
        var filter = new FileNameExtensionFilter("Fourier SVG files", "fsvg");
        chooser.setFileFilter(filter);
        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                var fileOut = new FileOutputStream(chooser.getSelectedFile());
                var out = new ObjectOutputStream(fileOut);
                out.writeObject(seriesProvidier);
                out.close();
                fileOut.close();
            } catch (IOException i) {
                JOptionPane.showMessageDialog(this, "FSVG file could not be saved properly.", "File error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Inits the MenuBar
     */
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
