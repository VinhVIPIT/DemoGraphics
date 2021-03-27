package com.demo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

/**
 * Create by VinhIT
 * On 19/03/2021
 */

public class PaintGUI extends JFrame {

    private JPanel rootPanel;
    private JButton btnPen;
    private JButton button2;
    private JButton button3;
    private JButton btnClear;
    private JButton button5;
    private JButton button6;
    private JButton button7;
    private JButton button8;
    private JButton button9;
    private JButton button10;
    private JButton button11;
    private JButton button12;
    private JPanel mainPanel;

    private DrawCanvas canvas;



    public PaintGUI() {
        setTitle("Paint");
        setSize(1200, 900);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        canvas = new DrawCanvas();

        /*canvas.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                if(startPoint != null){
                    endPoint = Point.fromComputerCoordinate(e.getX()/DrawCanvas.pixelSize, e.getY()/DrawCanvas.pixelSize);
                    canvas.repaint();
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
            }
        });
        canvas.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(startPoint == null){
                    startPoint = Point.fromComputerCoordinate(e.getX()/5, e.getY()/5);
                }

            }
        });
*/
        mainPanel.add(canvas);

        add(rootPanel);

    }



}
