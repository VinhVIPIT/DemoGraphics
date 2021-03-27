package com.demo;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.List;

/**
 * Create by VinhIT
 * On 27/03/2021
 */

public class DrawCanvas extends Canvas implements Runnable {

    public static final int canvasWidth = 1000, canvasHeight = 600;
    public static final int rowSize = canvasWidth / 5, colSize = canvasHeight / 5;

    public static final int pixelSize = 5;

    private int[][] board = new int[rowSize + 1][colSize + 1];
    private int[][] drawingBoard = new int[rowSize + 1][colSize + 1];

    private Point2D startPoint2D, endPoint2D;

    private boolean isDrawing = false;

    Line line;


    public DrawCanvas() {
        setPreferredSize(new Dimension(canvasWidth, canvasHeight));
        setBackground(new Color(0xffffff));

        this.addMouseListener(new MyMouseAdapter());
        this.addMouseMotionListener(new MyMouseMotionAdapter());

        for (int i = 0; i <= rowSize; i++) {
            for (int j = 0; j <= colSize; j++) {
                board[i][j] = drawingBoard[i][j] = 0xffffff;
            }
        }

    }

    public void clearDraw(List<Point2D> point2DS) {
        for (Point2D p : point2DS) {
            p.setColor(board[p.getComputerX()][p.getComputerY()]);
            drawingBoard[p.getComputerX()][p.getComputerY()] = board[p.getComputerX()][p.getComputerY()];
            putPixel(p);
        }
    }

    public void applyDraw(List<Point2D> point2DList) {
        for (Point2D p : point2DList) {
            drawingBoard[p.getComputerX()][p.getComputerY()] = p.getColor();
            putPixel(p);
        }
    }

    public void merge() {
        for (int i = 0; i <= rowSize; i++) {
            for (int j = 0; j <= colSize; j++) {
                if (board[i][j] != drawingBoard[i][j]) {
                    board[i][j] = drawingBoard[i][j];
                    drawingBoard[i][j] = 0xffffff;
                }
            }
        }
    }


    public void paint(Graphics g) {
        super.paint(g);

        line = new Line(null, null, this);

    }


    public void putPixel(Point2D point) {
        Graphics g = DrawCanvas.this.getGraphics();
        g.setColor(new Color(point.getColor()));
        g.fillRect(point.getComputerX() * pixelSize, point.getComputerY() * pixelSize, pixelSize, pixelSize);
        g.dispose();
    }

    void put8Pixel(int x, int y, int color) {
//        putPixel(x, y, color);
//        putPixel(y, x, color);
//        putPixel(y, -x, color);
//        putPixel(x, -y, color);
//        putPixel(-x, -y, color);
//        putPixel(-y, -x, color);
//        putPixel(-y, x, color);
//        putPixel(-x, y, color);

    } // Put8Pixel

    void CircleMidPoint(int R) {
        int x, y;

        x = 0;
        y = R;
        put8Pixel(x, y, 0xff00ff);
        double p = 1.25 - R; // 5/4-R
        while (x < y) {
            if (p < 0) p += 2 * x + 3;
            else {
                p += 2 * (x - y) + 5;
                y--;
            }
            x++;
            put8Pixel(x, y, 0xff00ff);
        }

    }


    @Override
    public void run() {
        while (true) {
//            if (isDrawing) redraw();
            try {
                Thread.sleep(10);
            } catch (Exception e) {

            }
        }

    }

    public class MyMouseAdapter extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            super.mouseReleased(e);
            merge();

            line.setStartPoint(null);
            line.setEndPoint(null);
            isDrawing = false;
        }
    }

    public class MyMouseMotionAdapter extends MouseMotionAdapter {
        @Override
        public void mouseDragged(MouseEvent e) {
            super.mouseDragged(e);
            isDrawing = true;

            if (e.getX() > canvasWidth || e.getY() > canvasHeight || e.getX() < 0 || e.getY() < 0) return;

            if (line.getStartPoint() == null) {
                line.setStartPoint(Point2D.fromComputerCoordinate(e.getX() / DrawCanvas.pixelSize, e.getY() / DrawCanvas.pixelSize));
            } else {
                line.setEndPoint(Point2D.fromComputerCoordinate(e.getX() / DrawCanvas.pixelSize, e.getY() / DrawCanvas.pixelSize));
            }

        }

        @Override
        public void mouseMoved(MouseEvent e) {
            super.mouseMoved(e);

        }
    }


}
