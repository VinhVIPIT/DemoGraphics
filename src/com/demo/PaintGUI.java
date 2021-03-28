package com.demo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Create by VinhIT
 * On 19/03/2021
 */

public class PaintGUI extends JFrame {

    private JPanel rootPanel;
    private JButton btnPen;
    private JButton button2;
    private JButton btnLine;
    private JButton btnClear;
    private JButton btnRect;
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
        mainPanel.add(canvas);

        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.clearScreen();
            }
        });

        add(rootPanel);

    }



}
