package com.triad;

import com.triad.view.MainWindow;

/**
 * The Main class.
 */
public final class Main {
    /**
     * The entrypoint to the program.
     * @param args command line input arguments. Not used.
     */
    public static void main(String [] args) {
        MainWindow mainWindow = new MainWindow();
        mainWindow.setVisible(true);
    }
}