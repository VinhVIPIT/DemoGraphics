package com.demo;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Create by VinhIT
 * On 15/03/2021
 */

public class MyCanvas extends Canvas implements MouseListener, MouseMotionListener, Runnable {
    int w = 500, h = 500;
    private Graphics g;

    public MyCanvas() {

        setSize(500, 500);

        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        Thread t = new Thread(this);
        t.start();
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, 500, 500);

        g.translate(w / 2, h / 2);

        g.setColor(new Color(217, 146, 54));
        g.drawLine(-w / 2, 0, w, 0);
        g.drawLine(0, -h / 2, 0, h / 2);

        g.drawString(String.valueOf(System.currentTimeMillis()), 100, 100);

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        g = this.getGraphics();

        g.setColor(Color.red);
        int x = e.getX();
        int y = e.getY();

        g.fillOval(x, y, 5, 5);
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void run() {
        while (true) {
            repaint();
            try {
                Thread.sleep(10000);
            } catch (Exception e) {

            }
        }

    }
}
