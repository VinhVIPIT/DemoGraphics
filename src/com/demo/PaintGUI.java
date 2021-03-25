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

    private int[][] board = new int[201][121];

    int mx = -1, my = -1, mx2 = -1, my2 = -1;


    public PaintGUI() {
        setTitle("Paint");
        setSize(1200, 900);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        canvas = new DrawCanvas();

        canvas.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                if (mx != -1 && my != -1) {
                    mx2 = -200 / 2 + e.getX() / 5;
                    my2 = 120 / 2 - e.getY() / 5;
                    canvas.repaint();

                }
//                canvas.putPixel(-200/2+e.getX()/5, 120/2-e.getY()/5, 0xff000000);
            }
        });
        canvas.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (mx == -1 || my == -1) {
                    mx = -200 / 2 + e.getX() / 5;
                    my = 120 / 2 - e.getY() / 5;
                }

            }
        });

        mainPanel.add(canvas);

        add(rootPanel);

    }


    private class DrawCanvas extends Canvas {

        private DrawCanvas() {
            setPreferredSize(new Dimension(1005, 605));
            setBackground(new Color(0xffffff));
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            g.setColor(Color.BLACK);

            int x1 = -20, y1 = 40;
            int x2 = 20, y2 = 40;
            int x3 = 20, y3 = -40;
            int x4 = -20, y4 = -40;


//            lineBresenham(x1, y1, x2, y2);
//            lineBresenham(x2, y2, x3, y3);
//            lineBresenham(x3, y3, x4, y4);
//            lineBresenham(x1, y1, x4, y4);

            lineBresenham(0, -59, 0, 59);
            lineBresenham(-99, 0, 99, 0);

            if (mx != -1 && my != -1 && mx2 != -1 && my2 != -1) {
                lineBresenham(mx, my, mx2, my);
                lineBresenham(mx2, my, mx2, my2);
                lineBresenham(mx2, my2, mx, my2);
                lineBresenham(mx, my2, mx, my);
            }

//            putPixel(0,0,0xff000000);
        }


        public void putPixel(int x, int y, int color) {
            //x,y hình học

            int xm = x + 200 / 2;
            int ym = 120 / 2 - y;

            Graphics g = DrawCanvas.this.getGraphics();
            board[xm][ym] = color;
            g.setColor(new Color(color));
            g.fillRect(xm * 5, ym * 5, 5, 5);
            g.dispose();
        }

        void drawLine(int x1, int y1, int x2, int y2) {
            int Dx, Dy, p, Const1, Const2;
            int x, y;

            if (x1 > x2) {
                x = x1;
                x1 = x2;
                x2 = x;
                y = y1;
                y1 = y2;
                y2 = y;
            }

            Dx = x2 - x1;
            Dy = y2 - y1;
            p = 2 * Dy - Dx;    //   (Dy <<1)  - Dx
            Const1 = 2 * Dy;        // Dy <<1
            Const2 = 2 * (Dy - Dx);    // (Dy-Dx) <<1
            x = x1;
            y = y1;
            putPixel(x, y, 0xff0000);
            if (Math.abs(x2 - x1) > Math.abs(y2 - y1)) {
                for (int i = x1; i < x2; i++) {
                    if (p < 0) p += Const1;
                    else {
                        p += Const2;
                        y++;
                    }
                    x++;
                    putPixel(x, y, 0xff0000);
                }
            } else {
                for (int i = y1; i < y2; i++) {
                    if (p < 0) p += Const1;
                    else {
                        p += Const2;
                        x++;
                    }
                    y++;
                    putPixel(x, y, 0xff0000);
                }
            }

        }

        void lineBresenham(int x1, int y1, int x2, int y2) {
            int x, y, Dx, Dy, p;
            Dx = Math.abs(x2 - x1);
            Dy = Math.abs(y2 - y1);
            x = x1;
            y = y1;

            int x_unit = 1, y_unit = 1;

            if (x2 - x1 < 0)
                x_unit = -x_unit;
            if (y2 - y1 < 0)
                y_unit = -y_unit;

            putPixel(x, y, 0xff0000);

            if (x1 == x2)   // trường hợp vẽ đường thẳng đứng
            {
                while (y != y2) {
                    y += y_unit;
                    putPixel(x, y, 0xff0000);
                }
            } else if (y1 == y2)  // trường hợp vẽ đường ngang
            {
                while (x != x2) {
                    x += x_unit;
                    putPixel(x, y, 0xff0000);
                }
            }
            // trường hợp vẽ các đường xiên
            else {

                if (Dx >= Dy) {
                    p = 2 * Dy - Dx;

                    while (x != x2) {
                        if (p < 0) p += 2 * Dy;
                        else {
                            p += 2 * (Dy - Dx);
                            y += y_unit;
                        }
                        x += x_unit;
                        putPixel(x, y, 0xff0000);
                    }
                } else {
                    p = 2 * Dx - Dy;

                    while (y != y2) {
                        if (p < 0) p += 2 * Dx;
                        else {
                            p += 2 * (Dx - Dy);
                            x += x_unit;
                        }
                        y += y_unit;
                        putPixel(x, y, 0xff0000);
                    }
                }

            }
        }


    }
}
