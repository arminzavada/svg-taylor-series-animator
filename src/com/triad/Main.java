package com.triad;
//
//import java.awt.event.*;
//import java.awt.*;
//import javax.swing.*;
//
// class Line{
//
//    public static void main(String[] args){
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                JFrame frame = new JFrame();
//                frame.add(new DrawLine(150,300,350,300));
//                frame.setSize(500,500);
//                frame.setVisible(true);
//            }
//        });
//    }
//}
//
//class DrawLine extends JPanel implements ActionListener{
//
//    int x1;
//    int y1;
//    int x2;
//    int y2;
//    int midx;
//    int midy;
//
//    Timer time = new Timer(10, (ActionListener) this);
//
//    public DrawLine(int x1, int y1, int x2, int y2){
//        this.x1 = x1;
//        this.y1 = y1;
//        this.x2 = x2;
//        this.y2 = y2;
//        midx = (x1+x2)/2;
//        midy = (y1+y2)/2;
//        time.start();
//    }
//
//    public void animateLine(Graphics2D g){
//        g.drawLine(x1,y1,midx,midy);
//        g.drawLine(x2,y2,midx,midy);
//    }
//
//    public void actionPerformed(ActionEvent arg0) {
//        if(midy>123){
//            midy--;
//            repaint();
//        }
//    }
//
//    public void paintComponent(Graphics newG){
//        super.paintComponent(newG);
//        Graphics2D g2d = (Graphics2D)newG;
//        animateLine(g2d);
//    }
//}

import com.triad.fourier.ComplexSeriesProviderImplementation;
import com.triad.fourier.FourierTemporalPointContainer;
import com.triad.fourier.SVGComplexFunction;
import com.triad.view.MainWindow;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

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